//function resizeInput() {
//    $(this).attr('size', $(this).val().length);
//}
//
//$('input[type="text"]')
//    // event handler
//    .keyup(resizeInput)
//    // resize on page load
//    .each(resizeInput);

function goToLocation(value) {
	location.href = './editQuestion?id=' + value;
}

function selectElement(id, valueToSelect) {
	let element = document.getElementById(id);
	element.value = valueToSelect;
}

function openDeleteModal(questionId) {
	document.getElementById("delete-question-btn").value = questionId;
	document.getElementById('delete-question').style.display = 'block';
}

function deleteQuestion(id) {

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './deleteQuestion',
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

	const id = $('#edit-id').val();
	const content = $('#edit-title').val();
	const score = $('#edit-score').val();
	const time = $('#edit-time').val();
	
	if (time < 15) {
		$("#label-time-edit-lower").fadeIn();
		setTimeout(function() {
			$("#label-time-edit-lower").fadeOut();
		}, 2000);
		document.getElementById("edit-time").value = "";
		return
	} else if (time > 60) {
		$("#label-time-edit-higher").fadeIn();
		setTimeout(function() {
			$("#label-time-edit-higher").fadeOut();
		}, 2000);
		document.getElementById("edit-time").value = "";
		return
	}

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './editQuestion',
		data: {
			id: id,
			content: content,
			score: score,
			time: time
		}
	}).done(function(data) {
		if (data === 1) {
			location.reload();
		} else if (data === 0) {
			$("#label-username-edit").fadeIn();
			setTimeout(function() {
				$("#label-username-edit").fadeOut();
			}, 2000);
			document.getElementById("username").value = "";
		} else if (data === 2) {
			$("#label-password-edit").fadeIn();
			setTimeout(function() {
				$("#label-password-edit").fadeOut();
			}, 2000);
			document.getElementById("password").value = "";
			document.getElementById("repeatPassword").value = "";

		} else if (data === 3) {
			$("#label-edit-admin").fadeIn();
			setTimeout(function() {
				$("#label-edit-admin").fadeOut();
			}, 2000);
		} else if (data === 4) {
			$("#label-email-edit").fadeIn();
			setTimeout(function() {
				$("#label-email-edit").fadeOut();
			}, 2000);
		}

	});
});


$("#add").submit(function(e) {
	e.preventDefault();

	const content = $('#add-title').val();
	const score = $('#add-score').val();
	const time = $('#add-time').val();
	// const quizId = $('#quizId').val();

	if (time < 15) {
		$("#label-time-add-lower").fadeIn();
		setTimeout(function() {
			$("#label-time-add-lower").fadeOut();
		}, 2000);
		document.getElementById("edit-time").value = "";
		return
	} else if (time > 60) {
		$("#label-time-add-higher").fadeIn();
		setTimeout(function() {
			$("#label-time-add-higher").fadeOut();
		}, 2000);
		document.getElementById("add-time").value = "";
		return
	}

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './createQuestion',
		data: {
			content: content,
			score: score,
			time: time,
			quizId: quizId
		}
	}).done(function(data) {
		if (data === 1) {
			location.reload();
		} else if (data === 0) {
			$("#label-username-add").fadeIn();
			setTimeout(function() {
				$("#label-username-add").fadeOut();
			}, 2000);
			document.getElementById("username").value = "";
		} else if (data === 2) {
			$("#label-password-add").fadeIn();
			setTimeout(function() {
				$("#label-password-add").fadeOut();
			}, 2000);
			document.getElementById("password").value = "";
			document.getElementById("repeat-password").value = "";

		}
	});
});


$(".edit-btn").click(function(button) {
	const tr = $(this).closest('tr');
	const content = tr.find("td:eq(1)").children('h5').text();
	const score = tr.find("td:eq(3)").children('h5').text();
	const time = tr.find("td:eq(2)").children('h5').text();
	const ordinalNumber = tr.find("td:eq(0)").children('div').children('h5').text();
	const id = tr.find("td:eq(4)").text();

	console.log('questionId:', id);


	document.getElementById("edit-title").value = content;
	document.getElementById("edit-score").value = score;
	document.getElementById("edit-time").value = time;
	document.getElementById("edit-id").value = id;

})

