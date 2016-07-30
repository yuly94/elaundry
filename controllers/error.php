<?php

class Error extends Controller {
	function __construct(){
		parent::__construct();
	//	echo "we are in error </br>";	
	}
	
	function index() {
		$this->view->msg1 = "Page Not Found :')";
		$this->view->msg2 = 'this page does not exist';
		$this->view->msg3 = 'Sorry you cannot find the pages in space, please go home';
		$this->view->button = "Go Home &raquo;";
		
		$this->view->render('error/index',true);
	}
}