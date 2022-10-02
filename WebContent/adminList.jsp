<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="javax.servlet.http.HttpServletRequest, Model.*, service.*, java.util.*, dao.*, javax.servlet.http.HttpServletRequest, java.lang.Math"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<link rel="stylesheet" href="../css/all.min.css" />
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="../css/adminList.css">
<script src="../scripts/jquery-3.6.1.min.js"></script>
<!-- <script src="../scripts/popper.min.js"></script> -->
<script src="../scripts/bootstrap.min.js"></script>
<script src="../scripts/adminList.js" defer></script>
</head>
<body>

	<%
	UserService userService = new UserService(new UserDao());
	List<User> users = userService.getAllUsers();
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

		<h3 class="i-name">Dashboard</h3>

		<div class="values">
			<div class="val-box">
				<i class="fas fa-users"></i>
				<div>
					<h3 id="Admins"><%=users.size()%></h3>
					<span>Users</span>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="edit-user">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Edit user</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body mbody" id="edit-user-body">
						<form method="POST" id="edit">
							<input placeholder="Username" class="form-control mb-3"
								name="edit-username" id="edit-username" required /> <input
								style="display: none;" id="old-username" name="old-username" />
							<input style="display: none;" id="old-id" name="old-id" /> <input
								placeholder="Password" class="form-control mb-3" type="password"
								name="edit-password" id="edit-password" />
							<input placeholder="Repeat password" class="form-control mb-3"
								type="password" name="edit-repeat-password"
								id="edit-repeat-password"/> <select
								class="form-select" aria-label="Default select example"
								id="edit-role" required>
								<option value="1">Super-admin</option>
								<option value="2">Admin</option>
								<option value="3">Pending</option>
							</select>
							<div>
								<label
									class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
									style="display: none;" id="label-password-edit">Passwords
									do not match!</label>
							</div>
							<div>
								<label
									class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
									style="display: none;" id="label-username-edit">That
									username already exists!</label>
							</div>
							<div style="margin-top: 20px; text-align: right">
								<button type="submit" class="btn d btn-default pull-right"
									id="edit-user-submit">Save</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="modal" tabindex="-1" role="dialog" id="add-user">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Add user</h4>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close"
							onclick='document.getElementById("add-user").style.display = "none"'>
							<span aria-hidden="true">&times;</span>
						</button>
					</div>

					<div class="modal-body mbody" id="add-user-body">
						<form method="POST" id="add">
							<div class="form-group">
								<label for="add-username">Username</label> <input
									placeholder="Username" class="form-control mb-3"
									name="add-username" id="add-username" required />
							</div>
							<div class="form-group">
								<label for="add-password">Password</label><input type="password"
									placeholder="Password" class="form-control mb-3"
									name="add-password" id="add-password" required />
							</div>
							<div class="form-group">
								<label for="add-repeat-password">Repeat Password</label> <input
									type="password" placeholder="Repeat Password"
									class="form-control mb-3" name="add-repeat-password"
									id="add-repeat-password" required />
							</div>
							<div class="form-group">
								<select class="form-select" aria-label="Default select example"
									id="add-role" required>
									<option value="1">Super-admin</option>
									<option value="2">Admin</option>
									<option value="3">Pending</option>
								</select>
							</div>

							<div style="margin-top: 20px; text-align: right">
								<button type="submit" class="btn submit btn-default pull-right"
									id="add-user-submit">Save</button>
							</div>
							<div>
								<label
									class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded"
									style="display: none;" id="label-password-add">Passwords
									must match</label>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal" tabindex="-1" role="dialog" id="delete-user">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Delete user</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick=" document.getElementById('delete-user').style.display = 'none'">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Do you want to delete this user?</p>
        <div>
          <label class="bg-light border border-danger text-danger text-center w-100 p-2 mt-5 rounded" style="display: none;" id="label-delete">You cannot delete this user!</label></div>

      </div>

      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="delete-user-btn" onclick="deleteUser(this.value)">Yes</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick= "document.getElementById('delete-user').style.display = 'none'">No</button>
      </div>
    </div>
  </div>
</div>

		<div class="board">
			<table width="100%">
				<thead>
					<tr>
						<td>Name</td>
						<td>Role</td>
						<!-- 							<td>Password</td> -->
						<td class="edit"
							onclick="document.getElementById('add-user').style.display = 'block'">Create</td>
					</tr>
				</thead>
				<tbody id="kontejner">
					<%
					for (Integer i = 0; i < users.size(); ++i) {
					%>
					<tr>
						<td class="people">
							<div class="people-de">
								<h5><%=users.get(i).getUsername()%></h5>
							</div>
						</td>

						<td class="role">
							<p><%=users.get(i).getRoleString()%></p>
						</td>

						<!-- 							<td class="role"> -->
						<!-- 								<p></p> -->
						<!-- 							</td> -->
						<td class="hidden"><%=users.get(i).getId()%></td>

						<td class="edit">
							<%
							String username = (String) session.getAttribute("username");
							User user = userService.findByUsername(username);
							if (user.getRole() == 1) {
							%>
							<button tabindex="1" aria-label="edit user button"
								class="btn bg-transparent edit-btn" data-toggle="modal"
								data-target="#edit-user">
								<i class="fa fa-edit"></i>

							</button>
							<button tabindex="1" aria-label="delete user button"
								class="btn bg-transparent" value=<%=users.get(i).getId()%>
								onclick="openDeleteModal(this.value)">
								<i class="fa fa-trash"> </i>
							</button> <%
 }
 %>
						
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
		</div>
	</section>
	<script>
		
	</script>

</body>
</html>