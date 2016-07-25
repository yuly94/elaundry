<?php
session_start();
include_once("db.php");
if(isset($_GET['del'])){
	$id=$_GET['del'];
	$query = "DELETE from posts WHERE ID = $id" or die("Non!").mysql_error();
	$res = mysqli_query($db,$query) or die("Failed".mysql_error());
	echo "Successful";
}
	header("Location: index.php");
?>
