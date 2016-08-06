<?php
class Dashboard_Model extends Model {
	function __construct() {
		parent::__construct();
	//	echo "this dashboard model";
	}
	
/* 	function xhrInsert(){
		$text =  $_POST['text'];
		
		$sth = $this->db->prepare('INSERT INTO data (text) VALUES (:text)');
		$sth->execute(array(':text' => $text));
		
		$data = array('text' => $text, 'id' => $this->db->lastInsertId());
		echo json_encode($data);
		
	}
	
	function xhrGetListings() {
		$sth = $this->db->prepare('SELECT * FROM data');
		$sth->setFetchMode(PDO::FETCH_ASSOC);
		$sth->execute();
		$data = $sth->fetchAll();
		echo json_encode($data);
	}
	
	function xhrDeleteListing()
	{
		$id = $_POST['id'];
		$sth = $this->db->prepare('DELETE FROM data WHERE id = "'.$id.'"');
		$sth->execute();
	} */
	
	public function xhrInsert()
	{
		$text = $_POST['text'];
	
		$this->db->insert('data', array('text' => $text));
	
		$data = array('text' => $text, 'id' => $this->db->lastInsertId());
		echo json_encode($data);
	}
	
	public function xhrGetListings()
	{
		$result = $this->db->select("SELECT * FROM data");
		echo json_encode($result);
	}
	
	public function xhrDeleteListing()
	{
		$id = (int) $_POST['id'];
		$this->db->delete('data', "id = '$id'");
	}
	
}