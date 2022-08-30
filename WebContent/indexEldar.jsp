<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
    <head>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">
        <script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"
                integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
                crossorigin="anonymous"></script>
        <script src="scripts/animations.js"></script>
        <script src="scripts/socket.js"></script>
        <script src="scripts/api.js"></script>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Kviz</title>
    </head>
    <body>
        <div class="area">
            <div id="clients-count" class="material-icons mdl-badge mdl-badge--overlap custom-padding" data-badge="1">account_box</div>
            <ul class="circles">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <div class="index-page" id="center-div">
                    <div id="loading" class="Box">
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                    <div id="main-menu">
                        <div>
                        <span class="span-index">
                            <button onclick="chooseQuizAPI()" class="btn button-index">Play Quiz</button>
                        </span>
                        </div>
                        <div>
                        <span class="span-index">
                            <button onclick="getRandomAPI()" class="btn button-index">Play 2 Quiz</button>
                        </span>
                        </div>
                        <div>
                        <span class="span-index">
                            <button onclick="location.href='/admin/login.jsp'" class="btn button-index">Login</button>
                        </span>
                        </div>
                    </div>
                </div>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
    </body>
</html>
