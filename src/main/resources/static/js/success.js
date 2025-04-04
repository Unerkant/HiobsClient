 'use strict';

 /**
  * Den 15.10.2024
  */


 /**
  * Kopier den Text aus die Table
  */
 function datenCopy() {

    var copyText = document.getElementById('myDaten');
    navigator.clipboard.writeText(copyText.innerText);

    // Information anzeigen (head -> functions.js)
    msgVisible("SUCCESSPOPUP", "SUCCESSTEXT", "Somit ist der Text in der Zwischenablage gespeichert.");

 }


 /**
  * Daten in einem TXT-Datei erstellen und Downloden
  */
 function datenDownload() {
    var downData    = document.getElementById('myDaten').innerText;
    var downLinks   = document.getElementById('downlinks');
    var linksText   = document.getElementById('linkstext');
    if(downLinks.href) {
        URL.revokeObjectURL(downLinks.href);
    }

    // BLOB object erstellen
    var blob = new Blob([downData], {type: "text/plain"});
    var txtname = "myDaten.txt";

    // Alles auf den Link schmeißen
    downLinks.href = URL.createObjectURL(blob);
    downLinks.download = txtname;
    downLinks.style = "pointer-events: none; color: red";
    linksText.textContent = "Heruntergeladen";

    msgVisible("SUCCESSPOPUP", "SUCCESSTEXT", "Heruntergeladene Dateien sind im Ordner Downloads gespeichert");
 }


 /**
  * Daten Drucken
  */
 function datenDrucken() {

    window.print();
    msgVisible("SUCCESSPOPUP", "SUCCESSTEXT", "Daten sind bereit zum Dr&#252;cken");
 }