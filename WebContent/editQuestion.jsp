<%@ page language="java" contentType="text/html"
	import="Model.*, service.*, java.util.*, dao.*, javax.servlet.http.HttpServletRequest, java.lang.Math"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="../css/all.min.css" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../scripts/jquery-3.6.1.min.js"></script>
<script src="../scripts/popper.min.js"></script>
<script src="../scripts/bootstrap.min.js"></script>
<link rel="stylesheet" href="../css/editQuiz.css">
<!-- <link rel="stylesheet" href="../css/center.css" /> -->
<script src="../scripts/editQuestion.js" defer></script>
<!-- <link rel="stylesheet" href="../css/styles.css"> -->
</head>
<body>
	<%
	QuestionService questionService = new QuestionService(new QuestionDao());
	int quizId = Integer.parseInt(request.getParameter("id")); // get
	Question question = questionService.getQuestionById(quizId);
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

		<h3 class="i-name">Answers</h3>
		<div class="values">
			<div class="val-box">
				<i class='fas fa-lightbulb'></i>
				<!-- 				<i class='far fa-lightbulb'></i> -->
				<div>
					<h3 id="quizzes">Question Title</h3>
					<span><%=question.getTitle()%></span>
				</div>
			</div>
		</div>

		<!-- 		<div class='container' style='margin-top: 100px;'> -->
		<!-- 			<div class='row' -->
		<!-- 				style='border: 1px darkgrey solid; border-radius: 10px; width: 50%; margin: 0 auto; padding: 20px;'> -->
		<!-- 				<div class='col-sm-12'> -->
		<!-- 					<h2 class='myclass'>Modify Question</h2> -->
		<!-- 					<form action='editQuestion' method='post' -->
		<!-- 						enctype='multipart/form-data'> -->
		<!-- 						<div class='form-group'> -->
		<!-- 							<label>Question Title</label> <input type='text' -->
		<%-- 								class='form-control' name='id' value='<%=question.getTitle()%>'> --%>
		<!-- 						</div> -->
		<!-- 						<div class='form-group'> -->
		<!-- 							<label>Time to answer</label> <input type='text' -->
		<!-- 								class='form-control' name='name' -->
		<%-- 								value='<%=question.getTimeToAnswer()%>'> --%>
		<!-- 						</div> -->
		<!-- 						<div class='form-group'> -->
		<!-- 							<label></label>Score<input type='text' class='form-control' -->
		<%-- 								name='name' value='<%=question.getScore()%>'> --%>
		<!-- 						</div> -->

		<!-- 						<button type='submit' class='btn btn-primary'>Save</button> -->
		<!-- 						<button type='reset' class='btn btn-primary' -->
		<!-- 							onclick="location.href='questions'">Cancel</button> -->
		<!-- 					</form> -->
		<!-- 				</div> -->
		<!-- 			</div> -->
		<!-- 		</div> -->

		<div class="modal" tabindex="-1" role="dialog" id="edit-answer">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Edit answer</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body mbody" id="edit-answer-body">
						<form method="POST" id="edit">
							<div class="form-group">
								<label for="edit-title">Title</label>
								<textarea class="form-control" name="edit-title" id="edit-title"
									rows="3"></textarea>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox" value=""
									name="edit-correct" id="edit-correct"> <label
									class="form-check-label" for="edit-correct"> Correct </label>
							</div>
							<input style="display: none;" id="edit-id" name="edit-id" />
							<button type="submit" class="btn d btn-default pull-right"
								id="edit-answer-submit">Save</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="board">
			<table width="100%">
				<thead>
					<tr>
						<td>Title</td>
						<td>Is Correct</td>
						<td></td>
					</tr>
				</thead>
				<tbody id="kontejner" class="container">
					<%
					List<Answer> answers = question.getAnswers();
					for (Integer i = 0; i < answers.size(); ++i) {
					%>
					<tr>
						<td class="people">
							<div class="people-de">
								<h5><%=answers.get(i).getTitle()%></h5>
							</div>
						</td>
						<td class="people-des">
							<h5><%=answers.get(i).isCorrect()%></h5>
						</td>
						<td class="hidden"><%=answers.get(i).getId()%></td>
						<td class="edit">
							<button class="btn bg-transparent edit-btn" data-toggle="modal"
								data-target="#edit-answer">
								<i class="fa fa-edit"></i>
							</button>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</section>
</body>
</html>