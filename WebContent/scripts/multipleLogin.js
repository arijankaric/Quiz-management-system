let playId = document.getElementById("playForm");
let loginId = document.getElementById("loginForm");
let registerId = document.getElementById("registerForm");
let btnId = document.getElementById("btn");
let heroClass = document.querySelector('.hero');
let wrapperBottomId = document.getElementById("wrapper_bottom");

const images = ['imgs/quiz.jpg', 'imgs/admin.jpg'];

// 		function fadeInFunction(bgImage) {
// 			heroClass.style.backgroundImage = bgImage;
// 			heroClass.style.animation = "fadeIn 0.5s 1";
// 		}

const changeImage = function(id, image) {
	$(id).css('background-image', 'url(' + image + ')');
};

function changeBackground() {
	$('#wrapper_bottom').animate({
		"opacity": 1
	}, 500);
}

function registerAnimation() {
	playId.style.left = "-850px";
	loginId.style.left = "-400px";
	registerId.style.left = "50px";
	btnId.style.left = "220px";
	// 			heroClass.style.animation = "fadeOut 0.5s 1";
	// 			heroClass.style.backgroundImage = "linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('imgs/admin.jpg')";
	// 			const myTimeout = setTimeout(fadeInFunction, 500, "linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('imgs/admin.jpg')");

	if (wrapperBottomId.style.backgroundImage !== "url(\"imgs/admin.jpg\")") {
		$("#wrapper_bottom").css("opacity", 0);
		changeImage('#wrapper_bottom', images[1]);
		changeBackground();
	}
}

function loginAnimation() {
	playId.style.left = "-400px";
	loginId.style.left = "50px";
	registerId.style.left = "500px";
	btnId.style.left = "110px";
	// 			heroClass.style.backgroundImage = "linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('imgs/admin.jpg')";
	// 			heroClass.style.animation = "animateAdmin 2s ease-in-out";

	// 			heroClass.style.animation = "fadeOut 0.5s 1";
	// 			heroClass.style.animation-timing-function = "ease-in-out";

	// 			console.log("fadeOut");
	// 			const myTimeout = setTimeout(fadeInFunction, 500, "linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('imgs/admin.jpg')");

	if (wrapperBottomId.style.backgroundImage !== "url(\"imgs/admin.jpg\")") {
		// 				console.log(wrapperBottomId.style.backgroundImage);
		$("#wrapper_bottom").css("opacity", 0);
		changeImage('#wrapper_bottom', images[1]);
		changeBackground();
	}

}

function playAnimation() {
	playId.style.left = "50px";
	loginId.style.left = "500px";
	registerId.style.left = "950px";
	btnId.style.left = "0px";
	// 			heroClass.style.transition = "background-image 3s";
	// 			heroClass.style.animation = "animateClient 2s ease-in-out";
	// 			heroClass.style.animation = "fadeOut 0.5s 1";
	// 			heroClass.style.animation-timing-function = "ease-in-out";

	// 			console.log("fadeOut");
	// 			const myTimeout = setTimeout(fadeInFunction, 500, "linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)), url('imgs/quiz.jpg')");

	if (wrapperBottomId.style.backgroundImage !== "url(\"imgs/quiz.jpg\")") {
		$("#wrapper_bottom").css("opacity", 0);
		changeImage('#wrapper_bottom', images[0]);
		changeBackground();
	}
}

playAnimation();

function playForm() {
	 e.preventDefault();
	
	let quizCode = document.getElementById("quizCode");
	let nickname = document.getElementById("nickname");
	let rememberNickname = document.getElementById("rememberNickname");


	let request = $.ajax({
		url: "ClientLogin",
		type: "POST",
		data: 
		{ 
			quizCode: quizCode,
			nickname: nickname,
			rememberNickname: rememberNickname 
		},
		contentType: "application/json",
		dataType: "appication/json"
	});
	
	request.done(function(msg) {
//		$("#log").html(msg);
		console.log(msg);
	});

	request.fail(function(jqXHR, textStatus) {
		alert("Request failed: " + textStatus);
	});
}

$(document).on("submit", "#loginForm", function(event) {
    let $form = $(this);

    $.post($form.attr("action"), $form.serialize(), function(response) {
	
		console.log(response);
		if(response.success !== "true"){
//			console.log(response.login);
		}
		else if(response.success === "true"){
			window.location.replace("admin/index");
		}
		else{
//			console.log(response);
		}
    });

    event.preventDefault(); // Important! Prevents submitting the form.
});

function loginForm(event) {
	console.log("loginForm");
//	e.preventDefault();
	
//	let username = document.getElementById("loginUsername");
//	let password = document.getElementById("loginPassword");
//	let rememberMe = document.getElementById("rememberMe");


	let request = $.ajax({
		url: "AdminLogin",
		type: "POST",
		data: { 
			username: username,
			password: password,
			rememberMe: rememberMe 
		},
		contentType: "application/json",
		dataType: "application/json"
	});

	request.done(function(msg) {
		console.log(msg.success);
		if(msg.success !== "true")
		{
			console.log(msg.login);
		}
//		$("#log").html(msg);
	});

	request.fail(function(jqXHR, textStatus) {
		alert("Request failed: " + textStatus);
	});
	
	reuest.finally(function()
	{
		console.log("finally Login");
	});
	
}

function signUpForm() {
	event.preventDefault();
	
	let password = document.getElementById("registerPassword");
	let passwordConfirm = document.getElementById("registerConfirmPassword");
	
	if(password !== passwordConfirm)
	{
		//
		return;
	}
	
	let username = document.getElementById("registerUsername");
	
	// check if such username in dbs
	
	let email = document.getElementById("registerEmail"); // check if email is email
	
	let TOS = document.getElementById("TOS"); // check if TOS is true


	let request = $.ajax({
		url: "AdminSignUp",
		type: "POST",
		data: 
		{ 
			username: username,
			password: password,
			email: email 
		},
		contentType: "application/json",
		dataType: "application/json"
	});
	
	request.done(function(msg) {
		$("#log").html(msg);
	});

	request.fail(function(jqXHR, textStatus) {
		alert("Request failed: " + textStatus);
	});
}


		// 		var images = document.getElementsByClassName("test");

		// 		for (var i = 0; i < images.length; ++i) {
		// 			images[i].style.opacity = 1;
		// 		}

		// 		/* This function changes the opacity of
		// 		current image at regular intervals*/
		// 		function transition() {
		// 			return new Promise(function(resolve, reject) {

		// 				// del is the value by which opacity is
		// 				// decreased every interval
		// 				var del = 0.01;

		// 				// id stores ID of setInterval
		// 				// this ID will be used later
		// 				// to clear/stop setInterval
		// 				// after we are done changing opacity
		// 				var id = setInterval(changeOpacity, 10);

		// 				// changeOpacity() decreasing opacity of
		// 				// current image by del
		// 				// when opacity reaches to 0, we stops/clears
		// 				// the setInterval and resolve the function
		// 				function changeOpacity() {
		// 					images[cur].style.opacity -= del;
		// 					if (images[cur].style.opacity <= 0) {
		// 						clearInterval(id);
		// 						resolve();
		// 					}
		// 				}
		// 			})
		// 		}