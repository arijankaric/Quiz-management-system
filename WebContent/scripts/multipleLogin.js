const playId = document.getElementById("playForm");
const loginId = document.getElementById("loginForm");
const registerId = document.getElementById("registerForm");
const btnId = document.getElementById("btn");
const heroClass = document.querySelector('.hero');
const wrapperBottomId = document.getElementById("wrapper_bottom");

const quizCodeInput = document.getElementById("quizCode");
const nicknameInput = document.getElementById("nickname");
const rememberNicknameCheckBox = document.getElementById("rememberNickname");

const images = ['imgs/quiz.jpg', 'imgs/admin.jpg'];

// 		function fadeInFunction(bgImage) {
// 			heroClass.style.backgroundImage = bgImage;
// 			heroClass.style.animation = "fadeIn 0.5s 1";
// 		}

const changeImage = function(id, image) {
	$(id).css('background-image', 'url(' + image + ')');
};

//function sleep(ms) {
//	return new Promise(resolve => setTimeout(resolve, ms));
//}
//
//async function sleepFor(howLong) {
//	for (let i = 0; i < howLong; i++) {
//		console.log(`Waiting ${i} seconds...`);
//		await sleep(i * 1000);
//	}
//	console.log('Done');
//}

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

$('#playBtn').click(function(event) {
	console.log('playForm');
	event.preventDefault();

	const quizCode = quizCodeInput.value;
	const nickname = nicknameInput.value;
	const rememberNickname = rememberNicknameCheckBox.checked;

	const data = $('#playForm').serialize();
	console.log('data:', data);
	console.log('quizCode:', quizCode);
	console.log('nickname:', nickname);
	console.log('rememberNickname:', rememberNickname);

	const request = $.ajax({
		url: "./clientLogin",
		type: "GET",
		data:
		{
			quizCode: quizCode,
			nickname: nickname,
			rememberNickname: rememberNickname
		},
		contentType: "application/json",
		dataType: "text"
	});
	
	request.then(function(msg) {
		console.log('then:', msg);
		if(msg == 1) {
			const urlParams = new URLSearchParams(window.location.search);

			urlParams.set('nickname', nickname);
			urlParams.set('quizCode', quizCode);
			window.location.search = urlParams;
			window.location.href = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/') + 1) + 'clientQuiz';
		} else if(msg == 0) {
			console.log('msg == 0');
			quizCodeInput.placeholder = 'Invalid Quiz Code'
			quizCodeInput.value = ''
		} else {
			console.log("shouldn't happen");
		}
	})

	request.done(function(msg) {
		console.log('done:', msg);
	});

	request.fail(function(jqXHR, textStatus) {
		alert("Request failed: " + textStatus);
	});
})

$('#loginBtn').click(function(event) {
	event.preventDefault();
	console.log("loginBtn clicked");
	const data = $('#loginForm').serialize();
	console.log('data:', data);
	$.ajax({
		url: './adminLogin',
		type: 'POST',
		dataType: 'json',
		data: data,
		success: function(data) {
			//					location.url = './admin/index';
			window.location.href = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/') + 1) + 'admin/index';
			console.log("data: ", data);
			// ... do something with the data...
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.error("XMHttpRequest:", XMLHttpRequest);
			console.error("textStatus:", textStatus);
			console.error("errorThrown:", errorThrown);
		}

	});
});

//$(document).on("submit", "#loginForm", function(event) {
//	console.log("loginForm2");
//    let $form = $(this);
//    
//    const username = document.getElementById("loginUsername").value;
//	const password = document.getElementById("loginPassword").value;
//	const rememberMe = document.getElementById("rememberMe").value;
//	
//	console.log("username: ", username);
//	console.log("password: ", password);
//	console.log("rememberMe: ", rememberMe);
//
//
//	let request = $.ajax({
//		url: "AdminLogin",
//		type: "POST",
//		data: { 
//			username: username,
//			password: password,
//			rememberMe: rememberMe 
//		},
//		contentType: "application/json",
//		dataType: "application/json"
//	});
//
//	request.done(function(msg) {
//		console.log(msg.success);
//		if(msg.success !== "true")
//		{
//			console.log(msg.login);
//		}
////		$("#log").html(msg);
//	});
//
//	request.fail(function(jqXHR, textStatus) {
//		alert("Request failed: " + textStatus);
//	});
//	
//	request.finally(function()
//	{
//		console.log("finally Login");
//	});
//
//    $.post($form.attr("action"), $form.serialize(), function(response) {
//	
//		console.log(response);
//		if(response.success !== "true"){
//			console.log("!true");
////			console.log(response.login);
//		}
//		else if(response.success === "true"){
//			console.log("true");
////			window.location.assign("/adminsndex");
//			console.log("Before: ");
//			console.log(location.href);
//			location.href = './projekat/admin/index';
//			console.log("After: ");
//			console.log(location.href);
////			window.location.href = "/admin/index";
//		}
//		else{
//			console.log("why?");
////			console.log(response);
//		}
//    });
//
//    event.preventDefault(); // Important! Prevents submitting the form.
//});

//function loginForm(event) {
//	console.log("loginForm1");
////	e.preventDefault();
//	
//	const username = document.getElementById("loginUsername").value;
//	const password = document.getElementById("loginPassword").value;
//	const rememberMe = document.getElementById("rememberMe").value;
//	
//	console.log("username: ", username);
//	console.log("password: ", password);
//	console.log("rememberMe: ", rememberMe);
//
//
//	let request = $.ajax({
//		url: "AdminLogin",
//		type: "POST",
//		data: { 
//			username: username,
//			password: password,
//			rememberMe: rememberMe 
//		},
//		contentType: "application/json",
//		dataType: "application/json"
//	});
//
//	request.done(function(msg) {
//		console.log(msg.success);
//		if(msg.success !== "true")
//		{
//			console.log(msg.login);
//		}
////		$("#log").html(msg);
//	});
//
//	request.fail(function(jqXHR, textStatus) {
//		alert("Request failed: " + textStatus);
//	});
//	
//	request.finally(function()
//	{
//		console.log("finally Login");
//	});	
//}

function signUpForm(event) {
	console.log('signUpForm');
	event.preventDefault();

	const password = document.getElementById("registerPassword");
	const passwordConfirm = document.getElementById("registerConfirmPassword");

	if (password !== passwordConfirm) {
		//
		return;
	}

	const username = document.getElementById("registerUsername");

	// check if such username in dbs

	const email = document.getElementById("registerEmail"); // check if email is email

	const TOS = document.getElementById("TOS"); // check if TOS is true


	const request = $.ajax({
		url: "./adminSignUp",
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
		console.log('msg:', msg);
	});

	request.fail(function(jqXHR, textStatus) {
		alert("Request failed: " + textStatus);
	});
}

if (typeOfForm === 'client') {
	playAnimation();
} else if (typeOfForm === 'admin') {
	loginAnimation();
} else {
	console.log('typeof typeOfForm:', typeof typeOfForm);
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