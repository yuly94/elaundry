<?php

class Error extends Controller {
	function __construct(){
		parent::__construct();
	//	echo "we are in error </br>";	
	}
	
	function index() {
            
                $lang = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
            
                if ($lang =="id"){
                   
                    $this->view->msg1 = "Halaman tidak di temukan :')";
                    $this->view->msg2 = 'halaman ini tidak ada';
                    $this->view->msg3 = 'Maaf anda tidak bisa menemukan halaman ini di luar angkasa, silahkan kembali';
                    $this->view->button = "Kembali &raquo;";
               
                } else {
                
                    $this->view->msg1 = "Page Not Found :')";
                    $this->view->msg2 = 'this page does not exist';
                    $this->view->msg3 = 'Sorry you cannot find the pages in space, please go home';
                    $this->view->button = "Go Home &raquo;";
                
                }
	
		$this->view->render('error/index',1);
	}
}