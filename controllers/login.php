<?php

class Login extends Controller {
	
	function __construct(){
		parent::__construct();
	//	echo 'we are in login controller';
		
	}
 
	function index() {
	//	require 'models/login_model.php'; // have creted autoload in controller
	//	$model = new Login_Model();  // have creted autoload in controller
	
		echo Hash::create('sha256','test',HASH_PASSWORD_KEY);
		
		$this->view->label_username = "Username";
		$this->view->label_password = "Password";
		
		$this->view->render('login/index',false);
	}
	
	function run() {
		$this->model->run();
	}
}