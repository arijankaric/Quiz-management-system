function openDeleteModal(username) {
	document.getElementById("delete-user-btn").value = username;
	document.getElementById('delete-user').style.display = 'block';
}

function deleteUser(id) {
	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './deleteUser',
		data: {
			id: id
		}
	}).done(function(data) {
		if (data == 1) {
			location.reload();
		}
		if (data == 3) {
			$("#label-delete").fadeIn();
			setTimeout(function() {
				$("#label-delete").fadeOut();
			}, 2000);
		}
	});
}

$("#edit").submit(function(e) {
	e.preventDefault();

	const username = $('#edit-username').val();
	const password = $('#edit-password').val();
	const repeatPassword = $('#edit-repeat-password').val();
	const oldId = $('#old-id').val();
	const role = $('#edit-role').val();
	console.log('oldId: ' + oldId)
	console.log('role: ' + role)

	if (password !== repeatPassword) {
		console.log('diff pw')
		$("#label-password-edit").fadeIn();
		setTimeout(function() {
			$("#label-password-edit").fadeOut();
		}, 2000);
		document.getElementById("password").value = "";
		document.getElementById("repeat-password").value = "";
		return;
	}

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './updateUser',
		data: {
			username: username,
			password: password,
			id: oldId,
			role: role
		}
	}).done(function(data) {
		if (data == 1) {
			location.reload();
		}
	});
  });

$("#add").submit(function(e) {
	e.preventDefault();
	const username = $('#add-username').val();
	const password = $('#add-password').val();
	const repeatPassword = $('#add-repeat-password').val();
	const role = $('#add-role').val();
	console.log('username', username)
	console.log('role', role)
	console.log('password', password)
	console.log('repeatPassword', repeatPassword)

	if (password !== repeatPassword) {
		$("#label-password-add").fadeIn();
		setTimeout(function() {
			$("#label-password-add").fadeOut();
		}, 2000);
		return;
	}

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './createUser',
		data: {
			username: username,
			password: password,
			repeatPassword: repeatPassword,
			role: role
		}
	}).done(function(data) {
		if (data == 1) {
			location.reload();
		} else {
			console.log('data:', data)
		}
	});
});

$(".edit-btn").click(function(button) {
	const tr = $(this).closest('tr');
	const username = tr.find("td:eq(0)").children('div').children('h5').text();
	let role = tr.find("td:eq(1)").children('p').text();
	const id = tr.find("td:eq(2)").text();

	if (role === 'Admin') {
		role = 2;
	} else if (role === 'Super-admin') {
		role = 1;
	} else if (role === 'Pending') {
		role = 3;
	} else {
		console.log('Error in role: ' + role)
	}

	console.log('role:', role);
	console.log('id:', id);
	console.log('username:', username);
	document.getElementById("edit-username").value = username;
	document.getElementById("old-username").value = username;
	document.getElementById("edit-password").value = "";
	document.getElementById("edit-role").value = role;
	document.getElementById("old-id").value = id;
})

//const table = document.getElementById('kontejner')
//let oldUserName
//
//function saveChanges(r){
//	let	name = table.rows[r].cells[0].innerText
//	let role = table.rows[r].cells[1].innerText
//	let pass = table.rows[r].cells[2].innerText
//	let user = {
//		"oldUserName": oldUserName,
//		"name": name,
//		"role": role,
//		"pass": pass
//	}
//	$.ajax({
//            type: "POST",
//            url: "updateUser",
//            data:JSON.stringify(user),
//            success: function(html){
//              console.log('success')
//            },
//            error: function(){
//				console.log('error')
//			}
//          });
//}
//
//function makeEditable(r){
//		event.preventDefault()
//		let edit = table.rows[r].cells[3]
////		console.log(edit.innerHTML)
//		oldUserName = table.rows[r].cells[0].innerText
//		console.log(oldUserName)
//		table.rows[r].cells[0].contentEditable = 'true'
//		table.rows[r].cells[1].contentEditable = 'true'
//		table.rows[r].cells[2].contentEditable = 'true'
////		table.rows[r].cells[2].contentEditable = 'true'
////		table.rows[r].cells[3].contentEditable = 'true'
//		edit.setAttribute('onclick', 'unMakeEditable(' + r + ');');
//}
//
//function unMakeEditable(r){
//		event.preventDefault()
//		let edit = table.rows[r].cells[3]
//		table.rows[r].cells[0].contentEditable = 'false'
//		table.rows[r].cells[1].contentEditable = 'false'
//		table.rows[r].cells[2].contentEditable = 'false'
////		table.rows[r].cells[2].contentEditable = 'false'
////		table.rows[r].cells[3].contentEditable = 'false'
//		edit.setAttribute('onclick', 'makeEditable(' + r + ');');
//		saveChanges(r)
//}
//
////$(document).on('click', '.edit', function() {
////             makeEditable
////});
//
//function assignEveryoneEditable(){
//	for (let r = 0, n = table.rows.length; r < n; r++) {
////		console.log(table.rows[r].cells[4])
//		table.rows[r].cells[3].setAttribute('onclick', 'makeEditable(' + r + ');');
////		table.rows[r].cells[4].addEventListener('click', function(e) {
////			makeEditable(r)
////		});
//	}
//}
//assignEveryoneEditable()