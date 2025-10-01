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