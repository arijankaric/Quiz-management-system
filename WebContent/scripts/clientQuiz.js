let fetchedQuestions
let quizTitle
let clients = []
let myScore = 0
let currentQuestion
let answers
let quizId
let sendMsg
let timeToAnswer
let currentQuestionNumber = 0
let verifiedCorrectAnswers = false
let timeLeft
let clientId = null
let counter = null

//selecting all required elements
//const start_btn = document.querySelector(".start_btn button");
//const info_box = document.querySelector(".info_box");
//const exit_btn = info_box.querySelector(".buttons .quit");
//const continue_btn = info_box.querySelector(".buttons .restart");
//const quiz_box = document.querySelector(".quiz_box");
//const result_box = document.querySelector(".result_box");
//const option_list = document.querySelector(".option_list");
//const time_line = document.querySelector("header .time_line");
const timeText = document.querySelector(".timer .time_left_txt");
const timeCount = document.querySelector(".timer .timer_sec");
//const canvas = document.querySelector("#my-canvas");

// creating the new div tags which for icons
const tickIconTag = '<div class="icon tick"><i class="fas fa-check"></i></div>';
const crossIconTag = '<div class="icon cross"><i class="fas fa-times"></i></div>';

const nextBtn = document.getElementById('nextBtn')
const quizTitleDiv = document.getElementById('quizTitle')

const questionSpan = document.getElementById('question')
const firstAnswerSpan = document.getElementById('firstAnswer')
const secondAnswerSpan = document.getElementById('secondAnswer')
const thirdAnswerSpan = document.getElementById('thirdAnswer')
const fourthAnswerSpan = document.getElementById('fourthAnswer')

const currentQuestionParagraph = document.getElementById('currQues') // p - paragraph
const totalQuestionsParagraph = document.getElementById('totQues') // p - paragraph
const leaderboard = document.getElementById('leaderboard')

const availableSelections = [firstAnswerSpan, secondAnswerSpan, thirdAnswerSpan, fourthAnswerSpan]
const options = document.getElementsByClassName('option')
let currentlyOnline = 0
const onlineClientsParagraph = document.getElementById('onlineClients')
onlineClientsParagraph.innerText = "Online: " + currentlyOnline

function printQuestions() {
	for (let i = 0; i < fetchedQuestions.length; ++i) {
		console.log(fetchedQuestions[i])
	}
}

let savedLeaderboard

function hideElement(id) {
	let x = document.getElementById(id);
	x.style.display = "none"
}

function showElement(id) {
	let x = document.getElementById(id)
	x.style.display = "block"
}

function toggleElement(id) {
	let x = document.getElementById(id);
	if (x.style.display === "none") {
		x.style.display = "block";
	} else {
		x.style.display = "none";
	}
}

function revealAnswers() {
	for (let i = 0; i < answers.length; ++i) {
		if (answers[i].correct === true) {
			options[i].classList.add("correct");
			options[i].classList.remove("incorrect");
		} else {
			options[i].classList.add("incorrect");
			options[i].classList.remove("correct");
		}
	}
}

function handleClick(i) {
	//	console.log('Before:', availableSelections[i].classList)
	//	console.log(availableSelections[i].parentElement.classList)
	if (options[i].classList.contains('correct')) {
		options[i].classList.remove('correct')
		//		availableSelections[i].parentElement.classList.remove('selected')
		//		availableSelections[i].classList.add("correct");	
	}
	else {
		options[i].classList.add('correct')
		//		availableSelections[i].parentElement.classList.add('selected')
	}
	//	console.log('After:', availableSelections[i].classList)
	//	console.log(availableSelections[i].parentElement.classList)
	//	console.log('After:',availableSelections[i])

}

function unrevealAnswers() {
	$('#firstAnswer').siblings().remove();
	$('#secondAnswer').siblings().remove();
	$('#thirdAnswer').siblings().remove();
	$('#fourthAnswer').siblings().remove();
	for (let i = 0; i < answers.length; ++i) {
		options[i].classList.remove("correct");
		options[i].classList.remove("incorrect");
		options[i].classList.remove("selected");
	}
}

function createChatClient() {
	const wsUrl = "ws://localhost:8080/projekat/quizGameplay";
	const client = new WebSocket(wsUrl);

	// -1 => inalidQuizCode(Client entered invalid quiz code)
	// 0 => clientConnect, 
	// 1 => adminStartQuiz, 
	// 2 => adminDisconnect,
	// 3 => clientDisconnect(broadcasting clientId/clientNickname)
	// 4 => adminDeployQuiz
	// 5 => updateClientScore
	// 6 => nextQuestion
	// 7 => everyoneFinished(everyoneSubmittedTheirAnswer)
	// 8 => quizCode(sent from server to moderator)
	// 9 => quizId(sent from server to client)
	// 10 => getQuizId(sent from client to server; client is asking for quiz id of sent quiz code)
	// 11 => getClientId(sent from server to client) 

	//		private int typeOfMessage;
	//	private String text;
	//	private int senderId; // -2 => server, -1 => moderator 
	//	private String senderNickname; // null for server & moderator

	client.onopen = function(event) {
		console.log(event)
		//		sendGetQuizId()
		sendClientConnect()
	};

	function sendGetQuizId() {
		const msg = {
			typeOfMessage: 10,
			text: quizCode,
			senderId: clientId,
			senderNickname: clientNickname
		}
		sendMsg(msg)
	}

	function sendMsg(msg) {
		console.log('sendingMsg:', msg)
		client.send(JSON.stringify(msg))
	}

	function sendUpdateClientScore() {
		const msg = {
			typeOfMessage: 5,
			text: myScore,
			senderId: clientId,
			senderNickname: clientNickname
		}
		handleUpdateClientScore(msg)
		sendMsg(msg)
	}

	function handleClientConnect(msg) {
		++currentlyOnline
		onlineClientsParagraph.innerText = "Online: " + currentlyOnline
		const newPlayer = { id: msg.senderId, nickname: msg.senderNickname, score: msg.text }
		clients.push(newPlayer)
	}

	function handleClientDisconnect() {
		--currentlyOnline
		onlineClientsParagraph.innerText = "Online: " + currentlyOnline
	}

	function handleUpdateClientScore(msg) {
		let obj = clients.find(x => x.id == msg.senderId);
		if (obj === null || obj === undefined) {
			const newPlayer = { id: msg.senderId, nickname: msg.senderNickname, score: msg.text }
			clients.push(newPlayer);
		} else {
			const index = clients.indexOf(obj);
			obj.score = msg.text
			clients = clients.fill(obj, index, index);
		}
	}

	function handleViewHighScore() {
		displayLeaderboard()
	}

	function sendClientConnect() {
		const msg = {
			typeOfMessage: 0,
			text: quizCode,
			senderId: -3,
			senderNickname: clientNickname
		}
		sendMsg(msg)
	}

	function handleNextQuestion() {
		nextQuestion()
	}

	function handleQuizId(msg) {
		console.log("handleQuizId:", msg)
		quizId = msg.text
		fetchQuestions()
	}

	function handleAdminStartQuiz() {
		nextQuestion()
	}

	function handleAdminDisconnect() {

	}

	client.onerror = function(event) {
		console.log(event)
	}

	window.addEventListener("beforeunload", function(event) {
		client.close()
	});

	client.onmessage = function(event) {
		const msg = JSON.parse(event.data);
		console.log("Message received:", msg);
		const typeOfMessage = msg.typeOfMessage

		switch (typeOfMessage) {
			case 0:
				handleClientConnect(msg)
				break;
			case 1:
				handleAdminStartQuiz()
				break;
			case 2:
				handleAdminDisconnect()
				break;
			case 3:
				handleClientDisconnect(msg)
				break;
			case 5:
				handleUpdateClientScore(msg)
				break;
			case 6:
				handleNextQuestion()
				break;
			case 7:
				handleViewHighScore()
				break;
			case 9:
				handleQuizId(msg)
				break;
			case 11:
				handleClientId(msg)
				break;
			default:
				console.log("Received undefined typeOfMessage:", typeOfMessage)
		}
	};

	function handleClientId(msg) {
		console.log('handling ClientId, typeOfMessage 11')
		console.log('assigning msg.text to clientId')
		clientId = msg.text
	}

	function handleSubmitAnswers(event) {
		verifyCorrectAnswers()
		hideElement('nextBtn')
	}

	nextBtn.addEventListener("click", handleSubmitAnswers);

	function goClientHome() {
		window.location.href = './clientLogin'
	}

	function displayLeaderboard() {
		hideElement('nextBtn')
		if (currentQuestionNumber > fetchedQuestions.length) {
			hideElement('timer')
			showElement('nextBtn')
			nextBtn.innerText = 'Home'
			nextBtn.removeEventListener("click", handleSubmitAnswers)
			nextBtn.addEventListener("click", goClientHome)
		}
		console.log('displayLeaderboard()')
		clearInterval(counter)
		totalQuestionsParagraph.innerText = fetchedQuestions.length
		quizTitleDiv.innerText = quizTitle
		//	hideElement('question')
		//	hideElement('firstAnswer')
		//	hideElement('secondAnswer')
		//	hideElement('thirdAnswer')
		//	hideElement('fourthAnswer')
		console.log('savedLeaderboard before:', savedLeaderboard)
		savedLeaderboard = leaderboard.innerHTML
		console.log('savedLeaderboard before:', savedLeaderboard)
		leaderboard.innerHTML = ''
		clients.sort((a, b) => parseFloat(b.score) - parseFloat(a.score));
		let end = 10
		if (clients.length < 10)
			end = clients.length
		for (let i = 0; i < end; ++i) {
			leaderboard.innerHTML += '<p>' + (i + 1) + ". " + clients[i].nickname + ": " + clients[i].score + '</p><br>'
		}
	}

	function beforeStartOfQuiz() {
		nextBtn.innerText = "Submit Answer"
		currentQuestionParagraph.innerText = '0'
		hideElement('question')
		hideElement('firstAnswer')
		hideElement('secondAnswer')
		hideElement('thirdAnswer')
		hideElement('fourthAnswer')
		hideElement('nextBtn')
		//		savedLeaderboard = leaderboard.innerHTML
		//		leaderboar.innerHTML = ''
	}

	beforeStartOfQuiz()

	function startTimer() {
		verifiedCorrectAnswers = false
		//	timeText.textCount = '';
		counter = setInterval(timer, 1000);
		function timer() {
			timeCount.textContent = timeLeft; //changing the value of timeCount with time value
			timeLeft--; //decrement the time value
			if (timeLeft < 9) { //if timer is less than 9
				let addZero = timeCount.textContent;
				timeCount.textContent = "0" + addZero; //add a 0 before time value
			}
			if (timeLeft < 0) { //if timer is less than 0
				clearInterval(counter); //clear counter
				console.log('timeLeft < 0')
				verifyCorrectAnswers()
				timeText.textContent = "Time Off"; //change the time text to time off
				displayLeaderboard()

				//            const allOptions = option_list.children.length; //getting all option items
				//            let correcAns = questions[que_count].answer; //getting correct answer from array
				//            for(i=0; i < allOptions; i++){
				//                if(option_list.children[i].textContent == correcAns){ //if there is an option which is matched to an array answer
				//                    option_list.children[i].setAttribute("class", "option correct"); //adding green color to matched option
				//                    option_list.children[i].insertAdjacentHTML("beforeend", tickIconTag); //adding tick icon to matched option
				//                    console.log("Time Off: Auto selected correct answer.");
				//                }
				//            }
				//            for(i=0; i < allOptions; i++){
				//                option_list.children[i].classList.add("disabled"); //once user select an option then disabled all options
				//            }
				//            next_bs.classList.add("show"); //show the next button if user selected any option
			}
		}
	}

	function nextQuestion() {
		console.log('leaderboard.innerHTML before:', leaderboard.innerHTML)
		leaderboard.innerHTML = savedLeaderboard
		console.log('leaderboard.innerHTML after:', leaderboard.innerHTML)
		timeText.textContent = "Time"; //change the time text to time off
		console.log('nextQuestion()')
		++currentQuestionNumber
		console.log(currentQuestionNumber)
		if (currentQuestionNumber > fetchedQuestions.length) {
			//			const timerDiv = document.getElementById('timer')
			hideElement('timer')
			console.log('currentQuestionNumber > fetchedQuestions.length')
			displayLeaderboard()
			return
		}

		showElement('question')
		showElement('firstAnswer')
		showElement('secondAnswer')
		showElement('thirdAnswer')
		showElement('fourthAnswer')
		showElement('nextBtn')

		currentQuestion = fetchedQuestions[currentQuestionNumber - 1]
		console.log('currentQuestion:', currentQuestion)
		answers = currentQuestion.answers
		console.log('answers:', answers)

		unrevealAnswers()
		//	console.log("questionSpan: " + questionSpan.innerText)

		// This doesn't change from question to question, move it to startOfQuiz
		quizTitleDiv.innerText = quizTitle
		totalQuestionsParagraph.innerText = fetchedQuestions.length

		console.log('currentQuestion:', currentQuestion);
		$("#question").text(currentQuestion.ordinalNumber + '. ' + currentQuestion.title)
		$("#firstAnswer").text(answers[0].title)
		$("#secondAnswer").text(answers[1].title)
		$("#thirdAnswer").text(answers[2].title)
		$("#fourthAnswer").text(answers[3].title)

		//		questionSpan.textContent = currentQuestion.id + '. ' + currentQuestion.title
		//		firstAnswerSpan.textContent = answers[0].title
		//		secondAnswerSpan.textContent = answers[1].title
		//		thirdAnswerSpan.textContent = answers[2].title
		//		fourthAnswerSpan.textContent = answers[3].title
		//	score = currentQuestion.score // currentQuestion is global, no need for this
		timeLeft = currentQuestion.timeToAnswer

		startTimer()
		currentQuestionParagraph.innerText = currentQuestionNumber
	}

	function fetchQuestions() {
		console.log("Fetching quizId:", quizId)
		const quiz = {
			"id": quizId
		}
		$.ajax({
			type: "GET",
			url: "./fetchQuestions",
			data: quiz,
			dataType: "json",
			contentType: "application/json",
			success: function(msg) {
				console.log("msg:", msg);
				console.log("msg.title:", msg.title)
				fetchedQuestions = msg.questions
				quizTitle = msg.title
				quizTitleDiv.innerText = msg.title
				displayLeaderboard();
			},
			error: function(jqXHR, exception) {
				let msg = '';
				if (jqXHR.status === 0) {
					msg = 'Not connect.\n Verify Network.';
				} else if (jqXHR.status == 404) {
					msg = 'Requested page not found. [404]';
				} else if (jqXHR.status == 500) {
					msg = 'Internal Server Error [500].';
				} else if (exception === 'parsererror') {
					msg = 'Requested JSON parse failed.';
				} else if (exception === 'timeout') {
					msg = 'Time out error.';
				} else if (exception === 'abort') {
					msg = 'Ajax request aborted.';
				} else {
					msg = 'Uncaught Error.\n' + jqXHR.responseText;
				}
				console.log(msg)
			}
		});
	}

	function verifyCorrectAnswers() {
		if (verifiedCorrectAnswers) {
			console.log('verifyingAnswers more than once, why?')
			return
		}
		verifiedCorrectAnswers = true
		let awardedPoints = true
		for (let i = 0; i < 4; ++i) {
			console.log('options[i].classList:', options[i].classList)
			console.log('answers[i].isCorrect:', answers[i].isCorrect === true)
			const containsCorrect = options[i].classList.contains("correct")
			const isCorrect = answers[i].isCorrect
			
			if (containsCorrect && isCorrect) {
				console.log('correct:', i)
				//			availableSelections[i].parentElement.classList.add("correct"); //adding green color to correct selected option
				options[i].insertAdjacentHTML("beforeend", tickIconTag); //adding tick icon to correct selected option
			}
			else if ((containsCorrect && !isCorrect) || (!containsCorrect && isCorrect)) {
				awardedPoints = false
				console.log('incorrect:', i)
				//			availableSelections[i].parentElement.classList.add("incorrect"); //adding red color to correct selected option
				options[i].insertAdjacentHTML("beforeend", crossIconTag); //adding cross icon to correct selected option
			}
		}
		if (awardedPoints) {
			console.log('awarded points')
			myScore += currentQuestion.score
		} else {
			console.log('not awarded any points')
		}
		sendUpdateClientScore()
	}
}

createChatClient()