function selectElement(id, valueToSelect) {
	let element = document.getElementById(id);
	element.value = valueToSelect;
}


$("#edit").submit(function(e) {
	e.preventDefault();

	const content = $('#edit-title').val();
	const correct = $('#edit-correct').is(":checked");
	const id = $('#edit-id').val();

	console.log('Edit Correct: ' + correct);

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: './editAnswer',
		data: {
			content: content,
			correct: correct,
			id: id
		}
	}).done(function(data) {
		if (data == 1) {
			location.reload();
		} else if (data == 0) {
			$("#label-username-edit").fadeIn();
			setTimeout(function() {
				$("#label-username-edit").fadeOut();
			}, 2000);
			document.getElementById("username").value = "";
		} else if (data == 2) {
			$("#label-password-edit").fadeIn();
			setTimeout(function() {
				$("#label-password-edit").fadeOut();
			}, 2000);
			document.getElementById("password").value = "";
			document.getElementById("repeatPassword").value = "";

		} else if (data == 3) {
			$("#label-edit-admin").fadeIn();
			setTimeout(function() {
				$("#label-edit-admin").fadeOut();
			}, 2000);
		} else if (data == 4) {
			$("#label-email-edit").fadeIn();
			setTimeout(function() {
				$("#label-email-edit").fadeOut();
			}, 2000);
		}

	});
});


$(".edit-btn").click(function(button) {
	const tr = $(this).closest('tr');
	const content = tr.find("td:eq(0)").children('div').children('h5').text();
	const correct = (tr.find("td:eq(1)").children('h5').text() === "true");
	const id = tr.find("td:eq(2)").text();

	document.getElementById("edit-title").value = content;
//	document.getElementById("edit-correct").value = !!correct;
	document.getElementById("edit-correct").checked = !!correct;
	document.getElementById("edit-id").value = id;
})