 function getVideo(){
		$.ajax({
			url:"/zadaca3/GetVideo",
			type:"POST",
			dataType:"json",
			data:{"data":"mojTitle"},
			success: function(data){
				console.log(data);
			}
		});
}