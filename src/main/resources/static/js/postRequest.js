'use strict';

/**
 * Den 20.03.2025
 */


 /**
  * postRequest.js -> ruft den Controller mit den @PostRequest auf
  * wird von alle settingcategory benutzt, für den Einstellungen oder Information sammeln
  * ERFORDERLICH PARAMETER: url-> zum Controller
  *                         param -> kann sein einen user Token oder schlüsselwort für einstellungen
  *     <code>
  *         <button data-th-onClick="postRequest('/auth', 'keine')">Abmelden</button>
  *     </code>
  *     <controller>
  *         @PostMapping(path = "/auth")
  *         public ResponseEntity<String> auth(@RequestBody String token ) {
  *
  *             System.out.println("Auth token: " + token);
  *             return ResponseEntity.status(HttpStatus.OK).body(token);
  *         }
  *     </controller>
  */
 function postRequest(url, param) {

	var xhr = new XMLHttpRequest();
	//var url = "login/einloggen";
	xhr.open("POST", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.onload = () => {
    				if (xhr.readyState == 4 && xhr.status == 200) {

    						//console.log('IF: ' + xhr.responseText); // ergebnis: nichtabgemeldet
                            infoAnzeigen(xhr.responseText);
    				} else {
    						//console.log('Error: ${xhr.status}');
    				}
    };
	xhr.send("param="+param);
	//xhr.send("hund=Dog&name=Sam");
 }

/**
 * Blendet den Fehler für 5 Sekunden an, id der #ID von wo denn click gemacht wird
 * die function showErrors() befindet sich in function.js
 * showErrors -> parameter:  #ID wo soll Pop-Up-Fenster angezeigt sein
 *                           kurze Fehler-Text ()
 *  CASE:   'nichtabgemeldet'   -> click kommt von profilabmelden.html Zeile: 88
 *          'abgemeldet'        -> click kommt von profilabmelden.html Zeile: 94
 */
 function infoAnzeigen( fehlerResponse ) {

    switch(fehlerResponse) {
        case'nichtabgemeldet':      showErrors('EDITPROFIL', 'Der aktuelle Benutzer kann nicht abgemeldet werden');
                                    break;
        case'abgemeldet':           showErrors('EDITPROFIL', 'Du bist erfolgreich abgemeldet!');
                                    window.location.href = '/';
                                    break;
        default:
    }
 }