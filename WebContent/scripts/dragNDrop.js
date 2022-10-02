const draggables = document.querySelectorAll('.draggable');
const containers = document.querySelectorAll('.container');
const table = document.getElementById('kontejner')

//function saveChanges(r){
//	const ordinalNumber = table.rows[r].cells[0].innerText
//	const title = table.rows[r].cells[1].innerText
//	const timeToAnswer = table.rows[r].cells[2].innerText
//	const score = table.rows[r].cells[3].innerText
//	const id = table.rows[r].cells[4].innerText
//	const question = {
//		"id": id,
//		"title": title,
//		"timeToAnswer": timeToAnswer,
//		"score": score,
//		"ordinalNumber": ordinalNumber
//	}
//	$.ajax({
//            type: "POST",
//            url: "./editQuestion",
//            data: question,
//            success: function(html){
//              console.log('success')
//            },
//            error: function(){
//				console.log('error')
//			}             
//          });
//}

//function makeEditable(r){
//		event.preventDefault() 
//		let edit = table.rows[r].cells[4]
////		console.log(edit.innerHTML)
////		table.rows[r].cells[0].contentEditable = 'true'
//		table.rows[r].cells[1].contentEditable = 'true'
//		table.rows[r].cells[2].contentEditable = 'true'
//		table.rows[r].cells[3].contentEditable = 'true'
//		edit.setAttribute('onclick', 'unMakeEditable(' + r + ');');
//}

//function unMakeEditable(r){
//		event.preventDefault() 
//		let edit = table.rows[r].cells[4]
////		table.rows[r].cells[0].contentEditable = 'false'
//		table.rows[r].cells[1].contentEditable = 'false'
//		table.rows[r].cells[2].contentEditable = 'false'
//		table.rows[r].cells[3].contentEditable = 'false'
//		edit.setAttribute('onclick', 'makeEditable(' + r + ');');
//		saveChanges(r)
//}

//$(document).on('click', '.edit', function() {
//             makeEditable
//});

//function assignEveryoneEditable(){
//	for (let r = 0, n = table.rows.length; r < n; r++) {
////		console.log(table.rows[r].cells[4])
//		table.rows[r].cells[4].setAttribute('onclick', 'makeEditable(' + r + ');');
////		table.rows[r].cells[4].addEventListener('click', function(e) {
////			makeEditable(r)
////		});
//	}
//}
//assignEveryoneEditable()

 class Question{
	constructor(id, title, score, time)
	{
		this.id = id;
		this.title = title;
		this.score = score;
		this.time = time;
	}
}

const question1 = new Question(1, "pitanje", 20, 30);
let questions = []

function updateTable(question){
	$.ajax({
            type: "POST",
            url: "./editQuestion",
            data: question,
            success: function(event){
				console.log('success:', event)
            },
            error: function(){
				console.log('error')
			}             
          });
}
 
 function getQuestions(){
	questions = []
	for (let r = 0, n = table.rows.length; r < n; r++) {
		const ordinalNumber = table.rows[r].cells[0].innerText
		const title = table.rows[r].cells[1].innerText
		const time = table.rows[r].cells[2].innerText
		const points = table.rows[r].cells[3].innerText
		const id = table.rows[r].cells[4].innerText
		const question = {
			"id": id,
			"content": title,
			"time": time,
			"score": points,
			"ordinalNumber": ordinalNumber
		}
		questions.push(question)
		updateTable(question)
	}
	console.log(questions)
}


 
 function updateTableEnumeration(){
		for (let r = 0, n = table.rows.length; r < n; r++) {
//			console.log(table.rows[r].cells[0].innerHTML)
//			console.log(table.rows[r].cells[0].innerText)
//			console.log("Before:")
//			console.log(table.rows[r].cells[0])
			table.rows[r].cells[0].innerText = (r+1)
//			table.rows[r].cells[0].innerHTML = "<td class=\"people\"> <div class=\"people-de\"><h5>"+ (r+1) +"</h5></div></td>"
//			console.log("After:")
//			console.log(table.rows[r].cells[0])
//			let newCell = row.insertCell(c+1);
		}
		getQuestions()
}
 
 draggables.forEach(draggable => {
	draggable.addEventListener('dragstart', () => {
		draggable.classList.add('dragging')
		console.log('drag start')
	})
	
	draggable.addEventListener('dragend', () => {
		draggable.classList.remove('dragging')
		console.log('drag end')
		console.log('table')
		updateTableEnumeration();
	})
	
	
})

containers.forEach(container => {
	container.addEventListener('dragover', e => {
		e.preventDefault(); // by default dropping inside of an elememnt is disabled
		const afterElement = getDragAfterElememnt(container, e.clientY)
		console.log("afterElement: " + afterElement)
		const draggable = document.querySelector('.dragging')
		if(afterElement == null)
		{
			container.appendChild(draggable)
		}
		else
		{
			container.insertBefore(draggable, afterElement)
		}
		console.log('drag over')
	})
})

function getDragAfterElememnt(container, y) {
	const draggableElements = [...container.querySelectorAll('.draggable:not(.dragging)')];
	
	return draggableElements.reduce((closest, child) => {
		const box = child.getBoundingClientRect()
		const offset = y - box.top - box.height / 2
		console.log(offset)
		if(offset < 0 && offset > closest.offset)
		{
			return {offset: offset, element: child}
		}
		else
		{
			return closest
		}
	}, {offset: Number.NEGATIVE_INFINITY}).element
}