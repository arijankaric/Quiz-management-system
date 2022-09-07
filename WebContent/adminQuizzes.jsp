<%@ page language="java" contentType="text/html" 
	import="Model.Quiz, service.QuizService, java.util.*, dao.QuizDao, javax.servlet.http.HttpServletRequest, java.lang.Math"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" />
<!--     <script defer src="https://use.fontawesome.com/releases/v5.15.4/js/all.js"></script> -->
<link rel="stylesheet" href="../css/adminQuizzes.css">
<!--     <script src="../scripts/adminIndex.js" defer></script> -->

<!-- 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css"> -->
<script src="http://code.jquery.com/jquery-2.1.4.min.js" type="text/javascript"></script>
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
	ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
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

		<div class="board">
			<table width="100%">
				<thead>
					<tr>
						<td>Name</td>
						<td>Creator</td>
						<td>Description</td>
						<td>Quiz Code</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
				<%
					String pageStr = request.getParameter("page");
					Integer pageNum;
					if(pageStr != null)
						pageNum = Integer.valueOf(pageStr);
					else
					{
						pageNum = new Integer(1);
// 						session.setAttribute("page", pageNum.intValue());
// 						session.getAttribute("username")
					}
					if(pageNum < 1)
						pageNum = 1;
					int start = (pageNum - 1) * 5;
					int end = start + 5;
					List<Quiz> quizList = quizService.getSomeQuizzes(5, start);
					for (Integer i = 0; i < quizList.size(); ++i) {
				%>
					<tr>
						<td class="people">
							<img alt="" src="../<%=quizList.get(i).getImageUrl() %>">
							<div class="people-de">
								<h5><%=quizList.get(i).getTitle() %></h5>
<!-- 								<p>3495</p> -->
							</div>
						</td>						
						<td class="people-des">
							<h5><%=quizList.get(i).getOwner().getUsername()%></h5>
<!-- 							<p>arijankaric@yahoo.com</p> -->
						</td>
						<td class="role">
							<p><%=quizList.get(i).getDescription()%></p>
						</td>
						<td class="notactive">
							<p>Inactive</p>
						</td>
						
						<td class="edit">
							<a id="start" href="#">Start</a>
							<a href="/projekat/admin/editQuiz?id=<%=quizList.get(i).getId()%>">Edit</a>
						</td>
					</tr>
				<%
				}
				%>
				</tbody>
			</table>
			<div class="pagination">
<!--   				<a href="#"s>&laquo;</a> -->
  				<%
  				int numOfPages = (int)Math.ceil((float)quizService.getNumberOfQuizzes()/ (float)5);
  				for(int i = 1; i <= numOfPages; ++i){
  					if(i == pageNum){
  				%>
  				<a href="#" class="active"><%=i%></a>
  				<%}else{ %>
  				<a name="<%=i%>" href="quizzes?page=<%=i %>"><%=i%></a>
  				<%}} %>
			</div>
		</div>
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