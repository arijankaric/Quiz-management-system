<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         import="java.util.HashMap" %>

<%!
    private HashMap<String, String> messages;
    private String loginMsg = "";
    private String passwordMsg = "";
    private String genericMsg = "";
%>
<html>
<head>
    <title>Admin Login</title>
    <!-- Custom CSS -->
    <link rel="stylesheet" href="../css/login.css">
    <link rel="stylesheet" type="text/css" href="../css/toastr.css"/>
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
</head>
<body>
<div class="container">
    <div class="screen">
        <div class="screen__content">
            <form action="login" method="post" class="login">
                <div class="login__field">
                    <i class="login__icon fas fa-user"></i>
                    <input name="username" type="text" class="login__input" placeholder="User name / Email">
                </div>
                <div class="login__field">
                    <i class="login__icon fas fa-lock"></i>
                    <input name="password" type="password" class="login__input" placeholder="Password">
                </div>
                <button class="button login__submit">
                    <span class="button__text">Log In Now</span>
                    <i class="button__icon fas fa-chevron-right"></i>
                </button>
                <%
                    messages = (HashMap<String, String>) request.getAttribute("messages");
                    if (messages != null) {
                        loginMsg = (messages.get("username") == null) ? "" : messages.get("username");
                        passwordMsg = (messages.get("password") == null) ? "" : messages.get("password");
                        genericMsg = (messages.get("login") == null) ? "" : messages.get("login");
                    }
                    if (messages != null && (!loginMsg.equals("") || !passwordMsg.equals("") || !genericMsg.equals(""))) {
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
                        toastr["error"]("<%=(loginMsg + passwordMsg + genericMsg)%>");
                    </script>
                <%
                    }
                %>
            </form>
        </div>
        <div class="screen__background">
            <span class="screen__background__shape screen__background__shape4"></span>
            <span class="screen__background__shape screen__background__shape3"></span>
            <span class="screen__background__shape screen__background__shape2"></span>
            <span class="screen__background__shape screen__background__shape1"></span>
        </div>
    </div>
</div>
</body>
</html>
