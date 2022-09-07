<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.*" %>
<%@ page import="Model.Quiz, Model.User" %>
<%@ page import="service.*" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Result" %>

<html>
<head>
    <title>Add quiz</title>
    <link rel="stylesheet" href="../css/admin-style.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <!-- Google fonts & Material Icons -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons">

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" type="text/css"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://pixinvent.com/stack-responsive-bootstrap-4-admin-template/app-assets/css/bootstrap-extended.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://pixinvent.com/stack-responsive-bootstrap-4-admin-template/app-assets/fonts/simple-line-icons/style.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://pixinvent.com/stack-responsive-bootstrap-4-admin-template/app-assets/css/colors.min.css">
    <link rel="stylesheet" type="text/css"
          href="https://pixinvent.com/stack-responsive-bootstrap-4-admin-template/app-assets/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat&display=swap" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="../css/toastr.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="../scripts/Admin.js"></script>
    <script src="../scripts/add-quiz.js"></script>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        response.sendRedirect("authorization_fail.jsp");
    }
    QuizService quizService = new QuizService(new QuizDao());
    QuestionService questionService = new QuestionService(new QuestionDao());
    AnswerService answerService = new AnswerService(new AnswerDao());
    ResultService resultService = new ResultService(new ResultDao());
    UserService userService = new UserService(new UserDao());
%>

<div class="container add-quiz-container">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">


    <div class="container">
        <div class="row">
            <div class="col-sm form-container">
                <form>
                    <div class="form-group row">
                        <label for="quiz-title-input" class="col-4 col-form-label">Quiz title:</label>
                        <div class="col-8">
                            <input id="quiz-title-input" name="quiz-title-input" placeholder="Quiz title" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="quiz-description-input" class="col-4 col-form-label">Description:</label>
                        <div class="col-8">
                            <input id="quiz-description-input" name="quiz-description-input" placeholder="Description" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="quiz-image-input" class="col-4 col-form-label">Image URL:</label>
                        <div class="col-8">
                            <input id="quiz-image-input" name="quiz-image-input" placeholder="Image URL" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Is Active:</label>
                        <div class="col-8">
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input name="checkbox" id="checkbox_0" type="checkbox" class="custom-control-input" value="1" checked="checked">
                                <label for="checkbox_0" class="custom-control-label">Yes</label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-sm form-container">
                <form>
                    <div class="form-group row">
                        <label for="question-input" class="col-4 col-form-label">Question title:</label>
                        <div class="col-8">
                            <div class="input-group">
                                <input id="question-input" name="question-input" placeholder="Your question" type="text" class="form-control" aria-describedby="question-inputHelpBlock" required="required">
                            </div>
                            <span id="question-inputHelpBlock" class="form-text text-muted">Enter your question</span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-4">Correct:</label>
                        <div class="col-8">
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input name="correct-answers" id="correct-answers_0" type="checkbox" aria-describedby="correct-answersHelpBlock" class="custom-control-input" value="1" checked="checked">
                                <label for="correct-answers_0" class="custom-control-label">1</label>
                            </div>
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input name="correct-answers" id="correct-answers_1" type="checkbox" aria-describedby="correct-answersHelpBlock" class="custom-control-input" value="2" checked="checked">
                                <label for="correct-answers_1" class="custom-control-label">2</label>
                            </div>
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input name="correct-answers" id="correct-answers_2" type="checkbox" aria-describedby="correct-answersHelpBlock" class="custom-control-input" value="3" checked="checked">
                                <label for="correct-answers_2" class="custom-control-label">3</label>
                            </div>
                            <div class="custom-control custom-checkbox custom-control-inline">
                                <input name="correct-answers" id="correct-answers_3" type="checkbox" aria-describedby="correct-answersHelpBlock" class="custom-control-input" value="4" checked="checked">
                                <label for="correct-answers_3" class="custom-control-label">4</label>
                            </div>
                            <span id="correct-answersHelpBlock" class="form-text text-muted">Select correct answers</span>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-4 col-form-label" for="answer-input-1">Answer 1:</label>
                        <div class="col-8">
                            <input id="answer-input-1" name="answer-input-1" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="answer-input-2" class="col-4 col-form-label">Answer 2:</label>
                        <div class="col-8">
                            <input id="answer-input-2" name="answer-input-2" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="answer-input-3" class="col-4 col-form-label">Answer 3:</label>
                        <div class="col-8">
                            <input id="answer-input-3" name="answer-input-3" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="answer-input-4" class="col-4 col-form-label">Answer 4:</label>
                        <div class="col-8">
                            <input id="answer-input-4" name="answer-input-4" type="text" class="form-control">
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <label for="question-score" class="col-4 col-form-label">Points:</label>
                        <div class="col-8">
                            <input id="question-score" name="question-score" type="number" class="form-control">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="question-time" class="col-4 col-form-label">Time:</label>
                        <div class="col-8">
                            <input id="question-time" name="question-time" type="number" class="form-control">
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-sm">
                <div class="form-group row">
                    <button onclick="addQuestion()" name="add-question-button" type="button" class="btn btn-primary add-quiz-buttons">Add Question</button>
                    <button onclick="changeQuestionsOrder()" data-toggle="modal" data-target="#questionsModal" name="add-question-button" type="button" class="btn btn-primary add-quiz-buttons">Question List</button>
                    <% if (user != null) { %>
                    <button onclick="addQuiz(<%=user.getId()%>)" id="add-quiz-button" name="add-quiz-button" type="button" class="btn btn-primary add-quiz-buttons">Submit Quiz</button>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <section>
        <!-- Modal HTML Markup -->
        <div id="questionsModal" class="modal fade">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title">Question List</h1>
                    </div>
                    <div class="modal-body">
                        <ul id="sortable">
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <div class="form-group">
                            <div>
                                <button onclick="onModalClose()" class="btn btn-danger" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </section>

</div>
</body>
</html>
