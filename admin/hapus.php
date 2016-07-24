<?php 
include 'dbconfig.php';
//$id=$_GET['id'];
//mysql_query("delete from barang where id='$id'");
try {
	$id = trim($_GET['id']);

	$stmt = $user_home->runQuery("DELETE FROM barang WHERE id=:id");
	$stmt->execute(array(":id"=>$id));
 
  }
catch(PDOException $e)
    {
    echo $sql . "<br>" . $e->getMessage();
    }
header("location:barang.php");

?>