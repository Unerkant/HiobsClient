 'use strict';

 /**
  * Den 4.08.2025
  */

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