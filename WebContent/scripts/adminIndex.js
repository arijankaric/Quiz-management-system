function createChatClient() {
	'use strict';
	
//	var username = params.username;
//	var container = params.container;

	const activeUsers = document.getElementById("activeUsers");
	
	const wsUrl = "ws://localhost:8080/projekat/quizGameplay";
//	var userId = "user-" + randomString();
//	var elements = {        
//		conversation: container.querySelector("#conversation"),
//		message: container.querySelector("#text"),
//		send: container.querySelector("#send"),		
//		status: container.querySelector("#status")
//	};

//	showContainer(container);
	
	let client = new WebSocket(wsUrl);    

	client.onopen = function(event) {
		console.log(event);
		console.log("connected to /quizGameplay");
    };
 
    client.onmessage = function (event) { 
		let json = JSON.parse(event.data);
   
		activeUsers.innerText = json.quizId;	
    	console.log("Event: ", json["quizId"]);
    };
    
    client.onclose = (function(event){
		console.log(event);
		console.log("disconnected from /quizGameplay");
	});
 
//    elements.send.onclick = function (event) {
//    	if (elements.message.value == "") {
//    		return;
//    	}
//        sendMessage(elements.message.value);
//        elements.message.value = "";
//    };
    
//    elements.message.addEventListener("keyup", function(event) {  
//      if (event.keyCode === 13) {    
//        event.preventDefault(); 
//        elements.send.click();
//      }
//    });
 
    function sendMessage() {
    	let message = {
			typeOfMessage: 3
//            username: username,
//			userId: userId,
//			text: text
		};    	
        client.send(JSON.stringify(message));
    }
}

createChatClient();