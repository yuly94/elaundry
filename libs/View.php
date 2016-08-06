<?php
class View{
	function __construct() {
	//	echo "this is view </br>";
	}
	
// 	public function render($name) {
// 		require 'views/'.$name.'.php';
// 	}

    

    public function render($name, $noInclude = 1)
    {
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
    }

}