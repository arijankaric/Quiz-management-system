<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>   
<div class="container" style="margin-top: 10px;">
            <div class="row"
                 style="border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;">
                <div class="col-sm-12">

                    <h2 class="myclass">Login - Ranking Accounts</h2>
                    <form action="login" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>username</label> 
                            <input type="text" class="form-control" name="username" placeholder="Enter username">
                        </div>
                        <div class="form-group">
                            <label>password</label> 
                            <input type="password" 
                                   class="form-control" name="password" placeholder="Enter password">
                        </div>
                        <button type="submit" class="btn btn-primary">Login</button>
                        <button type="reset" class="btn btn-primary">Cancel</button>
                    </form>
                </div>
            </div>
        </div>

<!--     <form action="login" method="post">   -->
<!--     Name:<input type="text" name="username"/><br/>   -->
<!--     Password:<input type="password" name="password"/><br/>   -->
      
<!--     <input type="submit" value="login">   -->
      
    </form>  
</body>
</html>