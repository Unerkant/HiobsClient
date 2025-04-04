 'use strict';

 /**
  * Den 22.01.2024
  */

  var chatBox           = document.getElementById('CHATBOX');
  var leftColumn        = document.getElementById('LEFTCOLUMN');
  var rightColumn       = document.getElementById('RIGHTCOLUMN');
  var rightColumnOpen   = document.getElementById('RIGHTCOLUMNOPEN');

 /**
  *     WENN WIRD DER BROWSER AKTUALISIERT + FUNCTION NUR UNTER 650PX
  *
  * Automatische start function, z.b.s. bei Browser Aktualisieren(keine slider transform - effect)
  * ACHTUNG: leftOpend() & rightOpend() nicht Löschen,
  *          weil: wenn Browser unter 650px geschrumpft wird und dann noch Aktualisiert da wird der leftColumn
  *                ohne leftOpend() function, auf 300px zurück gestellt...
  */
  (function() {

    var width = chatBox.offsetWidth;
    if(width < 650) {

        if(rightColumnOpen === null) {

            leftOpend();
            console.log('auto function unter 650px, column leer IF: ' + width+"px");

        } else {

            leftWidths();
            rightOpend();
            console.log('auto function unter 650px, column Voll IF: ' + width+"px");

        }

    } else {
        console.log('nichts Machen::: auto function über 650px ELSE: ' + rightColumnOpen);
    }
  }());

 /**
  *     WENN WIRD DER BROWSER VERKLEINERT/VERGRÖßERT
  *
  * ACHTUNG: Automatische anzeige von left & right bei Browser Verkleinerung
  *         1. width < 650px + right leer,      Anzeige: left width 100% z-index 1
  *         2. width < 650px + right gefüllt,   Anzeige: right width 100% z-index 1,
  *                                                       left width 100% z-index 0
  *         3. width > 650px,                   Anzeige: Standard anzeigen(delete alle open class)
  */
  window.addEventListener("resize", function () {

    /* IF < 650px & ELSE: > 650px */
    if(innerWidth < 651) {

        /* IF = right leer & ELSE = right gefüllt */
        if(rightColumnOpen === null) {

            sliderLeftOpend();
            console.log('event Listener unter 650px, right Leer IF: ' + rightColumnOpen);
        } else {

            rightOpend();
            leftWidths();
            console.log('event Listener unter 650px, right Voll ELSE: ' + rightColumnOpen);
        }

    } else {

        if(rightColumnOpen === null) {

            leftStandard();
             console.log('event Listener über 650px IF');
        } else {
            rightStandard();
            chatStandard();
            console.log('event Listener über 650px ELSE');
        }

    }

  });
 /**
  * Schließt die Rechte(MSG oder Kanal) Seite wenn unter 650px ist....
  * ACHTUNG: schließt mit der slider-animation den  #RIGHTCOLUMN und danach aktualisiert die
  *          ganze Seite um die daten in den #RIGHTCOLUMN zu löschen
  */
  function rightHide(option) {

    switch(option) {
        case 'msg':         leftWidths(); slideRightHide(); reMsgStart();
                            break;
        case 'setting':     leftWidths(); slideRightHide(); reSettingStart();
                            break;
        default: // nicht machen
    }

    console.log('Function rightHide')
/*    var width = chatBox.offsetWidth;
    if(width < 650) {
        leftWidths();
        slideRightHide();
        reStart();
        console.log('chatHide unter 650 IF: ' + elementID);
    } else {
        console.log('chatHide über 650 ELSE -> nicht machen: ' + elementID)
    }*/
  }

   /* ************* Hilfe function zum Oberer 3 Funktionen ****************** */

  /* width über 650px, right leer */
  function leftStandard() {
    leftColumn.classList.remove('sliderLeftOpen');
    leftColumn.classList.remove('leftOpen');
  }

  /* width über 650px, right gefüllt */
  function rightStandard() {
    rightColumn.classList.remove('rightOpen');
    leftColumn.classList.remove('leftWidth');
  }

  /* width unter 650px, right leer */
  function sliderLeftOpend() {
    leftColumn.classList.add('sliderLeftOpen');
  }

  /* widht unter 650px, right gefüllt, left width auf 100% */
  function leftWidths() {
    leftColumn.classList.add('leftWidth');
  }

  /* Browser Aktualisieren: width unter 650px, right leer  */
  function leftOpend() {
    leftColumn.classList.add('leftOpen');
  }

  /* Browser Aktualisieren: width unter 650px, right gefüllt*/
  function rightOpend() {
    rightColumn.classList.add('rightOpen');
  }

  /* Schließt die Rechte-Column, unter 650px */
  function slideRightHide() {
    rightColumn.classList.add('sliderRightHide');
  }

  /* auf Standard werte umstellen */
  function chatStandard() {
    rightColumn.classList.remove('sliderRightShow');
    leftColumn.classList.remove('leftWidth');
  }

  /* reStart verzögerung, ausführen nach dem translate-animation(rightHide..) */
  function reMsgStart() {
    setTimeout(function() {
           window.location.href = '/msg';
    }, 300);
  }

  function reSettingStart() {
    setTimeout(function() {
           window.location.href = '/setting';
    }, 300);
  }




