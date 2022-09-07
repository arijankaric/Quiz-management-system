//console.log('adminQuizzes.js');



function tableData(){
	console.log('tableData');
	$('#tabledit').Tabledit({
		url: 'updateQuestion',
		eventType: 'dblclick',
		editButton: true,
		deleteButton: true,
		hideIdentifier: false,
		buttons: {
			edit: {
				class: 'btn btn-sm btn-warning',
				html: '<span class="glyphicon glyphicon-pencil"></span> Edit',
				action: 'edit'
			},
			delete: {
				class: 'btn btn-sm btn-danger',
				html: '<span class="glyphicon glyphicon-trash"></span> Trash',
				action: 'delete'
			},
			save: {
				class: 'btn btn-sm btn-success',
				html: 'Save'
			},
			restore: {
				class: 'btn btn-sm btn-warning',
				html: 'Restore',
				action: 'restore'
			},
			confirm: {
				class: 'btn btn-sm btn-default',
				html: 'Confirm'
			}
		},
		columns: {
			identifier: [0, 'id'],
			editable: [[1, 'question'], [2, 'timeToAnswer'], [3, 'score']]
		},
		onSuccess: function(data, textStatus, jqXHR){
			console.log('onSuccess(data, textStatus, jqXHR)');
			console.log(data);
			console.log(textStatus);
			console.log(jqXHR);
		},
		onFail: function(jqXHR, textStatus, errorThrown){
			console.log('onSuccess(jqXHR, textStatus, errorThrown)');
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
		},
		onAjax: function(action, serialize){
			console.log('onAjax(action, serialize)');
			console.log(action);
			console.log(serialize);
		}
	});
}

function viewData(){
	console.log('viewData()');
	$.ajax({
		url: '',
		method: 'GET'
	}).done(function(data){
		console.log('viewData done');
	}).always(function(data){
//		$('tbody').html(data);
		console.log('viewData finally');
		tableData();
	});
}

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