<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"/>
<!--     <script defer src="https://use.fontawesome.com/releases/v5.15.4/js/all.js"></script> -->
    <link rel="stylesheet" href="../css/adminIndex.css">
    <script src="../scripts/adminIndex.js" defer></script>
</head>
<body>

	<%
		
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
					<i class="far fa-search"></i>
					<input type="text" placeholder="Search">
				</div>
			</div>
			
			<div class="profile">
				<i class="far fa-bell"></i>
				<img alt="" src="../imgs/1.jpg">
			</div>
		</div>
		
		<h3 class="i-name">
			Dashboard
		</h3>
			
			<div class="values">
				<div class="val-box">
					<i class="fas fa-users"></i>
					<div>
						<h3 id="activeUsers">100</h3>
						<span>Active Users</span>
					</div>
				</div>
<!-- 				<div class="val-box"> -->
<!-- 					<i class="fas fa-trophy"></i> -->
<!-- 					<div> -->
<!-- 						<h3>200,521</h3> -->
<!-- 						<span>Live Quizes</span> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="val-box"> -->
<!-- 					<i class="fas fa-acorn"></i> -->
<!-- 					<div> -->
<!-- 						<h3>215,542</h3> -->
<!-- 						<span>Products Sold</span> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="val-box"> -->
<!-- 					<i class="fas fa-dollar-sign"></i> -->
<!-- 					<div> -->
<!-- 						<h3>$677.89</h3> -->
<!-- 						<span>This Month</span> -->
<!-- 					</div> -->
<!-- 				</div> -->
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
					<tr>
						<td class="people">
							<img alt="" src="../imgs/1.jpg">
							<div class="people-de">
								<h5>Javascript</h5>
								<p>3495</p>
							</div>
						</td>
						
						<td class="people-des">
							<h5>Arijan Karic</h5>
							<p>arijankaric@yahoo.com</p>
						</td>
						
						<td class="active">
							<p>Active</p>
						</td>
						
						<td class="role">
							<p>30</p>
						</td>
						
						<td class="edit">
							<a href="#">Edit</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</section>

    
</body>
</html>