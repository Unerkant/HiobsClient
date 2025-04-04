 'use strict';

 /**
  * Den 30.12.2024
  */


 /**
  * Elementen einblenden und Ausblenden,
  * ID -> von div erforderlich...
  *
  *	ACHTUNG: ist immer in fragments/head.html eingebunden
  *
  *     BENUTZT:    1. noch keine
  */
  function elementShow(id)
  {

 	if(document.getElementById) {

 		var mydiv = document.getElementById(id);
 		mydiv.style.display = (mydiv.style.display == 'block' ? 'none' : 'block');
 	}
  }

 /**
  *  Kurze Nachricht anzeigen und nach 4 Sekunde wieder schliessen
  *	 ACHTUNG: muss 3 Parameter zugesendet sein, den msg-Box-ID + Text-ausgabe-Node-ID + selber Text
  *		<code>
  *         // msg Senden
  *         msgVisible("msg Box ID", "text-ausgabe Node ID", "den selber Text, frei kreiert");
  *         // die Ausgabe (erforderlich einen div in example.html)
  *			<div id="selbs festlegen" class="msgAnzeige boxShadow">
  *	 			<p id="selbs festlegen"></p>
  *			</div>
  *		</code>
  *
  *     BENUTZT:    1. success.html + success.js
  *                 2. noch keine
  */
  function msgVisible(boxId, nodeId, msgText ) {
        var msgDivs = document.getElementById(boxId);
        msgDivs.style.visibility = "visible";

        var msgElement = document.getElementById(nodeId);
        msgElement.innerHTML = msgText;

        setTimeout(() => msgDivs.style.visibility = "hidden", 4000);
  }


 /**
  * BENUTZT: von index.html
  * BESCHREIBUNG: der Fehler-Anzeige Box wird dynamisch hier erzeugt und in den beliebigen DIV angezeigt,
  *               in 5 Sekunden wieder ausgeblendet...
  *               der css-class befindet sich in: chat.css
  * ACHTUNG: 2 parameter sind erforderlich
  *     1. die ID von einem element wo wird den fehler angezeigt
  *     2. den selber fehler-text
  *     <javascript>
  *         var divId = "RIGHTCOLUMN";
            var text  = "bitte Starten Sie die APP neu!";
            showErrors(divId, text);
  *     </javascript>
  */
  function showErrors(msgBox, msgText ) {

    var anzeigeDiv  = document.getElementById(msgBox);
    var dynamischDiv   = document.createElement("div");
    dynamischDiv.classList.add('fehlerBox');
    dynamischDiv.classList.add('shadows');
    dynamischDiv.appendChild(document.createTextNode(msgText));
    anzeigeDiv.appendChild(dynamischDiv);
    setTimeout(() => anzeigeDiv.removeChild(dynamischDiv), 5000);

  }


 /**
  * BENUTZT: fragments/leftcolumn/friends.html Zeile: 22
  * BENUTZT:
  *
  * KURZE BESCHREIBUNG: zu dropMenu-function werden von angezeigte div nur #ID + class(welche soll div anzeigen)
  *                     zugesendet, bei anklicke wird die class ins den div(von zugesendete #ID) reingeschrieben
  *                     und bei schliessen (click in dropMenu oder body) die class wird gelöscht...
  *
  * die 3 functionen gehören zusammen: dropMenu + menuHide + bodyClick
  * ERFORDERLICH: an dropMenu()... die dropMenu ID + class (für dropMenu anzeige)
  * <code>
  *     <div class="dropBox">
  *         <button onClick="return dropBox('FREUNDEMENU', 'dropMenuOpen', event)" </button>
  *             <div id="FREUNDEMENU" class="dropMenu">
  *                 <p>Neue Gruppe</p>
  *                 <p>Neuer geheimer Chat</p>
  *                 <p>Neuer Kanal</p>
  *             </div>
  *     </div>
  * </code>
  * FAZIT: kann von mehreren Pop-up-Fenster benutzt, Voraussetzung #ID + open class
  */
  var currentFM, classe;
  function dropBox(element, klass, event) {

    var parent = document.getElementById(element);
    var isOpen = (parent.className || '').indexOf(klass) > 0;
    if(!isOpen) {

        parent.className = (parent.className || '') + ' ' + klass;
        window.addEventListener("click", bodyClick, false);
        currentFM = parent;
        classe = klass;

    } else {

        menuHide(currentFM, klass);
        currentFM = false;
    }
    // Stopt den ausführung den body-click -> window.addEventListener("click"....), das ist den click auf dem body...
    event.stopPropagation();
    return false;
  }

 /**
  * ACHTUNG: gehört zum function dropMenü(oben)
  * Pop-up-Fenster schliessen
  */
  function menuHide(parent, clas) {

        parent.className = parent.className.replace(clas, '');
        window.removeEventListener('click', bodyClick);
  }

 /**
  * ACHTUNG: gehört zum function dropMenü(oben)
  * Click auf dem Body, Pop-up-Fenster schliessen
  */
  function bodyClick(event) {

        if(currentFM) {
            menuHide(currentFM, classe);
            currentFM = false;
        }
  }