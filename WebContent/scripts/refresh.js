 function refresh(){
		$.ajax({
			url:"/zadaca3/VoteServlet",
			type:"POST",
			dataType:"json",
			data:{"data":"refresh"},
			success: function(data){
				$("#titleA").html(data[0].videoName);
				$("#titleB").html(data[1].videoName);
	            $("#videoIDA").attr('src','https://www.youtube.com/embed/' + data[0].videoId);
	            $("#videoIDB").attr('src','https://www.youtube.com/embed/' + data[1].videoId);
			}
		});
}