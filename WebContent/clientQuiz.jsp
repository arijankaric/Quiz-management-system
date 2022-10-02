<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/all.min.css" />
<script src="./scripts/jquery-3.6.1.min.js"></script>
<link rel="stylesheet" href="css/quizStyle.css">
<script src="scripts/clientQuiz.js" defer></script>
</head>
<body>
	<div class="start_btn">
		<button>Start Quiz</button>
	</div>

	<div id="quizDiv" class="quiz_box activeQuiz">
		<header>
			<div id="quizTitle" class="title"></div>
			<div id="timer" class="timer">
				<div class="time_left_txt">Time Off</div>
				<div class="timer_sec">00</div>
			</div>
			<div class="time_line" style="width: 550px;"></div>
		</header>
		<section id="leaderboard">
			<div class="que_text">
				<span id="question"></span>
			</div>
			<div class="option_list">
				<div class="option" onclick="handleClick(0)">
					<span class="" id="firstAnswer"></span>
				</div>
				<div class="option" onclick="handleClick(1)">
					<span class="" id="secondAnswer"></span>
				</div>
				<div class="option" onclick="handleClick(2)">
					<span class="" id="thirdAnswer"></span>
				</div>
				<div class="option" onclick="handleClick(3)">
					<span class="" id="fourthAnswer"></span>
					<!-- 					<div class="icon tick"> -->
					<!-- 						<i class="fas fa-check"></i> -->
					<!-- 					</div> -->
				</div>
			</div>
		</section>
		<footer>
			<div class="total_que">
				<span><p id="currQues">1</p> of
					<p id="totQues">5</p> Questions,
					<p id="onlineClients"></p></span>
			</div>
			<button id="nextBtn" class="next_btn show">Submit</button>
		</footer>
	</div>
	<canvas id="my-canvas"></canvas>
	<script>
		const clientNickname = '<%=session.getAttribute("nickname").toString()%>'
		const quizCode = '<%=session.getAttribute("quizCode").toString()%>'
		console.log('clientNickname:', clientNickname)
		console.log('quizCode:', quizCode)
	</script>
</body>
</html>