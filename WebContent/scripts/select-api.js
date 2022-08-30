$(document).ready(function() {
    $("#loading").hide();
});

function getQuizByIdAPI(id) {
    $("#hide-cards").hide();
    $("#loading").show();
    $.ajax({
        type: 'GET',
        url: './api/quiz?quiz_id=' + id,
        success: [function (msg) {
            window.location.href='/quiz.jsp';
        }]
    });
}