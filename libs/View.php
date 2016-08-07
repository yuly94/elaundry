<?php
class View{
	function __construct() {
	//	echo "this is view </br>";
	}
	
// 	public function render($name) {
// 		require 'views/'.$name.'.php';
// 	}

    

    public function render($viewName, 
            $withHeader = true,
            $withHeaderCss = true,
            $withFooterJs = true, 
            $withFooter = true )
    { /*
        if ($noInclude == 1) {
            require 'views/' . $name . '.php';    
        }
        else  if ($noInclude == 2) {
            require 'views/header.php';
            require 'views/' . $name . '.php';
        }
         else  if ($noInclude == 3) {
            require 'views/header.php';
            require 'views/' . $name . '.php';
            require 'views/footer.php';    
        }
     
     */
        if ($withHeader == TRUE) {
            require 'views/header.php';    
        }
        
        if ($withHeaderCss == TRUE) {
            require 'views/headerCss.php';      
        }
         if ($viewName != false) {
            require 'views/' . $viewName . '.php';       
        }
        
        if ($withFooterJs == TRUE) {
            require 'views/footerJs.php';         
        }
        
        if ($withFooter == TRUE) {
            require 'views/footer.php';     
        }
    }

}