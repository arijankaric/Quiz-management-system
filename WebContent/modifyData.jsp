<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<title>Create Data</title>
<meta charset="UTF-8">
<meta name="viewport" content="with=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.4.1.min.js" type="text/javascript"></script>
</head>

<body>
	<form method="POST" action="admin/NewVideoServlet"
		enctype="multipart/form-data">
		<input type="file" name="file" id=""> <input type="submit"
			value="Upload">
	</form>

	<form method="POST" enctype="multipart/form-data" action="fup.cgi">
		File to upload: <input type="file" name="upfile"><br /> Notes
		about the file: <input type="text" name="note"><br /> <br /> <input
			type="submit" value="Press"> to upload the file!
	</form>

	<p>Click on the following button for calling the function:</p>
	<form>
		<input type="button" onclick="getVideo()" value="Message Print">
	</form>
	<p>

		<script type="text/javascript" src="getVideo.js"></script>
</body>

</html>