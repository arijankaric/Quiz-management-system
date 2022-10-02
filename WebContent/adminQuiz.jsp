<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/all.min.css" />
<script src="../scripts/jquery-3.6.1.min.js"></script>
<link rel="stylesheet" href="../css/quizStyle.css">

<script src="../scripts/index.min.js" defer></script>
<script src="../scripts/adminQuiz.js" defer></script>

</head>
<body>
	<script>
		const quizId = <%=session.getAttribute("quizId")%>
		console.log("quizId:", quizId)
	</script>
	<div class="start_btn" id="startDiv">
		<button id="startBtn">Start Quiz</button>
	</div>
	
	<div id="quizDiv" class="quiz_box activeQuiz">
		<header>
			<div id="quizTitle" class="title">Awesome Quiz Application</div>
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
				<div class="option" onclick="optionSelected(this)">
					<span id="firstAnswer"></span>
				</div>
				<div class="option" onclick="optionSelected(this)">
					<span id="secondAnswer"></span>
				</div>
				<div class="option" onclick="optionSelected(this)">
					<span id="thirdAnswer"></span>
				</div>
				<div class="option" onclick="optionSelected(this)">
					<span id="fourthAnswer"></span>
<!-- 					<div class="icon tick"> -->
<!-- 						<i class="fas fa-check"></i> -->
<!-- 					</div> -->
				</div>
			</div>
		</section>
		<footer>
			<div class="total_que">
				<span><p id="currQues">1</p> of <p id="totQues">5</p> Questions, <p id="onlineClients"></p></span>
			</div>
			<button id="nextBtn" class="next_btn show">Next Que</button>
		</footer>
	</div>
	<canvas id="my-canvas"></canvas>
</body>
</html>