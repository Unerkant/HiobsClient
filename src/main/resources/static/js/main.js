 'use strict';

/**
 *  Den 30.09.2024
 */

 var connectAnzeige = document.querySelector('#connectAnzeige');
 var connectBox     = document.querySelector('#connectBox');
 var messageBox     = document.querySelector('#messageBox');
 var mitWemVerbunden= document.querySelector('#mitWemVerbunden');
 var recipientName  = document.querySelector('#recipientName');
 var messageText    = document.querySelector('#messageText');
 var messageAusgabe = document.querySelector('#messageAusgabe');

 var stompClient    = null;
 var userName       = null;

 var colors         = [
    '#2196F3', '#32C787', '#00BCD4', '#FF5652',
    '#FFC107', '#FF85AF', '#FF9800', '#39BBB0'
 ];



 function onConnect() {

    if(stompClient != null) {
        stompClient.disconnect();
    }

    userName = document.querySelector('#userName').value.trim();

    var socket = new SockJS("http://localhost:8080/register");
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