<?php
class View{
	function __construct() {
		echo "this is view </br>";
	}
	
	public function render($name) {
		require 'views/'.$name.'.php';
	}
}
