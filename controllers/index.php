<?php

class Index extends Controller {
	
	function __construct(){
		parent::__construct();
	//	echo 'we are in index controller';
		 
	}
        
        function index() {
        $this->view->render('index/index');
    }
    
}