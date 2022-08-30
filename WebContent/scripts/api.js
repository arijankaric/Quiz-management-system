$(document).ready(function() {
    $("#loading").hide();
});

$(document).ajaxStart(function () {
    $("#main-menu").hide();
    $("#loading").show();
}).ajaxStop(function () {
    $("#loading").hide();
});

function getRandomAPI() {
    $.ajax({
        type: 'GET',
        url: './api/random',
        statusCode: {
            500: function(xhr) {
                window.location.href='/no-quiz.jsp';
            },
            200: function (msg) {
                window.location.href='/two-random.jsp';
            }
        }
    });
}

function chooseQuizAPI() {
    $.ajax({
        type: 'GET',
        url: './choose-quiz.jsp',
        success: [function (msg) {
            window.location.href='/choose-quiz.jsp';
        }]
    });
}