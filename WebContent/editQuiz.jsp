<%@ page language="java" contentType="text/html" 
	import="Model.*, service.*, java.util.*, dao.*, javax.servlet.http.HttpServletRequest, java.lang.Math"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />
<!--     <script defer src="https://use.fontawesome.com/releases/v5.15.4/js/all.js"></script> -->
<link rel="stylesheet" href="../css/editQuiz.css">
<!-- <link rel="stylesheet" href="../css/center.css" /> -->
<script src="../scripts/editQuiz.js" defer></script>
    
<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css'>
<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js'></script>
<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js'></script>

<link rel="stylesheet" href="../css/styles.css">
<script src="../scripts/dragNDrop.js" defer></script>

<!-- 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> -->
<!-- <script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script> -->
<!-- <link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css"> -->
<!-- <link rel="stylesheet" href="../css/expandableTable.css"> -->
<!-- <script src="../scripts/expandableTable.js" defer></script> -->

<!-- <link rel="stylesheet" href="../css/bootstrap.css"> -->
<!-- <link rel="stylesheet" href="../css/font-awesome.css"> -->

<!-- <script src="../scripts/jquery.min.js" defer></script> -->
<!-- <script src="../scripts/bootstrap.js"></script> -->
<!-- <script src="../scripts/jquery.tabledit.min.js"></script> -->
<!-- <script src="../scripts/adminQuizzes.js"></script> -->


</head>
<body>
	<%
	QuizService quizService = new QuizService(new QuizDao());
	QuestionService questionService = new QuestionService(new QuestionDao());
	ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
	String quizId = request.getParameter("id"); // get
	Quiz quiz = quizService.getQuizById(Integer.valueOf(quizId));
	%>
	<section id="menu">
		<div class="logo">
			<img alt="" src="../imgs/quiz.png">
			<h2>RWAQuiz</h2>
		</div>

		<div class="items">
			<li><i class="fad fa-chart-pie-alt"></i><a href="index">Dashboard</a></li>
			<li><i class="fas fa-th-large"></i><a href="quizzes">Quizzes</a></li>
			<li><i class="fas fa-edit"></i><a href="admins">Admins</a></li>
		</div>
	</section>

	<section id="interface">
		<div class="navigation">
			<div class="n1">
				<div>
					<i id="menu-btn" class="fas fa-bars"></i>
				</div>

				<div class="search">
					<i class="far fa-search"></i> <input type="text"
						placeholder="Search">
				</div>
			</div>

			<div class="profile">
				<i class="far fa-bell"></i> <img alt="" src="../imgs/1.jpg">
			</div>
		</div>

		<div class='container' style='margin-top: 100px;'>
			<div class='row'
				style='border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;'>
				<div class='col-sm-12'>
					<h2 class='myclass'>Modify Quiz</h2>
					<form action='quizEdit' method='post'
						enctype='multipart/form-data'>
						<div class='form-group'>
							<label>Quiz Title</label> <input type='text'
								class='form-control' name='id'
								value='<%=quiz.getTitle()%>'>
						</div>
						<div class='form-group'>
							<label>Quiz Description</label> <input type='text' class='form-control'
								name='name' value='<%=quiz.getDescription()%>'>
						</div>
						
						<div class='form-group'>
							<label>Photo</label> <br /> <img width='120' height='90'
								src='../<%=quiz.getImageUrl()%>' class='portrait' /> <input type='file'
								class='form-control' name='file' placeholder='Enter photo'>
						</div>
						<button type='submit' class='btn btn-primary'>Save</button>
						<button type='reset' class='btn btn-primary' onclick=
							"location.href='quizzes'">Cancel</button>
					</form>
				</div>
			</div>
		</div>

		<div class="board">
			<table width="100%">
				<thead>
					<tr>
						<td>Id</td>
						<td>Question</td>
						<td>Time</td>
						<td>Points</td>
						<td></td>
					</tr>
				</thead>
				<tbody id="kontejner" class="container">
					<%
						List<Question> questionList = quiz.getQuestions();
						for (Integer i = 0; i < questionList.size(); ++i) {
					%>
					<tr class="draggable" draggable="true">
						<td class="people">
							<div class="people-de">
								<h5><%=questionList.get(i).getId() %></h5>
							</div>
						</td>						
						<td class="people-des">
							<h5><%=questionList.get(i).getTitle()%></h5>
						</td>
						<td class="role">
							<h5><%=questionList.get(i).getTimeToAnswer()%></h5>
						</td>
						<td class="role">
							<h5><%=questionList.get(i).getScore()%></h5>
						</td>
						
						<td class="edit">
							<a href="#">Edit</a>
						</td>
					</tr>
				<%
				}
 				%>
				</tbody>
			</table>
	</section>
<!-- 					<tr> -->
<!-- 						<td colspan="5"> -->
<!-- 							<div class="container" style="margin-top: 35px"> -->
<!-- 								<h4>Questions</h4> -->
<!-- 								<table id="tabledit" class="table table-bordered table-striped"> -->
<!-- 									<thead> -->
<!-- 										<tr> -->
<!-- 											<th>ID</th> -->
<!-- 											<th>Question</th> -->
<!-- 											<th>Time To Answer</th> -->
<!-- 											<th>Score</th> -->
<!-- <!-- 											<th>Phone</th> --> 
<!-- <!-- 											<th>Address</th> -->
<!-- 										</tr> -->
<!-- 									</thead> -->

<!-- 									<tbody> -->
<!-- 										<tr id="row1"> -->
<!-- 											<td name="id">1</td> -->
<!-- 											<td name="question">Pitanje je</td> -->
<!-- 											<td name="timeToAnswer">20</td> -->
<!-- 											<td name="score">10</td> -->
<!-- <!-- 											<th>Phone1</th> -->
<!-- <!-- 											<th>Address1</th> -->
<!-- 										</tr> -->
<!-- 									</tbody> -->
<!-- 								</table> -->
<!-- 							</div> -->
<!-- 						</td> --
<!-- 					</tr> -->

						<!-- 						<td colspan="5"><h4>Additional information</h4> -->
<!-- 							<ul> -->
<!-- 								<li><a href="http://en.wikipedia.org/wiki/Usa">USA on -->
<!-- 										Wikipedia</a></li> -->
<!-- 								<li><a href="http://nationalatlas.gov/">National Atlas -->
<!-- 										of the United States</a></li> -->
<!-- 								<li><a -->
<!-- 									href="http://www.nationalcenter.org/HistoricalDocuments.html">Historical -->
<!-- 										Documents</a></li> -->
<!-- 							</ul></td> -->
<!-- 					<tr> -->
<!-- 						<td>United Kingdom</td> -->
<!-- 						<td>61,612,300</td> -->
<!-- 						<td>244,820 km2</td> -->
<!-- 						<td>English</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td colspan="5"><h4>Additional information</h4> -->
<!-- 							<ul> -->
<!-- 								<li><a href="http://en.wikipedia.org/wiki/United_kingdom">UK -->
<!-- 										on Wikipedia</a></li> -->
<!-- 								<li><a href="http://www.visitbritain.com/">Official -->
<!-- 										tourist guide to Britain</a></li> -->
<!-- 								<li><a -->
<!-- 									href="http://www.statistics.gov.uk/StatBase/Product.asp?vlnk=5703">Official -->
<!-- 										Yearbook of the United Kingdom</a></li> -->
<!-- 							</ul></td> -->
<!-- 					</tr> -->



</body>
</html>