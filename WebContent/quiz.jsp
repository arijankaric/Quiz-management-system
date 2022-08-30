<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.blue-indigo.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/toastr.css"/>
    <link href='https://fonts.googleapis.com/css?family=Dosis' rel='stylesheet'>
    <script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
    <script src="scripts/socket.js"></script>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Kviz</title>
</head>
<%
    String quiz = (String) request.getSession().getAttribute("quiz");
    System.out.println(quiz);
    // String quiz = "{\"id\":35,\"title\":\"How much do you know about the world\",\"description\":\"Quiz about world knowledge\",\"imageUrl\":\"https://grizly.com/quizzes/can-you-name-these-countries-of-the-world\",\"questions\":[{\"id\":221,\"title\":\"Which language is most commonly spoken in the world?\",\"timeToAnswer\":30,\"score\":10,\"answers\":[{\"id\":1,\"title\":\"English\",\"correct\":true},{\"id\":2,\"title\":\"Bosnian\",\"correct\":false},{\"id\":3,\"title\":\"Chinese\",\"correct\":false},{\"id\":4,\"title\":\"Japanese\",\"correct\":false}]},{\"id\":222,\"title\":\"What is the largest country in the world?\",\"timeToAnswer\":30,\"score\":10,\"answers\":[{\"id\":5,\"title\":\"Canada\",\"correct\":false},{\"id\":6,\"title\":\"USA\",\"correct\":false},{\"id\":7,\"title\":\"Rusia\",\"correct\":true},{\"id\":8,\"title\":\"Australia\",\"correct\":false}]},{\"id\":223,\"title\":\"How many continents are there on Earth?\",\"timeToAnswer\":25,\"score\":15,\"answers\":[{\"id\":9,\"title\":\"4\",\"correct\":false},{\"id\":10,\"title\":\"7\",\"correct\":true},{\"id\":11,\"title\":\"12\",\"correct\":false},{\"id\":12,\"title\":\"6\",\"correct\":false}]},{\"id\":224,\"title\":\"How many oceans are there on Earth?\",\"timeToAnswer\":25,\"score\":15,\"answers\":[{\"id\":13,\"title\":\"3\",\"correct\":false},{\"id\":14,\"title\":\"5\",\"correct\":true},{\"id\":15,\"title\":\"4\",\"correct\":false},{\"id\":16,\"title\":\"6\",\"correct\":false}]},{\"id\":225,\"title\":\"A large coral reef is located along the coast of which country?\",\"timeToAnswer\":25,\"score\":15,\"answers\":[{\"id\":17,\"title\":\"South Africa\",\"correct\":false},{\"id\":18,\"title\":\"Germany\",\"correct\":false},{\"id\":19,\"title\":\"Australia\",\"correct\":true},{\"id\":20,\"title\":\"USA\",\"correct\":false}]},{\"id\":226,\"title\":\"Which of these cities is not in Europe?\",\"timeToAnswer\":20,\"score\":20,\"answers\":[{\"id\":21,\"title\":\"Paris\",\"correct\":false},{\"id\":22,\"title\":\"Berlin\",\"correct\":false},{\"id\":23,\"title\":\"Tel Aviv\",\"correct\":true},{\"id\":24,\"title\":\"Rome\",\"correct\":true}]},{\"id\":227,\"title\":\"In which South American country is Portuguese spoken?\",\"timeToAnswer\":20,\"score\":20,\"answers\":[{\"id\":25,\"title\":\"Brazil\",\"correct\":true},{\"id\":26,\"title\":\"Columbia\",\"correct\":false},{\"id\":27,\"title\":\"Bolivia\",\"correct\":false},{\"id\":28,\"title\":\"Argentina\",\"correct\":true}]},{\"id\":228,\"title\":\"Which two countries are located in Africa?\",\"timeToAnswer\":15,\"score\":30,\"answers\":[{\"id\":29,\"title\":\"Etiopia,Tansania\",\"correct\":true},{\"id\":30,\"title\":\"Tansania,China\",\"correct\":false},{\"id\":31,\"title\":\"Zimbabve,Germany\",\"correct\":false},{\"id\":32,\"title\":\"Iran,Irak\",\"correct\":false}]},{\"id\":229,\"title\":\"What is the longest river in Africa?\",\"timeToAnswer\":10,\"score\":50,\"answers\":[{\"id\":33,\"title\":\"Nil\",\"correct\":true},{\"id\":34,\"title\":\"Amazon\",\"correct\":false},{\"id\":35,\"title\":\"Bosna\",\"correct\":false},{\"id\":36,\"title\":\"Sava\",\"correct\":false}]},{\"id\":230,\"title\":\"What is the capital of the US state of Michigan?\",\"timeToAnswer\":10,\"score\":50,\"answers\":[{\"id\":37,\"title\":\"Lansing\",\"correct\":true},{\"id\":38,\"title\":\"Mexico City\",\"correct\":false},{\"id\":39,\"title\":\"Augusta\",\"correct\":false},{\"id\":40,\"title\":\"Phoenix\",\"correct\":false}]}],\"results\":[],\"active\":true}";
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
        </div>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>
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

    quiz1.setValues(<%=quiz%>);
</script>
</body>
</html>
