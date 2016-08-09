<?php

class Login extends Controller {
	
	function __construct(){
		parent::__construct();
	//	echo 'we are in login controller';
		
	}
 
	function index() {
	//	require 'models/login_model.php'; // have creted autoload in controller
	//	$model = new Login_Model();  // have creted autoload in controller
	
		//echo Hash::create('sha256','test',HASH_PASSWORD_KEY);
            
                $this->view->cssVendors = array( 
                    'libs/angular-material/1.1.0/angular-material.css'
                ); 
                
                $this->view->cssVendorsPrefetch = array(

                    'libs/fonts/roboto-italic.css',
                    'libs/angular-material/1.1.0/docs.css'
                    
                    ); 
                
                $this->view->css = array('login/css/style.css');
            
            
                $this->view->jsVendors = array(
                    'libs/angularjs/1.5.5/angular.min.js',
                    'libs/angularjs/1.5.5/angular-animate.min.js',
                    'libs/angularjs/1.5.5/angular-route.min.js',
                    'libs/angularjs/1.5.5/angular-aria.min.js',
                    'libs/angularjs/1.5.5/angular-messages.min.js',
                    'libs/svg-assets-cache/svg-assets-cache.js',
                    'libs/angular-material/1.1.0/angular-material.js'
                    );
                
                
                $this->view->jsCdn= array(
                
                
                );
                

                $this->view->js = array('login/js/App.js','login/js/loginController.js');

                
		$this->view->title = 'Login';
		$this->view->label_username = "Username";
		$this->view->label_password = "Password";
		
		$this->view->render('login/index',TRUE,TRUE,TRUE,TRUE );
	}
	
	function run() {
		$this->model->run();
	}
}