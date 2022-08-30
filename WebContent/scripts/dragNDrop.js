/**
 * 
 */
 
 const draggables = document.querySelectorAll('.draggable');
 const containers = document.querySelectorAll('.container');
 
 draggables.forEach(draggable => {
	draggable.addEventListener('dragstart', () => {
		draggable.classList.add('dragging')
		console.log('drag start')
	})
	
	draggable.addEventListener('dragend', () => {
		draggable.classList.remove('dragging')
		console.log('drag end')
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