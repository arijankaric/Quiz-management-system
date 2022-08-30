let ws = new WebSocket("ws://localhost:8080/websocket");
ws.onopen = function (ev) {
    console.log("New client connected!");
};
ws.onclose = function (ev) {
    console.log("Cliend disconnected!");
};
ws.onerror = function (ev) {
    console.error("Error in communication: ", ev);
};
ws.onmessage = function (msg) {
    document.querySelector('#clients-count').setAttribute('data-badge', msg.data);
    console.log("Message received: " + msg.data);
};