/**
 * 
 */
 
const draggables = document.querySelectorAll('.draggable');
const containers = document.querySelectorAll('.container');
const table = document.getElementById('kontejner')

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

function updateTable(){
	$.ajax({
            type: "POST",
            url: "updateQuestions",
            data:JSON.stringify(questions),
            success: function(html){
              console.log('success')
            },
            error: function(){
				console.log('error')
			}             
          });
}
 
 function getQuestions(){
	questions = []
	for (let r = 0, n = table.rows.length; r < n; r++) {
		let id = table.rows[r].cells[0].innerText
		let question = table.rows[r].cells[1].innerText
		let time = table.rows[r].cells[2].innerText
		let points = table.rows[r].cells[3].innerText
		questions.push({
			"id": id,
			"title": question,
			"timeToAnswer": time,
			"score": points 
		})
	}
	console.log(questions)
	updateTable()
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