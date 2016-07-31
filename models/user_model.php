<?php
class User_Model extends Model {
	function __construct() {
		parent::__construct();
	//	echo "this dashboard model";
	}
	
	public function userList() {
/* 		$sth = $this->db->prepare('SELECT id, user, role FROM users');
		$sth->execute();
		return $sth->fetchAll(); */
		
		return $this->db->select('SELECT id, user, role FROM users');
	}
	
	public function userSingleList($id) {
		/* $sth = $this->db->prepare('SELECT id, user, role FROM users WHERE id =:id');
		$sth->execute(array(
				':id'=> $id
		));
		return $sth->fetch(); */
		
		return $this->db->selectSingle("SELECT id, user, role FROM users WHERE id = :id", array(':id' => $id));
	 	//return  $this->db->select("SELECT id, user, role FROM users WHERE id = :id", array('id' => $id));
	 	
		
		 
		
	}
	
		
	public function create($data)
	{	
 
	 	$this->db->insert('users', array(
				'user' => $data['username'],
				'password' => Hash::create('sha1', $data['password'], HASH_PASSWORD_KEY),
				'role' => $data['role']
				
		));
		/*
		$sth = $this->db->prepare('INSERT INTO users
				(users, password, role)
				VALUES (:users,:password,:role)
				');
		$sth->execute(array(
				':users'=>$data['username'],
				':password'=> Hash::create('sha1', $data['password'], HASH_PASSWORD_KEY),
				':role'=>$data['role']
	
		));
			 */
	}
	
	public function editSave($data)
	{
/* 		$this->db->update('users',array(
				'users'=>$data['username'],
				'password'=> Hash::create('sha1',$data['password'],HASH_PASSWORD_KEY),
				'role'=>$data['role'] 
				
		
		),"'id'={$data['id']}"); */
		
		
		$postData = array(
			'user' => $data['username'],
			'password' => Hash::create('sha256', $data['password'], HASH_PASSWORD_KEY),
			'role' => $data['role']
		);
		
		$this->db->update('users', $postData, "`id` = {$data['id']}");
		
/* 		$postData = array(
				'users'=>$data['username'],
				'password'=> Hash::create('sha1',$data['password'],HASH_PASSWORD_KEY),
				'role'=>$data['role']	
				);
		
			$this->db->update('users',$postData,"'id' = {$data['id']}"); */
		
/* 		$sth = $this->db->prepare('UPDATE users
				SET users  = :username, 
					password  = :password,
					 role  = :role
				WHERE id = :id
				');
		
		$sth->execute(array(
				':id'=>$data['id'],
				':username'=>$data['username'],
				':password'=>$data['password'],
				':role'=>$data['role']
	
		)); */
			
	}
	
	
	public function delete($id) {
		
		$result = $this->db->select("SELECT role FROM users WHERE id = :id", array(':id' => $id));
		
		if ($result[0]['role'] == 'owner')
			return false;
		
			$this->db->delete('users', "id = '$id'");
		
/* 		$sth = $this->db->prepare('SELECT role FROM users WHERE id = :id');
		$sth->execute(array(
				':id' => $id
		));
		$data = $sth->fetch();
		if ($data['role']=='owner') {
			return false;
		}
		print_r($data);
		
		
		$sth = $this->db->prepare('DELETE FROM users WHERE id = :id');
		$sth->execute(array(
				':id' => $id
		)); */
	}
	
	
	
}






