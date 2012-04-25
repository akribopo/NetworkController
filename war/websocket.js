var socket;

function connect() {
    var host = "ws://127.0.0.1:8080/NetworkController/networkcontroller/websocket";
    var protocol = "uberdust";
    var encodedProtocol = "uberdust";

    try {

        if (!("WebSocket" in window)) {
            forwardMessage("You have a browser that does not support Websockets!");
            if (!("MozWebSocket" in window)) {
                forwardMessage("You have a browser that does not support MozWebsockets!");
                return -1;
            }
            else {
                socket = new MozWebSocket(host, protocol);
                forwardMessage('You have a browser that supports MozWebSockets');
            }
        }
        else {
            forwardMessage(encodedProtocol);
            socket = new WebSocket("ws://127.0.0.1:8080/NetworkController/networkcontroller/websocket", "uberdust");
            forwardMessage('You have a browser that supports WebSockets');
        }
        socket.onopen = function () {
            forwardMessage('socket.onopen ');
        }

        socket.onmessage = function (msg) {
            forwardMessage(msg.data);
        }

        socket.onclose = function () {
            forwardMessage("socket.onclose")
        }

    } catch (exception) {
        forwardMessage('Error' + exception + "");
    }


    function send(text) {

        if (text == "") {
            forwardMessage('Please enter a message' + "");
            return;
        }
        try {
            socket.send(text);
            forwardMessage('Sent: ' + text + "")
        } catch (exception) {
            forwardMessage('Exception ' + "");
        }
    }
}
function disconnect() {
    socket.close();
    forwardMessage("Connection Closed!");
}
