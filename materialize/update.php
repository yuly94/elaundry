<?php
	session_start();
	include_once("db.php");

	$id = $_POST['id'];
	$title = $_POST['title'];
	$content = $_POST['content'];

	$sql = "UPDATE posts SET title='$title', content='$content'
			WHERE id=$id";
	$res = mysqli_query($db,$sql);
	mysqli_close($db);
	header("Location:index.php");
?>