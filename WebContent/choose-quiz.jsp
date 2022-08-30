<%@ page import="service.QuizService" %>
<%@ page import="dao.QuizDao" %>
<%@ page import="Model.Quiz" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/choose-style.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.blue-indigo.min.css"/>
    <link href='https://fonts.googleapis.com/css?family=Dosis' rel='stylesheet'>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="scripts/socket.js"></script>
    <script src="scripts/select-api.js"></script>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Pick one Quiz!</title>
</head>
<body>
    <div class="area">
        <div id="clients-count" class="material-icons mdl-badge mdl-badge--overlap custom-padding" data-badge="1">account_box</div>
        <ul class="circles">
            <li></li>
            <li></li>
            <li></li>
            <li></li>
            <div id="center-div-select">
                <div id="loading" class="Box">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <div id="hide-cards" class="container">
                    <div id="slide-left-container">
                        <div class="slide-left">
                        </div>
                    </div>
                    <div id="cards-container">
                        <div class="cards">
                            <%
                                QuizService quizService = new QuizService(new QuizDao());
                                List<Quiz> quizzes = quizService.getQuizzes();
                                boolean noActive = true;
                                for (int i = 0; i < quizzes.size(); ++i) {
                                    if (quizzes.get(i).isActive()) {
                                        noActive = false;
                                        break;
                                    }
                                }
                                if (noActive) {
                                    response.sendRedirect("/no-quiz.jsp");
                                }
                                for (int i = 0; i < quizzes.size(); ++i) {
                                    if (quizzes.get(i).isActive()) {
                            %>
                            <div class="card" onclick="getQuizByIdAPI(<%=quizzes.get(i).getId()%>)">
                                <img src="<%=quizzes.get(i).getImageUrl()%>" alt="<%=quizzes.get(i).getDescription()%>" style="width:100%">
                                <div class="container">
                                    <h4>
                                        <%=quizzes.get(i).getTitle()%>
                                    </h4>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>

                    <div id="slide-right-container">
                        <div class="slide-right">
                        </div>
                    </div>
                </div>
            </div>
            <li></li>
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
    <script src="scripts/slider.js"></script>
</body>
</html>

