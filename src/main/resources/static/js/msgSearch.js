 'use strict';

 /**
  * Den 4.08.2025
  */

  (function() {
    //alert('Suchanfrage');
  }());

 /** *********** Search functionen ********** **/

 /**
  * ANZEIGE VON SUCH-FUNKTION IN MSG.HTML/HEADER
  * BESCHREIBUNG: die such-function wird in msg-header mit den class 'searchOpen' (chat.css) angezeigt
  *                 die function ist nur für den INPUT anzeigen/verstecken
  *
  * element -> #SEARCHFRAGMENT, msg.html Zeile: 13
  * clas    -> searchOpen, chat.css Zeile: 22
  * class   -> displayNone, main.css Zeile: 56
  * BESCHREIBUNG: der search-fragment ist mit scala(css) versteckt das wegen gibst Problemen
  * bei Verkleinerung unter 650px die slider function funktioniert nicht
  * LÖSUNG: includete search-fragment wird zusätzlich mit dem class 'displaNone' versteckt und
  * bei starten die suchfunktion wird der class 'displayNone' gelöscht und bei schliessen die
  * suchfunktion wieder aktiviert
  */
  function searchShow(element, clas, event) {

    let parent = document.getElementById(element);  /* #SEARCHFRAGMENT */
    parent.classList.remove('displayNone');
    let searchInput = document.querySelector('#SEARCHINPUT');
    searchInput.value = "";
    let ausgabeItem = document.querySelector('#AUSGABEITEM');
    ausgabeItem.innerHTML = "";
    ausgabeItem.setAttribute("style", "");

    let isOpen = (parent.className || '').indexOf(clas) > 0;
    if(!isOpen) {
        parent.className = (parent.className || '') + ' ' + clas;
        searchInput.focus();

    } else {
        parent.className = parent.className.replace(clas, '');
        parent.classList.add('displayNone');
    }

    /*event.stopPropagation();
    return false;*/
  }


  /**
   *    SELBER SUCH-FUNKTION MIT onKeyUp()
   *    BESCHREIBUNG: zugesendete json-array wird durchsucht und in dynamischen link angezeigt
   *
   *    data-th-onKeyUp="searchMessage('AUSGABEITEM', this.value )", msgsearchs.html Zeile: 20
   *    gefundene Nachricht anzeigen
   *    objectList -> zugesendete Json-Array zum untersuchen
   *    id -> <p #AUSGABEITEM>...</p>, msgsearch.html Zeile:34
   *    val -> Input Value
   *
   *    so sieht es zugesandte json-array
   *    Json Array: {"email":"example@example.com","other":"total","role":"admin"},
   *                {"email":"example@ex.com","other": "wainicht", "role": "user"},
   *                {"email": "example@example.com", "other": "einzeln", "role": "admin"},
   *                {"email": "example@example.com", "other": "keineahnung", "role": "admin"}
   *
   *    so sieht der json-array nach eval bearbeitung:
   *    obj -> JSON.parse: [object Object],[object Object],[object Object],[object Object]
   *
   *    ACHTUNG: zu Testen bitte admin oder user eingeben, weil role wird durchgesucht(Zeile 82)
   */
  function searchMessage(objectList, id, val) {

    //let json = JSON.stringify(objectList);
    let list = JSON.parse(objectList);
    let ausgabeItem = document.getElementById(id);

    if(val.length > 0) {

        ausgabeItem.setAttribute("style", "border-top: 1px solid #CCC; padding: .625rem 0; margin-top: 1rem; ");
        // wird spalte role durchgesucht,
        let result = list.find(local => local.role === val);
        if(result !== undefined) {

            let gefunden = "<a id='22' href='#myAnker' onClick='searchHover(this.id)' "
            +" class='jsSearchAusgabe nichtmarkieren boxSizing'>"
            + "<table class='jsSearchTab' cellpadding='0' cellspacing='0'><tr>"
            + "<td rowspan='3' class='jsSearchBild'>"
            + "<img src='/img/gastBlau.svg' class='jsSearchImg'>"
            + "</td>"
            + "<td class='jsSearchName textEllipsis'><span>" + result.email + "</span></td>"
            + "<td class='jsSearchDatum'>9.09.2025</td>"
            + "<tr></tr>"
            + "<td colspan='2' class='jsSearchText textEllipsis'><span>" + result.other + "</span></td>"
            + "</tr></table></a>";
            ausgabeItem.innerHTML = gefunden;
            console.log('Gefunden: ', list.lenght);
        } else {

            let keineErgebnis = "<p class='jsSearchKeinFund'>Keine Ergebnisse f&#252;r "
                                            +"&#34; <b class='jsSearchVal textEllipsis'>" + val + "</b> &#34;</p>";
            ausgabeItem.innerHTML = keineErgebnis;

        }
        /*for(let i=0; i < list.length; i++) {
            let object = list[i];
            console.log('Object: ', object);
            for(let prop in object) {
                fundItem.innerHTML = 'Email: ' + object.email + '<br> Role: ' + object.role;
            }
        }*/

    } else {
        ausgabeItem.innerHTML = "";
        ausgabeItem.setAttribute("style", "");
    }

  }

 /**
  * gefundene message hover setzen, msgsearch.html/<p id='AUSGABEITEM'
  */
  function searchHover(id) {
    console.log('Search Hover: ', id);
    let fundLink = document.getElementById(id);
    fundLink.classList.add('jsAusgabeVisited');
  }

 /** ************************ CALENDAR ****************************** **/

 /**
  * Calendar anzeigen
  */
  function searchCalendarShow(id, event) {

    const ausgabeItems  = document.getElementById(id);
    // calender anzeigen/schliessen
    ausgabeItems.style.display = (ausgabeItems.style.display === 'block' ? 'none' : 'block');

    ausgabeItems.innerHTML = "";
    const calendar = "<div class='jsSearchCalendar fragmentLayout nichtmarkieren'>"
        + "<div class='jsCalHeader'>"
            + "<button id='PREVBTN' class='flexCenter jsCalButton'>"
                + "<i class='icon-left icon-size20'></i>"
            + "</button>"
            + "<div id='CALJAHR' class='jsCalJahr'></div>"
            + "<button id='NEXTBTN' class='flexCenter jsCalButton'>"
                + "<i class='icon-rechts icon-size20'></i>"
            +"</button>"
        + "</div>"
        + "<div class='jsCalDays'>"
            + "<div class='jsCalday'>Mon</div>"
            + "<div class='jsCalday'>Die</div>"
            + "<div class='jsCalday'>Mit</div>"
            + "<div class='jsCalday'>Don</div>"
            + "<div class='jsCalday'>Fre</div>"
            + "<div class='jsCalday'>Sam</div>"
            + "<div class='jsCalday'>Son</div>"
        + "</div>"
        + "<div id='CALDATES' class='jsCalDates'></div>"
        + "<div class='jsCalAbbrechen'>"
        + "<button id='CALABBR' class='jsCalAbbrButton textRot finger'>Abbrechen</button>"
        + "</div>"
    + "</div><div class='leer0'></div>";

    ausgabeItems.innerHTML = calendar; // nach anzeigen des calendar ist es möglich der zugriff auf IDs

    const caljahr = document.querySelector('#CALJAHR');
    const caldates = document.querySelector('#CALDATES');
    const calprev = document.querySelector('#PREVBTN');
    const calnext = document.querySelector('#NEXTBTN');
    const calabbr = document.querySelector('#CALABBR');

    const aktuelleJahr  = new Date().toLocaleString('default', {year: 'numeric'});
    const aktuelleMonat = new Date().toLocaleString('default', {month: 'long'});
    const aktuelleTag   = new Date().getDate();

    let currentDate = new Date();
    const updateCalendar = () => {
        const currentYear    = currentDate.getFullYear();
        const currentMonth   = currentDate.getMonth();

        const firstDay      = new Date(currentYear, currentMonth, 0);
        const lastDay       = new Date(currentYear, currentMonth + 1, 0);
        const totalDay      = lastDay.getDate();
        const firstDayIndex = firstDay.getDay();
        const lastDayIndex  = lastDay.getDay();

        //const monthYearString = currentDate.toLocaleString('default', {month: 'long', year: 'numeric'});
        //caljahr.textContent = monthYearString;

        /* meine bearbeitung */
        const yearString = currentDate.toLocaleString('default', {year: 'numeric'});
        const monthString = currentDate.toLocaleString('default', { month: 'long'});

        caljahr.textContent = monthString +" "+yearString;

        /* meine bearbeitung */

        let calendarDaten = '';

        for(let i = firstDayIndex; i > 0; i-- ) {
            const prevDate = new Date(currentYear, currentMonth, 0 -i + 1);
            calendarDaten += `<div class="date inactive"> ${prevDate.getDate()} </div>`;
            //console.log("First Day: ", prevDate.getDate());
        }

        for(let i = 1; i <= totalDay; i++) {
            const date = new Date(currentYear, currentMonth, i);
            const activeClass = date.toDateString() === new Date().toDateString() ? 'active' : '' ;
            const disableClass = (i > aktuelleTag && yearString === aktuelleJahr && monthString === aktuelleMonat)
                                    ? 'noActive' : '' ;
            calendarDaten += `<a href="#12345" class="noStyle ${disableClass}">
                    <div class="date ${activeClass} ">${i}</div></a>`;
            //console.log("Aktuelle Datum: ", yearString +'/'+ aktuelleJahr);
        }

        for(let i = 1; i <= 7 - lastDayIndex; i++) {
            const nextDate = new Date(currentYear, currentMonth + 1, i);
            calendarDaten += `<div class="date inactive"> ${nextDate.getDate()} </div>`;
            //console.log("last Day: ", nextDate.getDate());
        }

        caldates.innerHTML = calendarDaten;

                    // zukunftige Monate sperren(next button sperren)
                    if(aktuelleJahr === yearString && aktuelleMonat === monthString) {

                        calnext.disabled = true;
                        return;

                    } else { calnext.disabled = false; }

    }

    /* Zurück Button */
    calprev.addEventListener('click', () => {
        currentDate.setMonth(currentDate.getMonth() - 1);
        updateCalendar();
    });
    /* Vor Button */
    calnext.addEventListener('click', () => {
        currentDate.setMonth(currentDate.getMonth() + 1);
        updateCalendar();
    });
    /* Abbrechen */
    calabbr.addEventListener('click', () => {
        ausgabeItems.style.display = 'none';
        const searchInput = document.querySelector('#SEARCHINPUT');
        searchInput.focus();
    });

    updateCalendar();
    //console.log("Object ID: ", caldates);

      /*event.stopPropagation();
      return false;*/

  }