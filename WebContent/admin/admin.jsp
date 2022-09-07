<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dao.*" %>
<%@ page import="service.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.ArrayList" %>

<%!
    private HashMap<String, String> messages;
    private String userExists = "";
    private String password = "";
    private String successEdit = "";
%>

<html>
<head>
    <title>Admin panel</title>
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
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <link rel="stylesheet" type="text/css" href="../css/toastr.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
    <script src="../scripts/Admin.js"></script>
    <script src="../scripts/sorting.js"></script>
</head>
<body>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        response.sendRedirect("/admin/authorization_fail.jsp");
    }
    QuizService quizService = new QuizService(new QuizDao());
    QuestionService questionService = new QuestionService(new QuestionDao());
    AnswerService answerService = new AnswerService(new AnswerDao());
    ResultService resultService = new ResultService(new ResultDao());
    UserService userService = new UserService(new UserDao());
%>

<div class="grey-bg container-fluid">
    <section id="minimal-statistics">
        <div class="row">
            <div class="col-12 mt-3 mb-1">
                <h4 class="text-uppercase">Quiz admin panel</h4>

            </div>
        </div>
        <div class="row">
            <div class="col-xl-3 col-sm-6 col-12">
                <div class="card">
                    <div class="card-content">
                        <div class="card-body">
                            <div class="media d-flex">
                                <div class="align-self-center">
                                    <i class="icon-pencil primary font-large-2 float-left"></i>
                                </div>
                                <div class="media-body text-right">
                                    <h3><%=quizService.getNumberOfQuizzes()%>
                                    </h3>
                                    <span>Total number of Quizzes</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-sm-6 col-12">
                <div class="card">
                    <div class="card-content">
                        <div class="card-body">
                            <div class="media d-flex">
                                <div class="align-self-center">
                                    <i class="icon-speech warning font-large-2 float-left"></i>
                                </div>
                                <div class="media-body text-right">
                                    <h3><%=questionService.getNumberOfQuestions()%>
                                    </h3>
                                    <span>Total questions</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-sm-6 col-12">
                <div class="card">
                    <div class="card-content">
                        <div class="card-body">
                            <div class="media d-flex">
                                <div class="align-self-center">
                                    <i class="icon-graph success font-large-2 float-left"></i>
                                </div>
                                <div class="media-body text-right">
                                    <%
                                        Result r = resultService.getBestPlayerFromDB();
                                        String bestPlayer = "Not available";
                                        int mostPoints = 0;
                                        if (r != null) {
                                            bestPlayer = r.getUserName() + " " + r.getUserSurname();
                                            mostPoints = r.getScore();
                                        }
                                        if (bestPlayer.equals("") || bestPlayer.equals(" ")) {
                                            bestPlayer = "Not available";
                                        }
                                    %>
                                    <h3 class="danger"><%=bestPlayer%>
                                    </h3>
                                    <span>Top Player</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-sm-6 col-12">
                <div class="card">
                    <div class="card-content">
                        <div class="card-body">
                            <div class="media d-flex">
                                <div class="align-self-center">
                                    <i class="icon-rocket danger font-large-2 float-left"></i>
                                </div>
                                <div class="media-body text-right">
                                    <h3><%=mostPoints%>
                                    </h3>
                                    <span>Most points</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xl-3 col-sm-6 col-12">
                <div class="card">
                    <div class="card-content">
                        <div class="card-body">
                            <div class="media d-flex">
                                <div class="media-body text-left">
                                    <h3>Add quiz</h3>
                                    <span>Add new quiz</span>
                                </div>
                                <div class="align-self-center">
                                    <button type="button" class="btn bmd-btn-icon" onclick="window.location = './add-quiz.jsp'">
                                        <i class="icon-film danger font-large-2 float-right"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-xl-3 col-sm-6 col-12">
                <div class="card">
                    <div class="card-content">
                        <div class="card-body">
                            <div class="media d-flex">
                                <div class="media-body text-left">
                                    <h3 class="success"><%=userService.usersCount()%>
                                    </h3>
                                    <span>Unique members</span>
                                </div>
                                <div class="align-self-center">
                                    <%
                                        boolean isDisabled = false;
                                        if (user != null) {
                                            isDisabled = (user.getRole() == 1 ? false : true);
                                        }
                                    %>
                                    <button id="addUserBtn" data-toggle="modal" data-target="#ModalMember" class="btn bmd-btn-icon">
                                        <i class="icon-user success font-large-2 float-right"></i>
                                    </button>
                                    <script>
                                        $('#addUserBtn').prop('disabled', <%=isDisabled%>);
                                    </script>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xl-6 col-md-12">
                <div class="card">
                    <div class="card-content">
                        <div class="card-body cleartfix">
                            <div class="media align-items-stretch">
                                <div class="align-self-center">
                                    <h1 class="mr-2"><%=user == null ? "" : user.getUsername()%>
                                    </h1>
                                </div>
                                <div class="media-body">
                                    <h4>Welcome back!</h4>
                                    <% if (user != null) { %>
                                    <span>Your role is: <%=(user.getRole()==1 ? "Admin" : "Editor")%></span>
                                    <% } %>
                                </div>
                                <div class="align-self-center">
                                    <button onclick="logout()" class="btn bmd-btn-icon">
                                        <i class="icon-logout danger font-large-2"></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section>
        <!-- Modal HTML Markup -->
        <div id="ModalExample" class="modal fade">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title">Add quiz</h1>
                    </div>
                    <div class="modal-body">
                        <form role="form" method="POST" action="/admin/save" enctype="multipart/form-data">
                            <input type="hidden" name="_token" value="">
                            <div class="form-group">
                                <label class="control-label">Quiz title</label>
                                <div>
                                    <input type="text" class="form-control input-lg" name="inputQuziTitle" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Quiz description</label>
                                <div>
                                    <input type="text" class="form-control input-lg" name="inputQuizDescription">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Image URL</label>
                                <div>
                                    <input type="text" class="form-control input-lg" name="inputQuizImage" value="">
                                </div>
                            </div>
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="isQuizActive">
                                <label class="form-check-label" for="isQuizActive">Is Quiz Active</label>
                                <small id="checkHelp" class="form-text text-muted">Check this to set quiz ready for play.</small>
                            </div>
                            <br>
                            <br>
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-success">Save</button>
                                    <button type="reset" class="btn btn-success">Reset</button>
                                    <button class="btn btn-danger" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </section>

    <section>
        <div id="quizUpdateModal" class="modal fade">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title">Update quiz</h1>
                    </div>
                    <div class="modal-body">
                        <form role="form" method="POST" action="/admin/updateQuiz">
                            <input type="hidden" name="_token" value="">
                            <input id="quizIdParam" type="hidden" name="quizIdParam" value="">
                            <input id="quizUserIdParam" type="hidden" name="quizUserIdParam" value="">
                            <div class="form-group">
                                <label class="control-label">Quiz title</label>
                                <div>
                                    <input required="required" id="quizTitleParam" type="text" class="form-control input-lg"
                                           name="quizTitleParam" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Quiz Description</label>
                                <div>
                                    <input required="required" id="quizDescriptionParam" type="text" class="form-control input-lg"
                                           name="quizDescriptionParam">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Image URL</label>
                                <div>
                                    <input required="required" id="quizImageUrlParam" type="text" class="form-control input-lg"
                                           name="quizImageUrlParam">
                                </div>
                            </div>
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="quizIsActiveParam" name="quizIsActiveParam">
                                <label class="form-check-label" for="quizIsActiveParam"> Is active</label>
                            </div>
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-success">Save</button>
                                    <button type="reset" class="btn btn-success">Reset</button>
                                    <button class="btn btn-danger" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </section>

    <section>
        <!-- Modal HTML Markup -->
        <div id="ModalMember" class="modal fade">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title">Add new member</h1>
                    </div>
                    <div class="modal-body">
                        <form role="form" method="POST" action="/admin/addUser">
                            <input type="hidden" name="_token" value="">
                            <div class="form-group">
                                <label class="control-label">Username</label>
                                <div>
                                    <input required="required" type="text" class="form-control input-lg" name="inputusername" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Password</label>
                                <div>
                                    <input required="required" type="password" class="form-control input-lg" name="inputpassword">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Role</label>
                                <div>
                                    <select required="required" name="inputrole">
                                        <option selected="selected" value="1">Admin</option>
                                        <option value="2">Editor</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-success">Save</button>
                                    <button type="reset" class="btn btn-success">Reset</button>
                                    <button class="btn btn-danger" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </section>

    <section>
        <div id="ModalMemberUpdate" class="modal fade">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title">Update member</h1>
                    </div>
                    <div class="modal-body">
                        <form role="form" method="POST" action="/admin/updateUser">
                            <input type="hidden" name="_token" value="">
                            <input type="hidden" id="memberOldUsernameParam" name="inputoldusername" value="">
                            <div class="form-group">
                                <label class="control-label">Username</label>
                                <div>
                                    <input required="required" id="memberUsernameParam" type="text" class="form-control input-lg"
                                           name="inputusername" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Password</label>
                                <div>
                                    <input required="required" id="memberPasswordParam" type="password" class="form-control input-lg"
                                           name="inputpassword">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Role</label>
                                <div>
                                    <select required="required" name="inputrole">
                                        <option selected="selected" value="1">Admin</option>
                                        <option value="2">Member</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-success">Save</button>
                                    <button type="reset" class="btn btn-success">Reset</button>
                                    <button class="btn btn-danger" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </section>

    <section>
        <div id="questionUpdateModal" class="modal fade">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title">Update question</h1>
                    </div>
                    <div class="modal-body">
                        <form role="form" method="POST" action="/admin/updateQuestion">
                            <input type="hidden" name="_token" value="">
                            <input id="questionId" type="hidden" name="questionId" value="">
                            <input id="quizId" type="hidden" name="quizId" value="">
                            <div class="form-group">
                                <label class="control-label">Question title</label>
                                <div>
                                    <input required="required" id="questionTitle" type="text" class="form-control input-lg"
                                           name="questionTitle" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Time to answer</label>
                                <div>
                                    <input required="required" id="questionTimeToAnswer" type="text" class="form-control input-lg"
                                           name="questionTimeToAnswer">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label">Points</label>
                                <div>
                                    <input required="required" id="questionScore" type="text" class="form-control input-lg"
                                           name="questionScore">
                                </div>
                            </div>
                            <div class="form-inline">
                                <div class="form-group">
                                    <label class="control-label">Answer 1: </label>
                                    <div>
                                        <input id="answer1" type="text" class="form-control input-lg input-text"
                                               name="answer1">
                                    </div>
                                    <label class="form-check-label label-text" for="answer1Correct">Correct: </label>
                                    <input style="" type="checkbox" class="form-check-input input-check" name="answer1Correct" id="answer1Correct">
                                </div>
                            </div>
                            <div class="form-inline">
                                <div class="form-group">
                                    <label class="control-label">Answer 2: </label>
                                    <div>
                                        <input id="answer2" type="text" class="form-control input-lg input-text"
                                               name="answer2">
                                    </div>
                                    <label class="form-check-label label-text" for="answer2Correct">Correct: </label>
                                    <input style="" type="checkbox" class="form-check-input input-check" name="answer2Correct" id="answer2Correct">
                                </div>
                            </div>
                            <div class="form-inline">
                                <div class="form-group">
                                    <label class="control-label">Answer 3: </label>
                                    <div>
                                        <input id="answer3" type="text" class="form-control input-lg input-text"
                                               name="answer3">
                                    </div>
                                    <label class="form-check-label label-text" for="answer3Correct">Correct: </label>
                                    <input style="" type="checkbox" class="form-check-input input-check" name="answer3Correct" id="answer3Correct">
                                </div>
                            </div>
                            <div class="form-inline">
                                <div class="form-group">
                                    <label class="control-label">Answer 4: </label>
                                    <div>
                                        <input id="answer4" type="text" class="form-control input-lg input-text"
                                               name="answer4">
                                    </div>
                                    <label class="form-check-label label-text" for="answer4Correct">Correct: </label>
                                    <input style="" type="checkbox" class="form-check-input input-check" name="answer4Correct" id="answer4Correct">
                                </div>
                            </div>
                            <br>
                            <br>
                            <div class="form-group">
                                <div>
                                    <button type="submit" class="btn btn-success">Save</button>
                                    <button type="reset" class="btn btn-success">Reset</button>
                                    <button onclick="deleteQuestion(document.getElementById('questionId').value)" class="btn btn-danger" data-dismiss="modal">Delete</button>
                                    <button class="btn btn-danger" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </section>

    <section>
        <!-- Modal HTML Markup -->
        <div id="questionsModal" class="modal fade">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title">Question List</h1>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="quizIdForQuestion" name="quizIdForQuestion" value="">
                        <ul id="sortable">
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <div class="form-group">
                            <div>
                                <button onclick="onSortModalClose()" class="btn btn-danger" data-dismiss="modal">Save</button>
                            </div>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </section>

    <%
        HashMap<String, String> messages = (HashMap<String, String>) request.getAttribute("userUpdateMessage");
        if (messages != null) {
            userExists = (messages.get("userExists") == null) ? "" : messages.get("userExists");
            password = (messages.get("password") == null) ? "" : messages.get("password");
            successEdit = (messages.get("successEdit") == null) ? "" : messages.get("successEdit");
        } else {
            messages = (HashMap<String, String>) request.getAttribute("userAddMessage");
            if (messages != null) {
                userExists = (messages.get("userExists") == null) ? "" : messages.get("userExists");
                password = (messages.get("password") == null) ? "" : messages.get("password");
                successEdit = (messages.get("successEdit") == null) ? "" : messages.get("successEdit");
            }
        }
        if (messages != null && (!userExists.equals("") || !password.equals(""))) {
    %>
    <script>
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
        toastr["error"]("<%=(userExists + password)%>");
    </script>
    <%
        }
    %>

    <%
        if (messages != null && (!successEdit.equals(""))) {
    %>
    <script>
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
        toastr["success"]("<%=(successEdit)%>");
    </script>
    <%
        }
    %>

    <section>
        <!-- Tab links -->
        <div class="tab">
            <button class="tablinks" onclick="openTab(event, 'QuizTab')">Quizzes</button>
            <% if (user != null && user.getRole() == 1) { %>
            <button class="tablinks" onclick="openTab(event, 'MembersTab')">Members</button>
            <% } %>
            <button class="tablinks" onclick="openTab(event, 'ResultsTab')">Results</button>
        </div>

        <!-- Tab content -->
        <div id="QuizTab" class="tabcontent">
            <table class="table table-hover table-light shadow-sm" id="quiz-table">
                <%
                    List<Quiz> quizzes = quizService.getQuizzes();
                    String sortableList = "";
                    List<String> sortableListArray = new ArrayList<String>();
                %>
                <script>
                    let jsSortArray = [];
                </script>
                <thead>
                <tr class="shadow-sm">
                    <th scope="col"></th>
                    <th scope="col" class="align-left">About quiz</th>
                    <th scope="col">Is active</th>
                    <th scope="col">Edit quiz info</th>
                    <th scope="col">Edit questions</th>
                    <th scope="col">Sort questions</th>
                    <th scope="col">Delete quiz</th>
                </tr>
                </thead>
                <tbody id="ranking-list">
                <%
                    for (int i = 0; i < quizzes.size(); ++i) {
                        Quiz quiz = quizzes.get(i);
                        String elementId = "table-row" + i;
                %>
                <tr class="shadow-sm" id="table-row<%=i%>">
                    <td><img
                            class="image-box img-fluid quiz-logo"
                            src="<%=quiz.getImageUrl()%>"
                            alt=""></td>
                    <td class="align-left">
                        <p class="quiz-title"><%=quiz.getTitle()%>
                        </p>
                        <p class="quiz-description"><%=quiz.getDescription()%>
                        </p>
                    </td>
                    <td>
                        <%
                            if (quiz.isActive()) {

                        %>
                        <i class="material-color-green material-icons md-48">check_circle</i>
                        <%
                            } else {
                        %>
                        <i class="material-color-red material-icons md-48">highlight_off</i>
                        <%
                            }
                        %>
                    </td>
                    <td>
                        <% if (user != null) { %>
                        <button onclick="$('#quizTitleParam').val('<%=quiz.getTitle()%>'); $('#quizDescriptionParam').val('<%=quiz.getDescription()%>'); $('#quizImageUrlParam').val('<%=quiz.getImageUrl()%>'); $('#quizIsActiveParam').attr('checked', <%=quiz.isActive()%>); $('#quizIdParam').val(<%=quiz.getId()%>); $('#quizUserIdParam').val(<%=user.getId()%>);"
                                data-toggle="modal" data-target="#quizUpdateModal" type="button"
                                class="btn btn-success">Update
                        </button>
                        <% } %>
                    </td>
                    <td>
                        <div class="btn-group dropup">
                            <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Edit questions
                            </button>
                            <div class="dropdown-menu">
                                <% for (int j = 0; j < quiz.getQuestions().size(); ++j) {
                                    Question q = quiz.getQuestions().get(j);
                                    sortableList += "<li id='" + j + "' class='ui-state-default'><span class='ui-icon ui-icon-arrowthick-2-n-s'></span>" + q.getTitle() + "</li>";
                                    List<Answer> a = q.getAnswers();
                                %>
                                    <a class="dropdown-item" href="#" onclick="$('#quizId').val('<%=q.getQuiz().getId()%>'); $('#questionId').val('<%=q.getId()%>'); $('#questionTitle').val('<%=q.getTitle()%>'); $('#questionTimeToAnswer').val('<%=q.getTimeToAnswer()%>'); $('#questionScore').val('<%=q.getScore()%>');
                                            <% for (int k = 0; k < a.size(); ++k) { %>
                                            $('#answer' + (<%=k+1%>) + 'Correct').attr('checked', <%=a.get(k).isCorrect()%>);
                                            $('#answer' + (<%=k+1%>)).val('<%=a.get(k).getTitle()%>');
                                            <% } %>"
                                       data-toggle="modal" data-target="#questionUpdateModal">
                                        <%=q.getTitle().length() > 50 ? q.getTitle().substring(0,50) : q.getTitle()%>
                                    </a>
                                <% } sortableListArray.add(sortableList); sortableList = ""; %>
                            </div>
                        </div>
                    </td>
                    <td>
                        <script>
                            jsSortArray.push("<%=sortableListArray.get(i)%>");
                        </script>
                        <button onclick="$('#quizIdForQuestion').val('<%=quiz.getId()%>'); initialize(jsSortArray[<%=i%>])" data-toggle="modal" data-target="#questionsModal" class="btn btn-primary" type="button">Sort questions</button>
                    </td>
                    <td>
                        <button onclick="deleteRowFromTable('<%=elementId%>', '<%=quiz.getId()%>')" type="button" class="btn btn-danger">Delete</button>
                    </td>
                </tr>
                <%
                    }
                %>
                <script>
                    $( function() {
                        $( "#sortable" ).sortable();
                        $( "#sortable" ).disableSelection();
                    } );
                    function initialize(questionList) {
                        let list = document.getElementById('sortable');
                        list.innerHTML = questionList;
                    }
                </script>
                </tbody>
            </table>
        </div>

        <div id="MembersTab" class="tabcontent">
            <table class="table table-hover table-light shadow-sm" id="members-table">
                <%
                    int usersCount = (int) userService.usersCount();
                    List<User> memberList = userService.findAll(usersCount, 0);
                %>
                <thead>
                <tr class="shadow-sm">
                    <th scope="col" class="align-left">Username</th>
                    <th scope="col">Password (hashed)</th>
                    <th scope="col">Role</th>
                    <th scope="col">Update</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody id="ranking-list">
                <%
                    for (int i = 0; i < usersCount; ++i) {
                        User userLocal = memberList.get(i);
                        String elementIdMember = "member-row" + i;
                %>
                <tr class="shadow-sm" id="member-row<%=i%>">
                    <td class="align-left">
                        <p class="quiz-title"><%=userLocal.getUsername()%>
                        </p>
                    </td>
                    <td><%=userLocal.getPassword()%>
                    </td>
                    <td><%=userLocal.getRole() == 1 ? "Admin" : "Editor"%>
                    </td>
                    <td>
                        <button onclick="$('#memberUsernameParam').val('<%=userLocal.getUsername()%>'); $('#memberOldUsernameParam').val('<%=userLocal.getUsername()%>');"
                                data-toggle="modal" data-target="#ModalMemberUpdate" type="button"
                                class="btn btn-success">Update
                        </button>
                    </td>
                    <td>
                        <% if (user != null) { %>
                        <button onclick="deleteUser('<%=elementIdMember%>', '<%=userLocal.getUsername()%>', '<%=user.getUsername()%>')"
                                class="btn btn-danger">Delete
                        </button>
                        <% } %>
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <div id="ResultsTab" class="tabcontent">
            <table class="table table-hover table-light shadow-sm" id="results-table">
                <%
                    List<Result> resultsList = null;
                    if (user != null) {
                        resultsList = resultService.getResultsOfAllQuizzesOfUser(user.getId());
                    }
                %>
                <thead>
                <tr class="shadow-sm">
                    <td scope="col" class="align-left">
                        <button id="exportResultsBtn" type="button" class="btn btn-warning">Export results</button>
                    </td>
                </tr>
                <tr class="shadow-sm">
                    <th scope="col" class="align-left">First name</th>
                    <th scope="col">Last name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Score</th>
                    <th scope="col">Quiz</th>
                </tr>
                </thead>
                <% if (resultsList != null) { %>
                <tbody id="ranking-list">
                <% if (resultsList.size() == 0) { %>
                <script>
                    $('#exportResultsBtn').click(function() {
                        toastr["error"]("There are no results to export!");
                    });
                </script>
                <% } else { %>
                <script>
                    $('#exportResultsBtn').click(function() {
                        exportResults('<%=(user != null) ? user.getId() : null%>');
                    });
                </script>
                <% } %>
                <%
                    for (int i = 0; i < resultsList.size(); ++i) {
                        Result result = resultsList.get(i);
                        String elementIdResult = "member-row" + i;
                %>
                <tr class="shadow-sm" id="result-row<%=i%>">
                    <td class="align-left">
                        <p><%=result.getUserName()%>
                        </p>
                    </td>
                    <td><%=result.getUserSurname()%>
                    </td>
                    <td><%=result.getUserEmail()%>
                    </td>
                    <td><%=result.getScore()%>
                    </td>
                    <td><%=result.getQuiz().getTitle()%>
                    </td>
                </tr>
                <%
                    }
                %>
                </tbody>
                <% } %>
            </table>
        </div>
    </section>
</div>
</body>
</html>
