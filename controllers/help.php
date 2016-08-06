<?php

class Help extends Controller{
	function __construct() {
		parent::__construct();
		//echo "we are in help </br>";
		//$this->view->render('help/index');
	}
	 
	function index() {
		$this->view->render('help/index');
	}
	
	public function other($arg = false){
		echo 'we are inside other </br>';
		echo 'Optional '.$arg.'</br>';
		require 'models/help_model.php';
		$model = new Help_Model();
		$this->view ->blah= $model->blah();
	}
}