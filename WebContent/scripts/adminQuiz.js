class XlsExport {
	// data: array of objects with the data for each row of the table
	// name: title for the worksheet
	constructor(data, title = 'Worksheet') {
		// input validation: new xlsExport([], String)
		if (!Array.isArray(data) || (typeof title !== 'string' || Object.prototype.toString.call(title) !== '[object String]')) {
			throw new Error('Invalid input types: new xlsExport(Array [], String)');
		}

		this._data = data;
		this._title = title;
	}

	set setData(data) {
		if (!Array.isArray(data)) throw new Error('Invalid input type: setData(Array [])');

		this._data = data;
	}

	get getData() {
		return this._data;
	}

	exportToXLS(fileName = 'export.xls', rtl = false) {
		if (typeof fileName !== 'string' || Object.prototype.toString.call(fileName) !== '[object String]') {
			throw new Error('Invalid input type: exportToCSV(String)');
		}

		var TEMPLATE_XLS_val = `
    <html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
    <meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8"/>
    <head><!--[if gte mso 9]><xml>
    <x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{title}</x:Name><x:WorksheetOptions>`;

		if (rtl)
			TEMPLATE_XLS_val = TEMPLATE_XLS_val + `<x:DisplayRightToLeft/>`;

		TEMPLATE_XLS_val = TEMPLATE_XLS_val +
			`<x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml>
    <![endif]--></head>
    <body>{table}</body></html>`;

		const TEMPLATE_XLS = TEMPLATE_XLS_val;

		const MIME_XLS = 'application/vnd.ms-excel;base64,';

		const parameters = {
			title: this._title,
			table: this.objectToTable(),
		};
		const computeOutput = TEMPLATE_XLS.replace(/{(\w+)}/g, (x, y) => parameters[y]);

		const computedXLS = new Blob([computeOutput], {
			type: MIME_XLS,
		});
		const xlsLink = window.URL.createObjectURL(computedXLS);
		this.downloadFile(xlsLink, fileName);
	}

	exportToCSV(fileName = 'export.csv') {
		if (typeof fileName !== 'string' || Object.prototype.toString.call(fileName) !== '[object String]') {
			throw new Error('Invalid input type: exportToCSV(String)');
		}
		const computedCSV = new Blob([this.objectToSemicolons()], {
			type: 'text/csv;charset=utf-8',
		});
		const csvLink = window.URL.createObjectURL(computedCSV);
		this.downloadFile(csvLink, fileName);
	}

	downloadFile(output, fileName) {
		const link = document.createElement('a');
		document.body.appendChild(link);
		link.download = fileName;
		link.href = output;
		link.click();
	}

	toBase64(string) {
		return window.btoa(unescape(encodeURIComponent(string)));
	}

	objectToTable() {
		// extract keys from the first object, will be the title for each column
		const colsHead = `<tr>${Object.keys(this._data[0]).map(key => `<td>${key}</td>`).join('')}</tr>`;

		const colsData = this._data.map(obj => [`<tr>
                ${Object.keys(obj).map(col => `<td>${(obj[col] === 0 || obj[col]) ? obj[col] : ''}</td>`).join('')}
            </tr>`]) // 'null' values not showed but zero value is showed
			.join('');

		return `<table>${colsHead}${colsData}</table>`.trim(); // remove spaces...
	}

	objectToSemicolons() {
		const colsHead = Object.keys(this._data[0]).map(key => [key]).join(';');
		const colsData = this._data.map(obj => [ // obj === row
			Object.keys(obj).map(col => [
				obj[col], // row[column]
			]).join(';'), // join the row with ';'
		]).join('\n'); // end of row

		return `${colsHead}\n${colsData}`;
	}
}

let counter
let fetchedQuestions
let quizTitle
let clients = []
let clientAnswer = 0
let timeLeft
let currentQuestionNumber = 0
let answers
let receivedAnswers = 0
const adminId = -1
const adminNickname = null
const options = document.getElementsByClassName('option')


function exportLeaderboard() {
	const xls = new XlsExport(clients);
	xls.exportToXLS('export.xls')
}

function printQuestions() {
	for (let i = 0; i < fetchedQuestions.length; ++i) {
		console.log(fetchedQuestions[i])
	}
}

function fetchQuestions() {
	const quiz = {
		"id": quizId
	}
	$.ajax({
		type: "GET",
		url: "../fetchQuestions",
		data: quiz,
		success: function(msg) {
			console.log("msg:", msg);
			fetchedQuestions = msg.questions
			quizTitle = msg.title
			quizTitleDiv.innerText = quizTitle + " | Quiz Code: " + quizCode
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

const currentQuestionParagraph = document.getElementById('currQues')
const totalQuestionsParagraph = document.getElementById('totQues')
const leaderboard = document.getElementById('leaderboard')

const onlineClientsParagraph = document.getElementById('onlineClients')
let currentlyOnline = 0
onlineClientsParagraph.innerText = "Online: " + currentlyOnline

const questionSpan = document.getElementById('question')
const firstAnswerSpan = document.getElementById('firstAnswer')
const secondAnswerSpan = document.getElementById('secondAnswer')
const thirdAnswerSpan = document.getElementById('thirdAnswer')
const fourthAnswerSpan = document.getElementById('fourthAnswer')
const availableSelections = [firstAnswerSpan, secondAnswerSpan, thirdAnswerSpan, fourthAnswerSpan]
const startBtn = document.getElementById('startBtn')
const startDiv = document.getElementById('startDiv')

let currentQuestion = 0
let quizCode = ''

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

let savedLeaderboard

function goToAdminHome(event) {
	window.location.href = "./quizzes"
}

function saveResults(event) {
	exportLeaderboard()
	nextBtn.removeEventListener("click", saveResults)
	nextBtn.innerText = 'Return to quizzes'
	nextBtn.addEventListener("click", goToAdminHome)
}

function displayLeaderboard() {
	showElement('nextBtn')
	hideElement('question')
	hideElement('firstAnswer')
	hideElement('secondAnswer')
	hideElement('thirdAnswer')
	hideElement('fourthAnswer')
	totalQuestionsParagraph.innerText = fetchedQuestions.length
	savedLeaderboard = leaderboard.innerHTML
	leaderboard.innerHTML = ''
	nextBtn.innerText = 'Next Question'
	if(currentQuestionNumber === fetchedQuestions.length) {
		console.log('displayLeaderboard currentQuestionNumber === fetchedQuestions.length')
		nextBtn.addEventListener("click", saveResults)
		nextBtn.innerText = 'Export to XLS'
		hideElement('timer')
	}
	clearInterval(counter)
	
	let end = 10
	clients.sort((a, b) => parseFloat(b.score) - parseFloat(a.score));
	if (clients.length < 10)
		end = clients.length
	for (let i = 0; i < end; ++i) {
		leaderboard.innerHTML += '<p>' + (i+1) + ". " + clients[i].nickname + ": " + clients[i].score + '</p><br>'
	}
}


function startTimer() {
	counter = setInterval(timer, 1000);
	function timer() {
		timeCount.textContent = timeLeft; //changing the value of timeCount with time value
		timeLeft--; //decrement the time value
		if (timeLeft < 9) { //if timer is less than 9
			let addZero = timeCount.textContent;
			timeCount.textContent = "0" + addZero; //add a 0 before time value
		}
		if (timeLeft < 0) { //if timer is less than 0
			displayLeaderboard()

			if (currentQuestionNumber === fetchedQuestions.length) {
				console.log('currentQuestionNumber === fetchedQuestions.length')
				nextBtn.innerText = 'Save XLS'
				nextBtn.onclick = 'exportLeaderboard()'
			}
			clearInterval(counter); //clear counter
			timeText.textContent = "Time Off"; //change the time text to time off
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

function unrevealAnswers() {
	for (let i = 0; i < 4; ++i) {
		const optionsClassList = options[i].classList
		optionsClassList.remove("correct");
		optionsClassList.remove("incorrect");
		//		availableSelections[i].classList.remove("selected");
	}
}

function revealAnswers() {
	for (let i = 0; i < answers.length; ++i) {
		const isCorrect = answers[i].isCorrect
		if (isCorrect) {
			console.log('correct:', i)
			options[i].classList.add("correct")
		} else {
			console.log('incorrect:', i)
			options[i].classList.add("incorrect")
		}
//		console.log('parent after:', parent)
	}
}

function nextQuestion() {
	leaderboard.innerHTML = savedLeaderboard
	timeText.textContent = "Time"; //change the time text to time off
	console.log('nextQuestion()')
	++currentQuestionNumber
	receivedAnswers = 0
	if (currentQuestionNumber > fetchedQuestions.length) {
		console.log('nextQuestion currentQuestionNumber > fetchedQuestions.length')
		nextBtn.onclick = 'exportLeaderboard()'
		nextBtn.innerText = 'Export to XLS'
		displayLeaderboard()
		hideElement('timer')
		return
//		exportLeaderboard()
	}

	showElement('question')
	showElement('firstAnswer')
	showElement('secondAnswer')
	showElement('thirdAnswer')
	showElement('fourthAnswer')
	hideElement('nextBtn')

	currentQuestion = fetchedQuestions[currentQuestionNumber - 1]
//	console.log('currentQuestion:', currentQuestion)
	answers = currentQuestion.answers
//	console.log('answers:', answers)
	
	$("#question").text(currentQuestion.ordinalNumber + '. ' + currentQuestion.title)
	$("#firstAnswer").text(answers[0].title)
	$("#secondAnswer").text(answers[1].title)
	$("#thirdAnswer").text(answers[2].title)
	$("#fourthAnswer").text(answers[3].title)
	//	console.log("questionSpan: " + questionSpan.innerText)
	
//	questionSpan.textContent = currentQuestion.id + '. ' + currentQuestion.title
//	firstAnswerSpan.textContent = answers[0].title
//	secondAnswerSpan.textContent = answers[1].title
//	thirdAnswerSpan.textContent = answers[2].title
//	fourthAnswerSpan.textContent = answers[3].title

	timeLeft = currentQuestion.timeToAnswer
	startTimer()
	currentQuestionParagraph.innerText = currentQuestionNumber
//	console.log('currentQuestionNumber:', currentQuestionNumber)
	unrevealAnswers()
	revealAnswers()
}

function createChatClient() {
	const wsUrl = "ws://localhost:8080/projekat/quizGameplay";
	let client = new WebSocket(wsUrl);
	
	function beforeStartOfQuiz() {
		showElement('startDiv')
		hideElement('question')
		hideElement('firstAnswer')
		hideElement('secondAnswer')
		hideElement('thirdAnswer')
		hideElement('fourthAnswer')
		hideElement('nextBtn')
		hideElement('quizDiv')
		savedLeaderboard = leaderboard.innerHTML
//		leaderboar.innerHTML = ''
	}
	
	client.onopen = function(event) {
		console.log("onopen:", event)
		fetchQuestions()
		sendAdminDeployQuiz()
		unrevealAnswers()
		beforeStartOfQuiz()
	};

	client.onerror = function(event) {
		console.log("onerror:", event)
	}

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

	function handleClientConnect(msg) {
		console.log('handleClientConnect', msg)
		++currentlyOnline
		startBtn.innerText = "Start Quiz: " + quizCode + " Online: " + currentlyOnline
		onlineClientsParagraph.innerText = "Online: " + currentlyOnline
		const newPlayer = { id: msg.senderId, nickname: msg.senderNickname, score: msg.text }
		console.log('newPlayer:', newPlayer)
		console.log('clients before:', clients)
		clients.push(newPlayer)
		console.log('clients after:', clients)
	}

	function handleClientDisconnect(msg) {
		--currentlyOnline
		startBtn.innerText = "Start Quiz: " + quizCode + " Online: " + currentlyOnline
		onlineClientsParagraph.innerText = "Online: " + currentlyOnline
	}

	function handleUpdateClientScore(msg) {
		++receivedAnswers;
		if (receivedAnswers === currentlyOnline) {
			nextBtn.innerText = 'Fast Forward'
			showElement('nextBtn')
		}
		console.log("updateScore msg:", msg);
		console.log("clients before:", clients);
		let obj = clients.find(x => x.id == msg.senderId);
		if (obj === null || obj === undefined) {
			const newPlayer = { id: msg.senderId, nickname: msg.senderNickname, score: msg.text }
			clients.push(newPlayer);
			++currentlyOnline
			onlineClientsParagraph.innerText = "Online: " + currentlyOnline
		} else {
			const index = clients.indexOf(obj);
			obj.score = msg.text
			if(obj.nickname !== msg.senderNickname) {
				console.log('obj.nickname !== msg.senderNickname')
				obj.nickname = msg.senderNickname
				clients = clients.fill(obj, index, index);
			} else {
				clients = clients.fill(obj, index, index);
			}
		}
		console.log("clients after:", clients);
	}

	function handleQuizCode(msg) {
		console.log('handlingQuizCode')
		startBtn.innerText = "Start Quiz: " + msg.text + " Online: " + currentlyOnline
		quizCode = msg.text
		quizTitleDiv.innerText = quizTitle + ' | Quiz Code: ' + msg.text
	}

	function sendAdminDeployQuiz() {
		const msg = {
			typeOfMessage: 4,
			senderId: adminId,
			senderNickname: adminNickname,
			text: quizId
		}
		sendMsg(msg)
	}

	function sendAdminStartQuiz() {
		const msg = {
			typeOfMessage: 1,
			senderId: adminId,
			senderNickname: adminNickname,
			text: null
		}
		sendMsg(msg)
	}

	function sendEveryoneFinished() {
		const msg = {
			typeOfMessage: 7,
			senderId: adminId,
			senderNickname: adminNickname,
			text: null
		}
		sendMsg(msg)
	}

	function sendMsg(msg) {
		console.log('sending Msg:', msg)
		client.send(JSON.stringify(msg))
	}

	client.onmessage = function(event) {
		const msg = JSON.parse(event.data);
		console.log("Message received", msg);
		const typeOfMessage = msg.typeOfMessage

		switch (typeOfMessage) {
			case 0:
				handleClientConnect(msg)
				break;
			case 3:
				handleClientDisconnect(msg)
				break;
			case 5:
				handleUpdateClientScore(msg)
				break;
			case 8:
				handleQuizCode(msg)
				break;
		}
	};

	function sendNextQuestion() {
		const msg = {
			typeOfMessage: 6,
			senderId: adminId,
			senderNickname: adminNickname,
			text: null
		}
		sendMsg(msg)
	}
	startBtn.addEventListener("click", function(event) {
		sendAdminStartQuiz()
		nextQuestion()
		hideElement('startDiv')
		showElement('quizDiv')
		totalQuestionsParagraph.innerText = fetchedQuestions.length
		currentQuestionParagraph.innerText = currentQuestionNumber
	})
	nextBtn.addEventListener("click", function(event) {
		const nextBtnText = nextBtn.innerText
		if (nextBtnText === "Next Question") {
			nextQuestion()
			sendNextQuestion()
		} else if (nextBtnText === "Fast Forward") {
			sendEveryoneFinished()
			displayLeaderboard()
		} else {
			console.log('maybe export leaderboard')
		}
	});
}

createChatClient()