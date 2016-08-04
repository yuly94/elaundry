<?php
class View{
	function __construct() {
	//	echo "this is view </br>";
	}
	
// 	public function render($name) {
// 		require 'views/'.$name.'.php';
// 	}

    

    public function render($name, $noInclude = false)
    {
        if ($noInclude == true) {
            require 'views/' . $name . '.php';    
        }
        else {
            require 'views/header.php';
            require 'views/' . $name . '.php';
            require 'views/footer.php';    
        }
    }

}