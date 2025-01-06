 'use strict';

 /**
  * Den 30.12.2024
  */



 /**
  * Elementen einblenden und Ausblenden,
  * ID -> von div erforderlich...
  *
  *	ACHTUNG: ist immer in head.view.php eingebunden
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
  *	 ACHTUNG: muss nur einen Parameter zugesendet sein, den msg-Text
  *	 	msgShow("Anzeige Text");
  *
  *		<code>
  *			<div id="msgBox" class="msgAnzeige boxShadow">
  *	 			<p id="msgText"></p>
  *			</div>
  *		</code>
  */
 function msgShow( msg ) {

    var msgDivs = document.getElementById('msgDiv');
    msgDivs.style.visibility = "visible";

    var msgText = document.getElementById('msgText');
    msgText.innerHTML = msg;

    setTimeout(() => msgDivs.style.visibility = "hidden", 4000);
 }
