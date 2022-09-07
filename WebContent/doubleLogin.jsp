<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/doubleLogin.css">
<script src="//code.jquery.com/jquery-1.11.1.min.js" defer></script>
<script src="scripts/multipleLogin.js" defer></script>
<title>multipleLogin</title>
</head>
<body>
	<div class="hero">
		<div class="form-box">
			<div class="button-box">
				<div id="btn"></div>
				<button type="button" class="toggle-btn" onclick="playAnimation()">Play</button>
				<button type="button" class="toggle-btn" onclick="loginAnimation()">Log
					in</button>
				<button type="button" class="toggle-btn" onclick="registerAnimation()">Register</button>

			</div>

			<div class="social-icons">
				<img alt="" src="imgs/fb.png"> <img alt="" src="imgs/tw.png">
				<img alt="" src="imgs/gp.png">
			</div>

			<form id="playForm" action="ClientLogin" class="input-group">
				<input autofocus id="quizCode" name="quizCode" type="text" class="input-field" placeholder="Quiz Code"
					required> <input id="nickname" type="text" name="nickname" class="input-field"
					placeholder="Nickname" required> <input name="remmemberNickname" id="rememberNickname" type="checkbox"
					class="check-box"><span>Remember Nickname</span>
				<button type="submit" name="submit" class="submit-btn" onClick="playHandler()">Play Quiz</button>
			</form>

			<form id="loginForm" action="adminLogin" method="post" class="input-group">
				<input id="loginUsername" type="text" name="username" class="input-field" placeholder="Username"
					required> <input id="loginPassword" type="password" name="password" class="input-field"
					placeholder="Enter Password" required> <input name="rememberMe" id="rememberMe"
					type="checkbox" class="check-box"><span>Remember
					Password</span>
				<button type="submit" name="submit" class="submit-btn"">Log in</button>
			</form>

			<form id="registerForm" action="AdminSignUp" class="input-group" method="post">
				<input id="registerUsername" type="text" name="username" class="input-field" placeholder="Username"
					required> <input name="email" id="registerEmail" type="email" class="input-field"
					placeholder="Email" required> <input id="registerPassword" type="password"
					class="input-field" placeholder="Password" name="password" required> <input id="registerConfirmPassword"
					type="password" class="input-field" placeholder="Confirm Password" name="passwordConfirm"
					required> <input name="TOS" id ="TOS" type="checkbox" class="check-box"><span>I
					agree to the terms & conditions</span>
				<button type="button" class="submit-btn" onClick="signUpHandler()">Register</button>
			</form>
		</div>
		<div id="wrapper_bottom"
			style="z-index: -1; top: 0; height: 100%; position: absolute; width: 100%; background-size: cover">
		</div>
	</div>
</body>
</html>