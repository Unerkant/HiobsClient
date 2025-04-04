 'use strict';

/**
 *   Den 10.03.2025
 */

        /**
         *  Zeigt Fragment in 2 reihe an, wie Info oder Alle Setting Einstellungen(rightColumn -> click)
         *  VORAUSSETZUNG: link zu PostMapping und User Token
         *  BENUTZT: von msg.html + Alle Setting click für die 2 reihe
         *      z.b.s. User Info
         *      1. Start: msg.html/Header/User Name Zeile: ~30
         *         data-th-onClick="userInfos('/userinfo',[[${msgToken}]])"
         *      2. Empfangen:  @PostMapping(path = "/userInfo") (UserInfoController)
         *      3. slider translate reine css, chat.css/index.html -> rightColumn/userInfo
         *      4. anzeige über msg.html in 2 reihe
         *  KURZE BESCHREIBUNG: mit einem click wird an die javascript function der @PostMapping-Link und
         *                      der User Token als parameter übergeben, dann in javascript wird mit den Request an der
         *                      Controller token gesendet und den Controller die Nötige Daten gesammelt und in ????.html
         *                      geschrieben, der html-datei wird als return an die javascript als response erhalten und
         *                      schliesslich mit innerHTML id der #OTHERFRAGMENT angezeigt, gleichzeitig wird die
         *                      translate-effect aktieviert... gleich hier unten Zeile: 42
         */

 var otherFragment = document.getElementById('OTHERFRAGMENT');

 function otherRequest(url, param) {

	var xhr = new XMLHttpRequest();
	xhr.open("POST", url, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.onreadystatechange = function(){
		if(this.readyState === XMLHttpRequest.DONE && this.status === 200){

            otherFragment.innerHTML = xhr.responseText;
		    otherShow();
		            //otherFragment.replaceWith(xhr.responseText);
		            console.log('DONE: ' + xhr.responseText);
					 //alert(xhr.responseText);
		}
	}
	xhr.send("param="+param);
	//xhr.send("hund=Dog&name=Sam");
 }

 /* Fragment 2 reihe anzeigen, nach dem response von UserInfoController */
 function otherShow() {
    otherFragment.classList.add('otherFragmentShow');
 }
 /* Fragment 2 reihe schliessen */
 function otherHide() {
    otherFragment.classList.remove('otherFragmentShow');
 }

