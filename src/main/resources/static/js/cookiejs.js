"use strict";

/**
 *    <code>
 *        Anbindung Möglichkeit
 *        echo'<button onclick="setCookie(\'hammer\','.$val.',1826)">Cookie setzen<button>';
 *        echo'<img src="...Link" onclick="setCookie(\'hammer\','.$val.',1826)">';
 *        echo'<button onclick="cokisetzen('.$datum.')"></button>';
 *        echo'<input onclick="cokisetzen(\'loggedIn\','.$val.')" </input>';
 *        echo '<script type="text/javascript">setCookie(\'Paul\',\'123456789\', 3);</script>';
 *        echo '<script type="text/javascript">
 *                    document.cookie = "loggedIn="+'.$token.'+"; expires=Thu, 18 Dec 2033 12:00:00 UTC";
 *                </script>';
 *    </code>
 */


  /* Überprüfen Browserunterstützung für cookie */
function cookieEnabled(){
    if(navigator.cookieEnabled === false ){
        document.getElementById("cookiebox").style.display = "block";
        }
}


/**
*	setCookie: setzt neue cookie
*	muss zugesendet sein: Name, value und
*	xday = in Tagen ( 5 = für 5 Tage...)
*  expires + ";path=/";
*/
function setCookie(name, value, xday) {
    var d = new Date();
	d.setTime(d.getTime() + (xday * 24 * 60 * 60 * 1000));
	var expires = "expires="+d.toUTCString();
	document.cookie = name + "=" + value + ";" + expires;

  	// cookie Richtlinien schlißen
  	var cc = document.cookie.search(name) !== -1 ? true : false;
  	if(cc == true)
  	{
  		document.getElementById('cookiebox').style.display = 'none';
  	}
}



/**
*	getCookie: prüft ob mit gleichen Namen cookie vorhanden ist
*	z.b.s getCookie('hammer'); function abrufen. Fertig
*/

function getCookie(name)
{
  var na = name + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(na) == 0) {
      return c.substring(na.length, c.length);
    }
  }
  return null;
}

// Cookie Löschen
function deleteCookie(name)
{
   document.cookie = name + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
}