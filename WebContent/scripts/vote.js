 function vote(video){
	   var video1 = document.getElementById("titleA").innerText
	   var video2 = document.getElementById("titleB").innerText
		$.ajax({
			url:"/zadaca3/VoteServlet",
			type:"POST",
			dataType:"json",
			data:{"data":"vote", "title1":video1, "title2":video2, "voted":video},
			success: function(data){
				$("#titleA").html(data[0].videoName);
				$("#titleB").html(data[1].videoName);
	            $("#videoIDA").attr('src','https://www.youtube-nocookie.com/embed/' + data[0].videoId);
	            $("#videoIDB").attr('src','https://www.youtube-nocookie.com/embed/' + data[1].videoId);
			}
		});
}