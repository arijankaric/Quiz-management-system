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
<script src="../scripts/bootstrap.min.js"></script>
<!-- <script src="../scripts/popper.min.js"></script> -->
<link rel="stylesheet" href="../css/editQuiz.css">
<!-- <link rel="stylesheet" href="../css/center.css" /> -->
<script src="../scripts/editQuiz.js" defer></script>

<!-- <link rel="stylesheet" href="../css/styles.css"> -->
<script src="../scripts/dragNDrop.js" defer></script>
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
		
		<h3 class="i-name">Questions</h3>
		<div class="values">
			<div class="val-box">
				<i class="fa fa-question-circle" aria-hidden="true"></i>
				<div>
					<h3 id="quizzes"><%=quiz.getQuestions().size()%></h3>
					<span>Questions</span>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="delete-question">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Delete question</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close"
							onclick=" document.getElementById('delete-question').style.display = 'none'">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Do you want to delete this question?</p>
						<div>
							<label
								class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
								style="display: none;" id="label-delete">You cannot
								delete this question!</label>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							id="delete-question-btn" onclick="deleteQuestion(this.value)">
							Yes</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal"
							onclick="document.getElementById('delete-question').style.display = 'none'">No
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="add-question">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Add question</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close"
							onclick='document.getElementById("add-question").style.display = "none"'>
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body mbody" id="add-question-body">
						<form method="POST" id="add">
							<div class="form-group">
								<label for="add-title">Title</label>
								<input placeholder="Title" class="form-control mb-3"
								name="add-title" id="add-title" required />
							</div>
							<div class="form-group">
								<label for="add-score">Score</label><input type="number"
								placeholder="Score" class="form-control mb-3" name="add-score"
								id="add-score" required />
							</div>
							<div class="form-group">
							 	<label for="add-time">Time</label> <input type="number"
								placeholder="Time in seconds" class="form-control mb-3"
								name="add-time" id="add-time" required />
							</div>	
							<div style="margin-top: 20px; text-align: right">
								<button type="submit" class="btn submit btn-default pull-right"
									id="add-question-submit">Save</button>
							</div>
							<div>
                        		<label class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
                               style="display: none;" id="label-time-add-lower">Time to answer at the very least 15 seconds</label>
                           	</div>
                           	<div>
                        		<label class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
                               style="display: none;" id="label-time-add-higher">Time to answer must not be longer than a minute</label>
                           	</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="edit-question">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Edit question</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body mbody" id="edit-question-body">
						<form method="POST" id="edit">
							<div class="form-group">
							<label for="edit-title">Title</label>
							<input placeholder="Title" class="form-control mb-3"
								name="edit-title" id="edit-title" required /> 
							</div>
							<div class="form-group">
								<label for="edit-score">Score</label>
								<input type="number" placeholder="Score" class="form-control mb-3" name="edit-score" id="edit-score" required />
							</div>
							<div class="form-group">
							<label for="edit-time">Time in seconds</label>
							<input
								type="number" placeholder="Time in seconds"
								class="form-control mb-3" name="edit-time" id="edit-time"
								required />
							</div>
								<input style="display: none;" id="edit-id"
								name="edit-id" />
							<button type="submit" class="btn d btn-default pull-right"
								id="edit-question-submit">Save</button>
								
							<div>
                        		<label class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
                               style="display: none;" id="label-time-edit-lower">Time to answer at the very least 15 seconds</label>
                           	</div>
                           	<div>
                        		<label class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
                               style="display: none;" id="label-time-edit-higher">Time to answer must not be longer than a minute</label>
                           	</div>
						</form>
					</div>
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
						<td class="edit" onclick="document.getElementById('add-question').style.display = 'block'">Add Question</td>
					</tr>
				</thead>
				<tbody id="kontejner" class="container">
					<%
					List<Question> questionList = quiz.getQuestions();
					System.out.println("questionList.size(): " + questionList.size());
					for (Integer i = 0; i < questionList.size(); ++i) {
					%>
					<tr class="draggable" draggable="true">
						<td class="people">
							<div class="people-de">
								<h5><%=questionList.get(i).getOrdinalNumber()%></h5>
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
						<td class="hidden"><%=questionList.get(i).getId()%></td>
						<td class="edit">
							<button class="btn bg-transparent edit-btn" data-toggle="modal"
								data-target="#edit-question">
								<i class="fa fa-edit"></i>
							</button>
							<button class="btn bg-transparent"
								value=<%=questionList.get(i).getId()%>
								onclick="openDeleteModal(this.value)">
								<i class="fa fa-trash"> </i>
							</button>
							<button class="btn bg-transparent"
								value="<%=questionList.get(i).getId()%>"
								onclick="goToLocation(this.value)">
								<i class="fa fa-list"> </i>
							</button>
							<%--  <a href="editQuestion?id=<%=questionList.get(i).getId()%>">Edit</a>  --%>
							<%-- <a href="deleteQuestion?id=<%=questionList.get(i).getId()%>">Delete</a> --%>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</section>
	<script>
		const quizId = '<%=request.getParameter("id").toString()%>'
	</script>
</body>
</html>