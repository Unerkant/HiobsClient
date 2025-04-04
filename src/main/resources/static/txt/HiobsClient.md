
    /* :::::::::::::::::::::::: Was Soll noch gemacht werden ::::::::::::::::::::::::::::::::::::: */
        
        1. ankomende messages count anzeigen, nur die classe einbinde und count angeben, die classe
            befindet sich in fragments.css: .frMenuCountTrue (Fragments/components:: footer Menü Bar)
        2.


    /* :::::::::::::::::::::::: Anleitung zu Project Hiobs Client ::::::::::::::::::::::::::::::::: */

        1. der Project HiobsClient basiert sich auf:
            a. java (version 23)
            b. spring boot (<version>3.4.0-M1</version>)
            c. Thymeleaf + Web ( <version>3.4.0-M1</version> )
            d. spting-websocket + messaging ( <version>6.2.0-M7</version> )
            e. Database H2 ( <version>2.3.232</version> ) hiobsClient (Tabelle: AUTH)
                Auth: {id=1, datum='15.09.2024 19:47:55', mail='sam@sam.de', other='',
                   role='', sperre='gesperrt', sperrdatum='1730659266485', token='1234567890'}
            f. Git 
        2. Hauptseite ist die index.html + zusätzlich: sperre.html und login.html
            Bie Ersten Start http://localhost:8090/ wird die index.html abgeruffen, in den
            IndexController wir zuerst auf sperrung und Token geprüft, quasi gesagt wenn Datenbank
            hiobsClient/AUTH leer ist dan weiterleitung an LoginController
            bei einer erfolgreichen einloggen (gültige E-Mail) werden die Daten von MySql(hiobsServer)
            geholt und in Datenbak (H2: hiobsClient/auth) gespeichert...
            Die Daten von Tabelle auth werden für die indefizierung benutzt(index.html frei geben)
        3.  ACHTUNG: die Tabelle 'AUTH' in H2 Datenbank 'hiobsClient' soll nur einmal vorkommen, 
            bei einem ausloggen wird die Tabelle 'AUTH' geleert
        4. bei eine sperrung wird id der Tabelle 'AUTH' in der spalte: sperre den text 'gesperrt'
            und in spalte 'sperrdatum' datum(1 woche oder meer) in Millisekunden eingetragen, bei
            eine aktualisierung die index.html wird auf sperrung geprüft, bei true werden die
            Seiten index.html + login.html gesperrt, nur die sperrseite angezeigt
        5.
        


    /* ******************************** HTML + Controller ***************************************** */
        1. MailController -> verwaltet merhere HTML (login/mail.html + idcode.html + success.html)
        2. QrloginController -> noch nict fertig, soll (login/ qrlogin.html + idcode.html + success.html)
           ACHTUNG: Qr-Login soll später fertigstellen, soll idcode + success mitbenutzen!!!


    /* ******************************** Cookie (javacript) **************************************** */
        zurzeit keine cookie benutzt

    /* ******************************** noscript(no javascript) *********************************** */
        wenn in Browser die javascript wird deaktiviert, dann wird den Fehler anzeigen und den rest
        die seite wird ausgeblendet... <div class="container"> auf display: none gesetzt
        Quelle von script wir in fragments ausgelagert, für den Globalen zugriff

    /* ******************************** functions.js (javacript) **************************************** */
    
        in functions.js script sind verschiedene functions zusammen gesammelt und können in jeden script
        benutzt werden, wiel ist in head integriert
        KURZE BESCHREIBUNG VON FUNCTIONEN:
        1. elementShow(id){}, ist einfache function den div anzeigen/verstecken, ID erforderlich
        2. msgShow(msg){}, zeigt kurze benachrichtung und in 4 sekunden schließt sich selbs, background -> schwarz
        3.

        // noch nicht benutzt
    /* ******************************** sperre.html + SperreController **************************** */
        VORAUSETZUNG: 
            in Datenbank H2 Tabelle/hiobsClient/Auth die spalte 'sperrdatum' soll Sperr-Datum in Millisecunden 
                erhalten oder bei leer einen NULL, 
                diese 2 Eingeschften brauch man in SperreController + sperreCountdown.js

        ACHTUNG: die spalte 'sperrdatum' SOLL den wert NULL haben, weil die alle abfragen in Controllers
                 sind auf NULL positioniert: if (authService.sperreMillis() != null) {...}
        
           1.  User gesperrt! wenn user wird auf Bestimmte Zeit gesperrt, dann wird in Datenbank
               HiobsServer/MySql/User spalte sperrdatum(in Millisekunden) eingetragen
           2. die sperre wird von Admin(noch in Arbaeit) festgelgt und im MySql/User eingetragen
           3. bei erfolgreichen Einloggen auf der HiobsClient Seite werden die User-Daten von 
               HiobsServer/MySql/User geladen und ins H2 Datenbank (hiobsClient) geschrieben...
               bei Starten die Index.html wird in IndexController auf sperre geprüfft, wenn
               eintaragung da ist dann wird die sperre.html abgeruffen und gesperrte Zeit angezeigt
           4.  bei ablauf die Sperrzeit, wird in H2 + MySql die spalte 'sperrdatum' Update auf NULL

        KURZE BESCHREIBUNG: wenn einen User wird gespert, zuerst wir die sperr-zeit(in millisekunden) ins Datenbank 
                (MySql/globalHiobs) spalte 'sperrdatum' eingetragen + gleich die sperrdatum wird automatisch ins 
                HiobsClient/h2database/hiobsClient aktualisiert, bei aktualisierung der datenbank wird die 
                IndexController auch aktualisiert, und der IndexController bei jeder aktualisierung prüft in
                h2/hiobsClient nach sperrdatum, wenn in die spalte 'sperrdatum' die sperrzeit(in millis) 
                vorhanden ist wird die sperre aktiviert...
            SPERRE-FUNCTION: bei einer user-sperre wird den SperreController abgerufen und die sperre.html
                geladen, gleich von dem controller wird die sperrzeit in millisekunden an die sperre.html 2x gesendet
                    1. zu einem input(später wird die value von javacsript abgerufen)...
                    2. zu einem javascript: den script aktivieren/freimachen + die function coutdown(){}, starten...
                        <script data-th-if="${sperrJS}" data-th-src="@{/js/sperreCountdown.js}"></script>
                sperreCountdown.js: bei entsperrung das script(position 2) wird die function coutdown() automatisch 
                                    gestartet und von input die millisekunde abgeruffen... der COUNTDOWN läuft,
                sperrzeit abgelaufen: wenn die aktuelle zeit wird größer als sperrzeit dann wird die function
                                    sperreAufheben(){...} geladen, 
                function sperreAufheben(){...} AUFGABE: die function setzt eine request an den SperreController ab um
                                                die @DeleteMapping("/sperreDelete") zu starten, bei einer 
                                                response 1 wird den link abgeruffen window.location.href = 'index';
                
        FAZIT: die sperre functionier gut, aber fehlt noch automatische abfrage nach sperrzeit in globalHiobs Datenbank,
                abfrage soll min. einmal pro Tag in globalHiobs/usern nach der sperrzeit abfragen, wenn vorhanden ist
                soll der H2 Datenbank bei Client(hiobsClient/auth) die spalte 'sperrdatum' aktualisieren...
            
        ÜBRIGENS: 


    /* ******************************** H2 Database *********************************************** */
        der datenbank H2 besitzt nur eine Tabelle Auth für einloggen oder sperren der Users
        a. bei einloggen wird nur auf vorhandenen Token geprüft
        b. bei gesperrten User wird auf spalte sperre und sperrdatum (in Millisekunden) zugegriefen
            bei spalte: 'id' (Long) soll nurr die 1 eigetragen sein
            bei spalte: 'sperre' (varchar) soll Value immer den Text(gesperrt) sein 
            bei spalte: 'sperrdatum' (Long/null) soll Value immer in Millisekunden sein,  
            bei splate: 'token' (varchar) der token von HiobsServer (MySql Haut Database)
        c.  Auth: {id=1, datum='15.09.2024 19:47:55', mail='sam@sam.de', other='',
                   role='', sperre='gesperrt', sperrdatum='1730659266485', token='1234567890'}
        d.
        


    /* ******************************** Allgemeine Daten  ***************************************** */
    style:
    html, body 	        { height: 100%; width: 100%; min-width: 500px; min-height: 600px; }
    html                { font-family: sans-serif; line-height: 1.15; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }
    .container          { min-height: calc(100vh - 0px); }
    .layout             { margin-left: auto; margin-right: auto; }

    a. Color

    b. IMG: 

    /* ******************************** index.html + IndexController ****************************** */
    index.html
    1. index.html ist die Haupseite des projects
    2. aus dem index.html wird der socket + stomp gestartet (javascript)
        <script src="/js/sockjs.js"></script>
        <script src="/js/stomp.js"></script>
        <script src="/js/socketConnect.js"></script>
    3.

    /* ******************************** login.html + LoginController ****************************** */
    Login.html
    1. login.html ist auf 3 Teile geteilt: 
            a. 1, einloggen mit E-Mail
            b. 2, einloggen mit Q-Code
            c. 3, Sicherheitscode prüfen, bei einloggen mit e-mail wird eine 4-stelige sicherheitscode
                  per e-mail versendet

    Controller



    /* ******************************** Cookie (javacript) **************************************** */
    /* ******************************** Cookie (javacript) **************************************** */
    /* ******************************** Cookie (javacript) **************************************** */
    /* ******************************** Cookie (javacript) **************************************** */
    /* ******************************** Cookie (javacript) **************************************** */
    
    // noch keine Benutzung

