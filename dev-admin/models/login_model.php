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
	
		$uname = $_POST['username'];
		$upass = $_POST['password'];
		
		$stmt = $this->db->prepare("SELECT * FROM users WHERE users=:username OR password=:password LIMIT 1");
		$stmt->execute(array(':username'=>$uname, ':password'=>$upass));
		$userRow=$stmt->fetch(PDO::FETCH_ASSOC);
		
		//$data = $sth->fetchAll();
		//print_r($data);
	 
		if($stmt->rowCount() > 0){
			//login
			Session::init();
			Session::set('loggedIn',true);
			header('location: ../dashboard');
		} else {
			//show an error!
			header('location: ../login');
		}
	}
}