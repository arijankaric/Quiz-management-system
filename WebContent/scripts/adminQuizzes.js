//function tableData(){
//	console.log('tableData');
//	$('#tabledit').Tabledit({
//		url: 'updateQuestion',
//		eventType: 'dblclick',
//		editButton: true,
//		deleteButton: true,
//		hideIdentifier: false,
//		buttons: {
//			edit: {
//				class: 'btn btn-sm btn-warning',
//				html: '<span class="glyphicon glyphicon-pencil"></span> Edit',
//				action: 'edit'
//			},
//			delete: {
//				class: 'btn btn-sm btn-danger',
//				html: '<span class="glyphicon glyphicon-trash"></span> Trash',
//				action: 'delete'
//			},
//			save: {
//				class: 'btn btn-sm btn-success',
//				html: 'Save'
//			},
//			restore: {
//				class: 'btn btn-sm btn-warning',
//				html: 'Restore',
//				action: 'restore'
//			},
//			confirm: {
//				class: 'btn btn-sm btn-default',
//				html: 'Confirm'
//			}
//		},
//		columns: {
//			identifier: [0, 'id'],
//			editable: [[1, 'question'], [2, 'timeToAnswer'], [3, 'score']]
//		},
//		onSuccess: function(data, textStatus, jqXHR){
//			console.log('onSuccess(data, textStatus, jqXHR)');
//			console.log(data);
//			console.log(textStatus);
//			console.log(jqXHR);
//		},
//		onFail: function(jqXHR, textStatus, errorThrown){
//			console.log('onSuccess(jqXHR, textStatus, errorThrown)');
//			console.log(jqXHR);
//			console.log(textStatus);
//			console.log(errorThrown);
//		},
//		onAjax: function(action, serialize){
//			console.log('onAjax(action, serialize)');
//			console.log(action);
//			console.log(serialize);
//		}
//	});
//}
//
//function viewData(){
//	console.log('viewData()');
//	$.ajax({
//		url: '',
//		method: 'GET'
//	}).done(function(data){
//		console.log('viewData done');
//	}).always(function(data){
////		$('tbody').html(data);
//		console.log('viewData finally');
//		tableData();
//	});
//}


function goToLocation(value) {
	location.href = './editQuiz?id=' + value;
}

function startQuiz(value) {
	location.href = './quiz?id=' + value;
}

function selectElement(id, valueToSelect) {
	let element = document.getElementById(id);
	element.value = valueToSelect;
}

function openDeleteModal(quizId) {
	document.getElementById("delete-quiz-btn").value = quizId;
	document.getElementById('delete-quiz').style.display = 'block';
}

function deleteQuiz(id) {

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './deleteQuiz',
		data: {
			id: id
		}
	}).done(function(data) {
		if (data === 1) {
			location.reload();
		}
		if (data === 3) {
			$("#label-delete").fadeIn();
			setTimeout(function() {
				$("#label-delete").fadeOut();
			}, 2000);
		}
	});

}

$("#edit").submit(function(e) {
	e.preventDefault();

	console.log("edit submit");
	const id = $('#edit-id').val();
	console.log("id: ", id);
	const title = $('#edit-title').val();
	console.log("title: ", title);
	const description = $('#edit-description').val();
	console.log("description: ", description);

	let data = new FormData();
	data.append('imageFile', $('#edit-image-file')[0].files[0]);
	data.append('title', title);
	data.append('description', description);
	data.append('id', id);
	console.log("data:", data);

	$.ajax({
		url: './editQuiz',
		data: data,
		type: 'POST',
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		cache: false,
		success: function(data) {
			console.log(data);
			if (data == 1) {
				location.reload();
			} else {

			}
		}
	});
});

$("#add").submit(function(e) {
	e.preventDefault();

	const title = $('#add-title').val();
	console.log(title)
	const description = $('#add-description').val();
	console.log(description)

	let data = new FormData();
	data.append('imageFile', $('#add-image-file')[0].files[0]);
	data.append('title', title);
	data.append('description', description);
	data.append('username', currentUsername);

	$.ajax({
		url: './createQuiz',
		data: data,
		type: 'POST',
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		cache: false,
		success: function(data) {
			console.log(data);
			console.log(typeof data);
			if (data == 1) {
				console.log('data == 1');
				location.reload();
			}

			if (data === 1) {
				console.log('data === 1');
			}
		}
	});
});

$(".edit-btn").click(function(button) {
	const tr = $(this).closest('tr');
	const title = tr.find("td:eq(0)").children("div").children("h5").text();
	const description = tr.find("td:eq(2)").children("p").text();
	// const imageUrl = tr.find("td:eq(2)").find('img').attr('src');
	const username = tr.find("td:eq(1)").children("h5").text();

	console.log("username: ", username);
	console.log("currentUsername: ", currentUsername);
	
	if (username !== currentUsername && currentRole !== 1) {
		document.getElementById("edit-quiz").style.display = "none";
		return;
	}

	const quizId = tr.find("td:eq(4)").text();

	console.log('quizId: ' + quizId);

	document.getElementById("edit-title").value = title;
	document.getElementById("edit-description").value = description;
	document.getElementById("edit-id").value = quizId;

})

//function goToPage(page){
////	let nameOfFunction = this[event.target.name];
//
//	$.ajax({
//		url: 'admin/quizzes',
//		method: 'GET',
//		data: page,
//        dataType: 'json',
//	}).done(function(data){
//		console.log('viewData done');
//	}).always(function(data){
////		$('tbody').html(data);
//		console.log('viewData finally');
//		tableData();
//	});
//
//}