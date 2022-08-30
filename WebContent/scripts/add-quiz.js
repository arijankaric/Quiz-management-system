let allQuestions = [];
let originalArray = [];

$(document).ready(function() {
    clearForm();
});

function clearForm() {
    document.getElementById('question-input').value = '';
    document.getElementById('question-score').value = '';
    document.getElementById('question-time').value = '';
    for (let i = 0; i < 4; ++i) {
        document.getElementById('correct-answers_' + i).checked = false;
        document.getElementById('answer-input-' + (i + 1)).value = '';
    }
}

function addQuestion() {
    const question = document.getElementById('question-input').value;
    const answers = [];
    let answer = null;
    let isCorrect = false;
    if (question == null || question == '') {
        toastr["error"]("Enter question title!");
        return;
    }
    for (let i = 0; i < 4; ++i) {
        isCorrect = document.getElementById('correct-answers_' + i).checked
        answer = document.getElementById('answer-input-' + (i + 1)).value
        if (answer != null && answer != '') {
            answers.push({
                title: answer,
                isCorrect: isCorrect
            })
        }
    }
    if (answers.length == 0) {
        toastr["error"]("Enter at least one answer!");
        return;
    }

    const score = document.getElementById('question-score').value
    const time = document.getElementById('question-time').value

    if (score == null || score == '' || score <= 0 || score > 100) {
        toastr["error"]("Enter valid score!");
        return;
    }

    if (time == null || time == '' || time > 60 || time <= 5) {
        toastr["error"]("Enter valid time!");
        return;
    }

    const questionObject = {
        title: question,
        timeToAnswer: time,
        score: score,
        answers: answers
    };
    console.log(questionObject)
    allQuestions.push(questionObject);
    toastr["success"]("Question added! Total: " + allQuestions.length);
    originalArray.push(questionObject);
    clearForm();
}

function addQuiz(userId) {
    let addButton = document.getElementById('add-quiz-button');
    addButton.innerHTML = 'Saving ...';
    addButton.disabled = true;

    const title = document.getElementById('quiz-title-input').value;
    if (title == null || title == '') {
        toastr["error"]("Enter quiz title!");
        addButton.innerHTML = 'Submit Quiz';
        addButton.disabled = false;
        return;
    }
    const description = document.getElementById('quiz-description-input').value;
    if (description == null || description == '') {
        toastr["error"]("Enter quiz description!");
        addButton.innerHTML = 'Submit Quiz';
        addButton.disabled = false;
        return;
    }
    const imageUrl = document.getElementById('quiz-image-input').value;
    if (imageUrl == null || imageUrl == '') {
        toastr["error"]("Enter quiz image URL!");
        addButton.innerHTML = 'Submit Quiz';
        addButton.disabled = false;
        return;
    }
    const active = document.getElementById('checkbox_0').checked
    if (allQuestions.length == 0) {
        toastr["error"]("You have to add at least one question!");
        addButton.innerHTML = 'Submit Quiz';
        addButton.disabled = false;
        return;
    }
    const quizObject = {
        creator: userId,
        title: title,
        description: description,
        imageUrl: imageUrl,
        active: active,
        questions: allQuestions
    };
    console.log(quizObject);

    $.ajax({
        type: 'POST',
        url: './addQuiz',
        data: {
            'data': JSON.stringify(quizObject)
        },
        statusCode: {
            400: function(xhr) {
                toastr["error"]("Quiz with that name exists!");
                addButton.innerHTML = 'Submit Quiz';
                addButton.disabled = false;
            },
            200: function(xhr) {
                toastr["success"]("Quiz successfully added!");
                addButton.innerHTML = 'Submit Quiz';
                window.location = "./admin.jsp";
            }
        }
    });
}

$( function() {
    $( "#sortable" ).sortable();
    $( "#sortable" ).disableSelection();
} );

function changeQuestionsOrder() {
    let list = document.getElementById('sortable');
    let sortableList = ''
    for (let i = 0; i < allQuestions.length; ++i) {
        sortableList += '<li id="' + i + '" class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>' + allQuestions[i].title + '</li>'
    }
    list.innerHTML = sortableList;
}

$( function() {
    let tempArray = [];
    $( "#sortable" ).sortable({
        stop: function(event, ui) {
            tempArray = [];
            var itemOrder = $('#sortable').sortable("toArray");

            for (var i = 0; i < itemOrder.length; i++) {
                console.log("Position: " + i + " ID: " + itemOrder[i]);
                tempArray.push(originalArray[parseInt(itemOrder[i])]);
            }
            allQuestions = tempArray.slice();
            console.log(allQuestions);
        }
    });

});

function onModalClose() {
    originalArray = allQuestions.slice();
}