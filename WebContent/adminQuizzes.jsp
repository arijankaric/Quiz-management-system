<%@ page language="java" contentType="text/html"
	import="Model.User, Model.Quiz, service.UserService, service.QuizService, java.util.*, dao.QuizDao, dao.UserDao, javax.servlet.http.HttpServletRequest, java.lang.Math"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="../css/all.min.css" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../scripts/jquery-3.6.1.min.js"></script>
<!-- <script src="../scripts/popper.min.js"></script> -->
<link rel="stylesheet" href="../css/adminQuizzes.css">
<script src="../scripts/bootstrap.min.js"></script>
<script src="../scripts/adminQuizzes.js" defer></script>
</head>
<body>
	<%
	QuizService quizService = new QuizService(new QuizDao());
	UserService userService = new UserService(new UserDao());
	User user = (User) session.getAttribute("user");
	if(user == null) {
		String username = (String) session.getAttribute("username");
		if(username == null){
			System.out.println("no username");
		} else {
			System.out.println(username);
		}
		user = userService.findByUsername(username);
	}
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

		<h3 class="i-name">Quizzes</h3>
		<div class="values">
			<div class="val-box">
				<i class="fas fa-trophy"></i>
				<div>
					<h3 id="quizzes"><%=quizService.getNumberOfQuizzes()%></h3>
					<span>Quizzes</span>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="delete-quiz">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Delete quiz</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close"
							onclick=" document.getElementById('delete-quiz').style.display = 'none'">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Do you want to delete this quiz?</p>
						<div>
							<label
								class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
								style="display: none;" id="label-delete">You cannot
								delete this quiz!</label>
						</div>

					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							id="delete-quiz-btn" onclick="deleteQuiz(this.value)">
							Yes</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal"
							onclick="document.getElementById('delete-quiz').style.display = 'none'">No
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="edit-quiz">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Edit quiz</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body mbody" id="edit-quiz-body"
						enctype="multipart/form-data">
						<form method="POST" id="edit">
							<div class="form-group">
								<label for="edit-title">Title</label> <input placeholder="Title"
								class="form-control mb-3" for="edit-title" name="edit-title"
								id="edit-title" required />
							</div>
							<div class="form-group">
								<label for="edit-description">Description</label>
								<textarea class="form-control" id="edit-description" rows="3"></textarea>
							</div>
							<div class="form-group">
								<input type="file" name="edit-image-file" id="edit-image-file" />
								<input style="display: none;" id="edit-id" name="edit-id" />
							</div>
							<button type="submit" class="btn d btn-default pull-right"
								id="edit-quiz-submit">Save</button>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="add-quiz">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Add quiz</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close"
							onclick='document.getElementById("add-quiz").style.display = "none"'>
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body mbody" id="add-quiz-body"
						enctype="multipart/form-data">
						<form method="POST" id="add">
							<label for="add-title">Title</label> <input placeholder="Title"
								class="form-control mb-3" for="add-title" name="add-title"
								id="add-title" required />
							<div class="form-group">
								<label for="add-description">Description</label>
								<textarea class="form-control" id="add-description" rows="3"></textarea>
							</div>
							<input type="file" name="add-image-file" id="add-image-file" />
							<div style="margin-top: 20px; text-align: right">
								<button type="submit" class="btn submit btn-default pull-right"
									id="add-quiz-submit">Save</button>
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
						<td>Name</td>
						<td>Creator</td>
						<td>Description</td>
						<td>Quiz Code</td>
						<td class="edit" onclick="document.getElementById('add-quiz').style.display = 'block'">Create</td>
					</tr>
				</thead>
				<tbody>
					<%
					String pageStr = request.getParameter("page");
					Integer pageNum;
					if (pageStr != null)
						pageNum = Integer.valueOf(pageStr);
					else {
						pageNum = new Integer(1);
						// 						session.setAttribute("page", pageNum.intValue());
						// 						session.getAttribute("username")
					}
					if (pageNum < 1)
						pageNum = 1;
					int start = (pageNum - 1) * 5;
					int end = start + 5;
					List<Quiz> quizList = quizService.getSomeQuizzes(5, start);
					for (Integer i = 0; i < quizList.size(); ++i) {
					%>
					<tr>
						<td class="people"><img alt=""
							src="../<%=quizList.get(i).getImageUrl()%>">
							<div class="people-de">
								<h5><%=quizList.get(i).getTitle()%></h5>
								<!-- 								<p>3495</p> -->
							</div></td>
						<td class="people-des">
							<h5><%=quizList.get(i).getOwner().getUsername()%></h5> <!-- 							<p>arijankaric@yahoo.com</p> -->
						</td>
						<td class="role">
							<p><%=quizList.get(i).getDescription()%></p>
						</td>
						<td class="notactive">
							<p>Inactive</p>
						</td>
						<td class="hidden"><%=quizList.get(i).getId()%></td>
						<td class="td-icon">
							<button class="btn bg-transparent"
								value=<%=quizList.get(i).getId()%>
								onclick="startQuiz(this.value)">
								<i class="fa fa-play" aria-hidden="true"></i>
							</button>
							<button class="btn bg-transparent edit-btn" data-toggle="modal"
								data-target="#edit-quiz">
								<i class="fa fa-edit"></i>
							</button>
							<button class="btn bg-transparent"
								value=<%=quizList.get(i).getId()%>
								onclick="openDeleteModal(this.value)">
								<i class="fa fa-trash"> </i>
							</button>
							<button class="btn bg-transparent"
								value=<%=quizList.get(i).getId()%>
								onclick="goToLocation(this.value)">
								<i class="fa fa-list"> </i>
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

	<script>
		const currentUsername = '<%=user.getUsername()%>';
		const currentRole =	<%=user.getRole()%>;
		console.log('currentUsername: ' + currentUsername);
		console.log('currentRole: ' + currentRole);
	</script>
</body>
</html>