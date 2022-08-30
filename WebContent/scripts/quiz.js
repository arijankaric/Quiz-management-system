function QuizConstruction(quizId) {
    let obj = {}
    obj.defaultNumberOfSeconds = 10;
    obj.refreshRate = 100;
    obj.totalTime = 0;
    obj.currentTimerProgress = 100;
    obj.currentQuestion = 1;
    obj.currentSeconds = 10;

    obj.index = 0;
    obj.maxIndex = 0;

    obj.savedQuiz = null;

    obj.savedAnswers = null;
    obj.unansweredQuestions = [];
    obj.displayingUnanswered = false;

    obj.quizHtml = null;
    obj.isEnded = false;

    obj.totalScore = 0;
    obj.maxScore = 0;

    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": true,
        "progressBar": false,
        "positionClass": "toast-top-right",
        "preventDuplicates": true,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "2000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }

    obj.Quiz = function (quiz) {
        const quiz_ = {};
        quiz_.id = quiz.id;
        quiz_.title = quiz.title;
        quiz_.description = quiz.description;
        quiz_.imageUrl = quiz.imageUrl;
        quiz_.isActive = quiz.isActive;
        quiz_.questions = quiz.questions;
        quiz_.results = quiz.results;

        return quiz_;
    }

    obj.setValues = function (quiz) {
        obj.savedQuiz = obj.Quiz(quiz);
        console.log(obj.savedQuiz);
        obj.maxIndex = obj.savedQuiz.questions.length - 1;
        console.log(obj.index);
        obj.currentSeconds = obj.savedQuiz.questions[obj.index].timeToAnswer;
        obj.defaultNumberOfSeconds = obj.currentSeconds;
        obj.displayQuiz();
        document.querySelector('#submit-button' + quizId).onclick = function () { obj.restart(true); }
        document.querySelector('#next-button' + quizId).onclick = function () { obj.nextQuestion(); }
        obj.displayingUnanswered = false;
    }

    obj.displayQuiz = function () {
        document.querySelector('#question' + quizId).innerHTML = obj.savedQuiz.questions[obj.index].title;
        document.querySelector('#question-points' + quizId).innerHTML = obj.savedQuiz.questions[obj.index].score;
        document.querySelector('#quiz-questions' + quizId).innerHTML = obj.generateAnswersHtml(obj.savedQuiz.questions[obj.index].answers);
    }

    setInterval(function () {
        if (obj.displayingUnanswered) {
            return;
        }
        obj.restart(false);
        obj.currentTimerProgress -= (10 / obj.defaultNumberOfSeconds);
        obj.totalTime += obj.refreshRate;
        const temp = (obj.defaultNumberOfSeconds - (obj.totalTime / 1000)).toFixed(1);
        if (temp > 0) {
            obj.currentSeconds = temp;
        } else {
            obj.currentSeconds = 0;
        }
        document.querySelector('#question-time' + quizId).innerHTML = obj.currentSeconds;
        document.querySelector('#question-number' + quizId).innerHTML = obj.currentQuestion.toString() + '/' + (obj.maxIndex + 1).toString();
        if (obj.isEnded) {
            document.querySelector('#question-number' + quizId).innerHTML = (obj.index + 1).toString() + '/' + (obj.maxIndex + 1).toString();
            document.querySelector('#timer-progress' + quizId).classList.remove("mdl-progress");
            document.querySelector('#timer-progress' + quizId).classList.remove("mdl-js-progress");
        } else {
            document.querySelector('#timer-progress' + quizId).MaterialProgress.setProgress(obj.currentTimerProgress);
        }
    }, obj.refreshRate);

    obj.restart = function (isSubmit) {
        if (!isSubmit && obj.currentTimerProgress > 0) {
            return;
        }
        obj.checkIsAnswerCorrect();
        obj.loadNextQuestion();
    }

    obj.generateAnswersHtml = function (answers) {
        let html = '';
        obj.savedAnswers = answers;
        for (let i = 0; i < answers.length; ++i) {
            html +=
                '                               <tr>\n' +
                '                                  <td class="question-cell">' + answers[i].title + '</td>\n' +
                '                                    <td>\n' +
                '                                        <div class="input_wrapper">\n' +
                '                                            <input id="' + quizId + i.toString() + '" type="checkbox" class="switch_4">\n' +
                '                                            <svg class="is_checked" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 426.67 426.67">\n' +
                '                                                <path d="M153.504 366.84c-8.657 0-17.323-3.303-23.927-9.912L9.914 237.265c-13.218-13.218-13.218-34.645 0-47.863 13.218-13.218 34.645-13.218 47.863 0l95.727 95.727 215.39-215.387c13.218-13.214 34.65-13.218 47.86 0 13.22 13.218 13.22 34.65 0 47.863L177.435 356.928c-6.61 6.605-15.27 9.91-23.932 9.91z"/>\n' +
                '                                            </svg>\n' +
                '                                            <svg class="is_unchecked" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 212.982 212.982">\n' +
                '                                                <path d="M131.804 106.49l75.936-75.935c6.99-6.99 6.99-18.323 0-25.312-6.99-6.99-18.322-6.99-25.312 0L106.49 81.18 30.555 5.242c-6.99-6.99-18.322-6.99-25.312 0-6.99 6.99-6.99 18.323 0 25.312L81.18 106.49 5.24 182.427c-6.99 6.99-6.99 18.323 0 25.312 6.99 6.99 18.322 6.99 25.312 0L106.49 131.8l75.938 75.937c6.99 6.99 18.322 6.99 25.312 0 6.99-6.99 6.99-18.323 0-25.313l-75.936-75.936z" fill-rule="evenodd" clip-rule="evenodd"/>\n' +
                '                                            </svg>\n' +
                '                                        </div>\n' +
                '                                    </td>\n' +
                '                                </tr>';
        }
        return html;
    }

    obj.checkIsAnswerCorrect = function () {
        const points = obj.savedQuiz.questions[obj.index].score;
        obj.maxScore += parseInt(points);
        for (let i = 0; i < obj.savedAnswers.length; ++i) {
            if (document.getElementById(quizId + i.toString()).checked != obj.savedAnswers[i].correct) {
                toastr["error"]("Quiz " + quizId  + ": Incorrect!")
                return;
            }
        }
        obj.totalScore += parseInt(points);
        toastr["success"]("Quiz " + quizId  + ": Correct!  +" + points);
    }

    obj.nextQuestion = function() {
        obj.savedQuiz.questions[obj.index].timeToAnswer = obj.currentSeconds;
        obj.savedQuiz.questions[obj.index].originalIndex = obj.index;
        obj.unansweredQuestions.push(obj.savedQuiz.questions[obj.index]);
        obj.loadNextQuestion();
    }

    obj.quizEndReached = function() {
        if (obj.currentQuestion < obj.savedQuiz.questions.length) {
            return false;
        }
        return true;
    }

    obj.displayUnansweredQuestions = function () {
        obj.displayingUnanswered = true;
        obj.quizHtml = document.querySelector('#fade-quiz' + quizId).innerHTML;

        if (obj.unansweredQuestions.length == 0) {
            let resultsBody =
                '<div class="sc-gauge">\n' +
                '  <div class="sc-background">\n' +
                '    <div id="sc-custom' + quizId + '" class="sc-percentage"></div>\n' +
                '    <div class="sc-mask"></div>\n' +
                '    <span class="sc-value">' + obj.totalScore + '</span>\n' +
                '  </div>\n' +
                '  <span class="sc-min">0</span>\n' +
                '  <span class="sc-max">' + obj.maxScore + '</span>\n' +
                '<form id="custom-form" action="#">\n' +
                '  <div class="mdl-textfield mdl-js-textfield">\n' +
                '    <input class="mdl-textfield__input" placeholder="Your firstname" type="text" id="name' + quizId + '">\n' +
                '  </div>\n' +
                '  <div class="mdl-textfield mdl-js-textfield">\n' +
                '    <input class="mdl-textfield__input" placeholder="Your lastname" type="text" id="lastname' + quizId + '">\n' +
                '  </div>\n' +
                '  <div class="mdl-textfield mdl-js-textfield">\n' +
                '    <input class="mdl-textfield__input" placeholder="Your email" type="text" id="email' + quizId + '">\n' +
                '  </div>\n' +
                '  <button type="button" id="submit-result' + quizId + '" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent">\n' +
                '    Submit results \n' +
                '  </button>' +
                '  <br><br>' +
                '  <button type="button" id="go-home" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent">\n' +
                '    Return to home \n' +
                '  </button>' +
                '</form>' +
                '</div>'
            const rotateAngle = (obj.totalScore / obj.maxScore) * 180;
            document.querySelector('#fade-quiz' + quizId).innerHTML = resultsBody;
            document.querySelector('#sc-custom' + quizId).style.transform = "rotate(" + rotateAngle.toString() + "deg)";
            document.querySelector('#sc-custom' + quizId).style.transformOrigin = "top center"
            document.querySelector('#submit-result' + quizId).addEventListener("click", obj.checkInput, false);
            document.querySelector('#go-home').addEventListener("click", function() { window.location = './index.jsp'; }, false);
            return;
        }

        let questionsBody =  '<table class="ua-table">';
        for (let i = 0; i < obj.unansweredQuestions.length; ++i) {
            questionsBody += '<tr><td id="uq-' + i.toString() + quizId + '" class="ua-cell">' + obj.unansweredQuestions[i].title + '</td></tr>';
        }
        questionsBody += '</table>';
        document.querySelector('#fade-quiz' + quizId).innerHTML = questionsBody;
        for (let i = 0; i < obj.unansweredQuestions.length; ++i) {
            document.querySelector('#uq-' + i.toString() + quizId).addEventListener("click", obj.displayOneQuestion.bind(null, obj.unansweredQuestions[i].originalIndex, i), false);
            console.log(obj.unansweredQuestions[i].originalIndex);
        }
    }

    obj.loadNextQuestion = function () {
        ++obj.index;
        if (obj.quizEndReached()) {
            obj.displayUnansweredQuestions();
            return;
        }
        obj.currentTimerProgress = 100;
        obj.currentQuestion += 1;
        obj.currentSeconds = obj.savedQuiz.questions[obj.index].timeToAnswer
        obj.defaultNumberOfSeconds = obj.savedQuiz.questions[obj.index].timeToAnswer
        obj.totalTime = 0;
        obj.displayQuiz();
        $("#fade-quiz" + quizId).hide().fadeIn('slow');
    }

    obj.displayOneQuestion = function (i, newIndex) {
        obj.currentTimerProgress = 100;
        obj.totalTime = 0;
        obj.isEnded = true;
        obj.unansweredQuestions.splice(newIndex, 1);
        document.querySelector('#fade-quiz' + quizId).innerHTML = obj.quizHtml;
        console.log(obj.quizHtml);
        obj.index = i;
        obj.setValues(obj.savedQuiz);
    }

    obj.checkInput = function() {
        const name = document.getElementById("name" + quizId).value;
        const lastname = document.getElementById("lastname" + quizId).value;
        const email = document.getElementById("email" + quizId).value;

        if (name == "" || lastname == "" || email == "") {
            toastr["error"]("You must enter all fields!");
        } else {
            document.querySelector('#submit-result' + quizId).disabled = true;
            $.ajax({
                type: 'GET',
                url: './api/save-results',
                data: {
                    'id': obj.savedQuiz.id,
                    'name': name,
                    'lastname': lastname,
                    'email': email,
                    'score': obj.totalScore
                },
                success: [function () {
                    toastr["success"]("Result is saved to database!");
                }]
            });
        }
        return false;
    }

    return obj;
}