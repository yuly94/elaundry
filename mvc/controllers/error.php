<?php

class Error extends Controller {
	function __construct(){
		parent::__construct();
		echo "we are in error </br>";
		$this->view->msg = 'this page does not exist';
		$this->view->render('error/index');
	}
}