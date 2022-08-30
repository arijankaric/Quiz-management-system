<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.blue-indigo.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/toastr.css"/>
    <link href='https://fonts.googleapis.com/css?family=Dosis' rel='stylesheet'>
    <script src="scripts/socket.js"></script>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Two random quizzes!</title>
</head>
<%
     String quiz1 = (String) request.getSession().getAttribute("quiz1");
     String quiz2 = (String) request.getSession().getAttribute("quiz2");
%>
<body>
<div class="area">
    <div id="clients-count" class="material-icons mdl-badge mdl-badge--overlap custom-padding" data-badge="1">account_box</div>
    <ul class="circles">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>

        <div id="center-div">
            <table>
                <tr>
                    <td>
                        <div id="fade-quiz1" class="my-quiz">
                            <table>
                                <tr>
                                    <td class="timer-td">
                                        <div class="countdown-circles d-flex flex-wrap justify-content-center pt-4">
                                            <div class="holder m-2"><span id="question-points1"
                                                                          class="h1 font-weight-bold minutes">25</span>
                                                <p>points</p></div>
                                        </div>
                                    </td>
                                    <td class="timer-td">
                                        <div class="countdown-circles d-flex flex-wrap justify-content-center pt-4">
                                            <div class="holder m-2"><span id="question-time1"
                                                                          class="h1 font-weight-bold seconds">10</span>
                                                <p>seconds</p></div>
                                        </div>
                                    </td>
                                    <td class="timer-td">
                                        <!-- Primary-colored flat button -->
                                        <button id="next-button1" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent next-button">
                                            Next Question
                                        </button>
                                        <p class="current-question" id="question-number1">1/10</p>
                                    </td>
                                    <td class="timer-td">
                                        <button id="submit-button1" class="mdl-button mdl-js-button mdl-button--raised mdl-button--primary submit-button">
                                            Submit
                                        </button>
                                        <p class="current-question">Submit answer</p>
                                    </td>
                                </tr>
                            </table>
                            <div id="timer-progress1" class="mdl-progress mdl-js-progress"></div>
                            <div id="question1" class="question-title">
                                Question
                            </div>
                            <table id="quiz-questions1" style="width:100%">
                            </table>
                        </div>
                    </td>
                    <td>
                        <div id="fade-quiz2" class="my-quiz">
                            <table>
                                <tr>
                                    <td class="timer-td">
                                        <div class="countdown-circles d-flex flex-wrap justify-content-center pt-4">
                                            <div class="holder m-2"><span id="question-points2"
                                                                          class="h1 font-weight-bold minutes">25</span>
                                                <p>points</p></div>
                                        </div>
                                    </td>
                                    <td class="timer-td">
                                        <div class="countdown-circles d-flex flex-wrap justify-content-center pt-4">
                                            <div class="holder m-2"><span id="question-time2"
                                                                          class="h1 font-weight-bold seconds">10</span>
                                                <p>seconds</p></div>
                                        </div>
                                    </td>
                                    <td class="timer-td">
                                        <!-- Primary-colored flat button -->
                                        <button id="next-button2" class="mdl-button mdl-js-button mdl-button--raised mdl-button--accent next-button">
                                            Next Question
                                        </button>
                                        <p class="current-question" id="question-number2">1/10</p>
                                    </td>
                                    <td class="timer-td">
                                        <button  id="submit-button2" class="mdl-button mdl-js-button mdl-button--raised mdl-button--primary submit-button">
                                            Submit
                                        </button>
                                        <p class="current-question">Submit answer</p>
                                    </td>
                                </tr>
                            </table>
                            <div id="timer-progress2" class="mdl-progress mdl-js-progress"></div>
                            <div id="question2" class="question-title">
                                Question
                            </div>
                            <table id="quiz-questions2" style="width:100%">
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
<script src="scripts/animations.js"></script>
<script src="scripts/quiz.js"></script>
<script>
    let quiz1 = new QuizConstruction("1");
    let quiz2 = new QuizConstruction("2");
    console.log(quiz1);

    quiz1.setValues(<%=quiz1%>);
    quiz2.setValues(<%=quiz2%>);
</script>
</body>
</html>

