<?php
class Login_Model extends Model {
	function __construct() {
	//	echo "this login model";
	 
	//	echo md5('yuly');
		parent::__construct();
	}
	
	function run()
	{
		//$sth = $this->db->prepare("SELECT id FROM users  ");
	
		//$uname = $_POST['username'];
		//$upass = $_POST['password'];
		
		$sth = $this->db->prepare("SELECT * FROM users WHERE users=:username AND password=:password LIMIT 1");
		$sth->execute(array(
				':username'=>$_POST['username'], 
				':password'=>$_POST['password']
				
		));
		//$userRow=$stmt->fetch(PDO::FETCH_ASSOC);
		
		//$data = $sth->fetchAll();
		//print_r($data);
		
		$data = $sth->fetch();
		print_r($data);
		//die;
	 
		if($sth->rowCount() > 0){
			//login
			Session::init();
			Session::set('role', $data['role']);
			Session::set('users', $data['users']);
			Session::set('loggedIn',true);
			header('location: ../dashboard');
		} else {
			//show an error!
			header('location: ../login');
		}
	}
}