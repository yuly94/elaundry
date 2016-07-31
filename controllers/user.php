<?php

class User extends Controller {
	
	public function __construct(){
		parent::__construct();
		//echo 'we are in login dashboard';
		Session::init();
		$logged = Session::get('loggedIn');
		$role = Session::get('role');
		if ($logged==false || $role !='owner') {
			Session::destroy();
			header('location: login');
			exit;
		}
		

	}
	
	public function index() {
		//	require 'models/login_model.php'; // have creted autoload in controller
		//	$model = new Login_Model();  // have creted autoload in controller
	
		
		
		$this->view->label_username = "Username";
		$this->view->label_password = "Password";
		$this->view->label_role = "Role";
	
	
		$this->view->userList = $this->model->userList();
		$this->view->render('user/index');
	}
	
	public function create(){
		$data = array();
		$data['username'] = $_POST['username'];
		$data['password'] = $_POST['password'];
		$data['role'] = $_POST['role'];
		
		// @TODO : Do your error checking
		
		$this->model->create($data);
		
		//echo $data['username'];
		header('location: '.URL.'user');
	}
	 
	public function edit($id) {
		// fetch individual user
		$this->view->user = $this->model->userSingleList($id);
		$this->view->render('user/edit');
	}
	
	public function editSave($id) {
		
		$data = array();
		$data['id'] = $id;
		$data['username'] = $_POST['username'];
		$data['password'] = $_POST['password'];
		$data['role'] = $_POST['role'];
		
		// @TODO : Do your error checking
		
		$this->model->editSave($data);
		
		header ('location: ' .URL. 'user');
	}
	
	public function delete($id) {
		$this->model->delete($id);
		header ('location: ' .URL. 'user');
	}
	 
}