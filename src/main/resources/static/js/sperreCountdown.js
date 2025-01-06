'use strict';

 /**
  * Den 12.11.2024
  */

 /**
  *     die sperrZeitInMillis werden aus einem input gelesen (sperre.html)
  *     <input type="hidden" id="sperrMillis"  data-th-value="${sperreInMillis}" disabled>
  *     aus die gesperrte Zeit und Aktuelle Zeit wird differenz ermittelt
  *     danach läst sich der entsperr-Tag ausrechnen und anzeigen
  *     bei abgelaufenen Zeit wird die function sperreAufheben(){..} aktiviert
  *
  *      // sperrZeitInMillis: 1735678800000 ( Zeit in Millisekunden bis 31.12.2024 22:00:00)
  *      // 1 Sekunde drüber als sperr Zeit: 1735678801000
  */
 var sperrZeitInMillis = document.getElementById('sperrMillis').value.trim();
 var myTimeout;

 (function countdown(){

    let newMillis = Date.now();
    let differenz = Math.abs(sperrZeitInMillis - newMillis);

    let timeInSeconds   = Math.floor( differenz / 1000 ),
        seconds         = timeInSeconds % 60;
        seconds         = (seconds < 10 ? '0' : '') + seconds;
    let timeInMinutes   = Math.floor( timeInSeconds / 60 ),
        minutes         = timeInMinutes % 60;
        minutes         = (minutes < 10 ? '0' : '') + minutes;
    let timeInHours     = Math.floor( timeInMinutes / 60 ),
        hours           = timeInHours % 24;
        hours           = (hours < 10 ? '0' : '') + hours;
    let days            = Math.floor( timeInHours / 24 );

    if(newMillis < sperrZeitInMillis) {

        document.getElementById('sperrCountdown').innerHTML = getDays(days) + getHours(hours, days)
                                                + getMinutes(minutes) + getSeconds(seconds);
        myTimeout = setTimeout(countdown, 1000);

    } else {

        stopTimeout();
        document.getElementById('entsperrText').innerHTML = "ihrer Account ist wieder Frei!";
        sperreAufheben();

    }

 }) ();

 function stopTimeout() {
    clearTimeout(myTimeout);
    myTimeout = null;
 }

 function getDays(days) {
    return days > 0 ? ('<div class="sperreJs"><h2>'+ days +'</h2><p>Tage</p></div>') : '';
 }

 function getHours(hours, days) {
    return hours > 0 || days > 0 ? ('<div class="sperreJs"><h2>'+ hours +'</h2><p>Stunden</p></div>') : '';
 }

 function getMinutes(minutes) {
    return minutes >= 0 ? ('<div class="sperreJs"><h2>'+ minutes +'</h2><p>Minuten</p></div>') : '';
 }

 function getSeconds(seconds) {
    return seconds >= 0 ? ('<div class="sperreJs"><h2 class="lilaLight">'+ seconds +'</h2><p>Sekunden</p></div>') : '';
 }

/**
 * HINWEIS: ablaufzeit anzeige, aus Millisekunden Tag ermittelt
 * wenn sperrzeit abgelaufen ist Einloggen anzeigen
 */
 var entsperrDate = new Date();
 entsperrDate.setMilliseconds( Math.abs(sperrZeitInMillis - entsperrDate) );
 var aktuellMillis = new Date().getTime();
 document.getElementById('entsperrDatum').innerHTML =
        (aktuellMillis < sperrZeitInMillis) ?  '<b>Hinweis: </b>'+
        '<span>Ihre sperre wird am ' + entsperrDate.toLocaleString() +' aufgehoben.</span'
        : '<a href="index">Einloggen</a>';


/**
 *  Sperrzeit aus dem Datenbank löschen
 *  Gesendet an SperreController/@DeleteMapping("/sperreDelete")
 *
 * body: "" -> Symbolisch, nicht notwendig, möchten kein NULL senden
 * body: "" Leeres Zeichen funktioniere auch
 *
 *  ACHTUNG: der Fetch wird LEER an SperreController/@DeleteMapping gesendet,
 *           rückgabewert wird als response.text erhalten
 *
 *  response als 1: weiterleiten an index
 *  response als 0: nicht machen
 */
 function sperreAufheben() {

 fetch('sperreDelete', {
    method: 'DELETE',
    headers: { 'Content-Type': 'application/json' },
    body: '' //JSON.stringify({"id": 1}),
    })
    .then( response => response.text() )
    .then(data => {
        if(data == 1) {
            window.location.href = 'index';
        } else {
            //console.log('Nicht machen: ' + data);
        }
    });
    //.then( data => console.log('Response: ', data) )
    //.catch(error => console.error('Error: ', error.message) );
 }
