<?php 

require_once 'class.user.php';
 
$nama=trim($_POST['nama']);
$jenis=trim($_POST['jenis']);
$suplier=trim($_POST['suplier']);
$modal=trim($_POST['modal']);
$harga=trim($_POST['harga']);
$jumlah=trim($_POST['jumlah']);
$sisa=trim($_POST['jumlah']);

//mysql_query("insert into barang values('','$nama','$jenis','$suplier','$modal','$harga','$jumlah','$sisa')");

 
		try
		{							
			$stmt = $this->conn->prepare("INSERT INTO barang(nama,jenis,suplier,modal,harga,jumlah,jumlah) 
			                                             VALUES(:nama, :jenis, :suplier, :modal, :harga, :jumlah, :jumlah)");
			$stmt->bindparam(":nama",$nama);
			$stmt->bindparam(":jenis",$jenis);
			$stmt->bindparam(":suprier",$suplier);
			$stmt->bindparam(":modal",$modal);
			$stmt->bindparam(":harga",$harga);
			$stmt->bindparam(":jumlah",$jumlah);
			$stmt->bindparam(":jumlah",$sisa);
			$stmt->execute();	
			
		}
		catch(PDOException $ex)
		{
			echo $ex->getMessage();
		}
 
 header("location:barang.php"); 



 ?>