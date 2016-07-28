<?php
class Controller{
	function __construct() {
		echo "main controller </br>";
		$this->view = new View();
	}
}