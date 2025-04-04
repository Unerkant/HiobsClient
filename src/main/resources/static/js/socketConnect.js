 'use strict';

/**
 *  Den 30.09.2024
 */


/**
 * BENUTZT: index.html
 */
 var connectAnzeige = document.getElementById('connectAnzeige');
 var connectBox     = document.getElementById('connectBox');
 var messageBox     = document.getElementById('messageBox');
 var mitWemVerbunden= document.getElementById('mitWemVerbunden');
 var recipientName  = document.getElementById('recipientName');
 var messageText    = document.getElementById('messageText');
 var messageAusgabe = document.getElementById('messageAusgabe');

 var stompClient    = null;
 var userName       = null;

 function onConnect() {

    if(stompClient != null) {
        stompClient.disconnect();
    }

    userName = document.getElementById('userName').value.trim();

    var socket = new SockJS("http://194.164.63.85:8080/register");
    //var socket = new SockJS("http://localhost:8080/register");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        stompClient.subscribe('/messages/receive/' + userName, function (msg) {
            var meineMsg = JSON.parse(msg.body);

            console.log("Message: " + meineMsg);
            showMessage(meineMsg);

        });

        if(userName) {
            onConnected();
        }

    }, function(err) {
        // Fehler Ausgeben + reconnect einbauen
        console.log("Connect: " + err);
    });
 }

 function sendeMessage(recipient, text) {
    event.preventDefault();

    console.log("Sende Message: " + userName + "/" + recipient + "/" + text);
    stompClient.send("/app/messages", {}, JSON.stringify({'name': userName, 'text': text, 'recipient': recipient}));

 }

 function showMessage(msg) {

    console.log("" + msg.name);
    messageAusgabe.innerHTML += "<tr><td>"+msg.name+"</td><td colspan='2'>"+msg.text+"</td></tr>";
    //messageAusgabe.innerHTML = "" + msg.name+":" + msg.text;
 }

 function onConnected() {

    connectAnzeige.innerHTML = "<b class='leuchtgrun'>mit Server Verbunden</b>";
    connectBox.style.display = 'none';
    messageBox.style.visibility = 'visible';
    mitWemVerbunden.innerHTML = "Registriert als:  <b class='twilight'>"+ userName +"</b>";
 }