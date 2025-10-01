 'use strict';

 /**
  * Den 14.09.2025
  */

  (function() {
    //alert('Suchanfrage');
  }());

  /**
   *    Anrufen
   *    ACHTUNG: noch keine Funktion,
   *    AUDIO: audio.play(), audio.pause(), audion.currentTime = 0;
   *
   *    KURZE BESCHREIBUNG: was ist gemacht, nur mp3 anruf-track abgerufen und beenden, keine Telefon verbindung
   *                        nur der telefon sound + Bilder wechseln
   */
   var audio;
   function callByPhone(telefonNummer, id, event) {

        const iconElement = document.getElementById(id);
        const esKlingelt = (iconElement.className || '').indexOf('icon-phoneklingelt') > 0;

        if(!esKlingelt) {
            // zuerst den class icon-phone löschen dann icon-phoneklingert schreiben, replace functioniert nicht
            iconElement.classList.remove('icon-phone');
            iconElement.className = (iconElement.className || '') + ' icon-phoneklingelt';
            audio = newAudio();
            audio.play();

        } else {

            audio.pause();
            audio.currentTime = 0;
            // hier functioniert die replace von classen
            iconElement.className = iconElement.className.replace('icon-phoneklingelt', 'icon-phone');

        }

        //console.log('Ausen', audio);
        event.stopPropagation();
        return false;

   }

   function newAudio() {
        audio = new Audio('/tone/anruf.mp3');
        return audio;
   }