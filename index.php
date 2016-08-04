<?php

//require the files

require 'config/paths.php';
require 'config/database.php';
require 'config/constants.php';
require 'util/Auth.php';

//Also spl_autoload_register
function __autoload($class){
	require LIBS.$class.'.php';
}

 
/* require 'libs/Bootstrap.php';
require 'libs/Controller.php';
require 'libs/Model.php';
require 'libs/View.php';

//library
require 'libs/Database.php';
require 'libs/Session.php';
require 'libs/Hash.php';
 */

$bootstrap = new Bootstrap();

		  
 // Optional Path Settings		
 //$bootstrap->setControllerPath();		
 //$bootstrap->setModelPath();		
 //$bootstrap->setDefaultFile();		
 //$bootstrap->setErrorFile();		
 		
 $bootstrap->init();