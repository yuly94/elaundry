<?php
/**
 * Controler
 *      My_COntroller extend controller
 *          Dashboaard extend my_controller
 */


class Dashboard extends Controller {
	
	function __construct(){
		parent::__construct();
		//echo 'we are in login dashboard';
                Auth::handleLogin();
		
		//print_r($_SESSION);
		
		$this->view->js = array('dashboard/js/default.js');
		
	}
 
	function index() {
	//	require 'models/login_model.php'; // have creted autoload in controller
	//	$model = new Login_Model();  // have creted autoload in controller
	
		$this->view->render('dashboard/index');
	}
	
	function logout() {
		Session::destroy();
		header('location: '.URL.'login');
		exit;
	}

	function xhrInsert()
	{
		$this->model->xhrInsert();
	}
	
	function xhrGetListings()
	{
		$this->model->xhrGetListings();
	}
	
	function xhrDeleteListing()
	{
		$this->model->xhrDeleteListing();
	}
 
}