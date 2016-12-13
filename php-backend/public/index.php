<?php

require '../vendor/autoload.php'; #composer autoloader

//require '../vendor/slim/slim/Slim/Slim.php'; # replace with composer autoloader
//\Slim\Slim::registerAutoloader(); # replace with composer autoloader
 

//require_once '../package/Package.php'; # replace with composer autoloader
//\Package\Package::registerAutoloader(); # replace with composer autoloader

 
$appconfig = require_once '../config/config.slim.php' ;
$app = new \Slim\Slim($appconfig);


//require_once '../application/controllers/index.php'; # Move to config folder
//require_once '../application/controllers/error.php'; # Move to config folder

$app->hook('slim.before', function () use ($app) {
    $app->view()->appendData(array('baseUrl' => BASE_URL));
});


$app->hook('slim.before.router', function () use ($app) {

    $uri = substr(preg_replace('/(\/+)/','/', $app->request->getResourceUri()), 1);

    $pieces = explode('/', $uri);

    $basePath = $path = realpath('../application/controllers').'/';

    foreach($pieces as $value){

        $value = trim($value);

        if($value!=='' && file_exists($path.$value) && is_dir($path.$value)){

            $path = $path.$value.'/';

            if(file_exists($path.'index.php') && is_file($path.'index.php')
                    && strpos(realpath($path.'index.php'), $basePath)===0){

                require_once realpath($path.'index.php');
            }
        }
        else{

            break;
        }
    }

    if(file_exists($path.$value.'.php') && is_file($path.$value.'.php')
            && strpos(realpath($path.$value.'.php'), $basePath)===0){

        require_once realpath($path.$value.'.php');
    }
});

$filesList = scandir('../config');

foreach($filesList as $fileName){

    if(substr($fileName, -11)=='.config.php'){

        $key = substr($fileName, 0, -11);
        $value = include '../config/'.$fileName;
        $app->config($key, $value);
    }
}

    // Get request object
    $req = $app->request;
    //Get resource URI
    $resourceUri = $req->getResourceUri();
    $expl = explode("/", $resourceUri);

    if(file_exists('../config/'.$expl[1].'.auth.php')){
    require_once '../config/'.$expl[1].'.auth.php';
    
    }  


$app->container->singleton('db', function () use ($app) {

    return PDOMySQLConnectionModel::newInstance($app);
});

spl_autoload_register(function($className) use ($app) {

    if(substr($className, -5)=='Model' && file_exists('../application/models/'.$className.'.php')){

        require_once '../application/models/'.$className.'.php';
        
    }  else {
        
        // Get request object
        $req = $app->request;

        //Get resource URI
        $resourceUri = $req->getResourceUri();
        $expl = explode("/", $resourceUri);
           
        if(substr($className, -5)=='Model' && file_exists('../application/models/'.$expl[1].'/'.$className.'.php')){
        require_once '../application/models/'.$expl[1].'/'.$className.'.php';
    }  
     else {
        
        if(substr($className, -5)=='Model' && file_exists('../application/models/utils/'.$className.'.php')){

        require_once '../application/models/utils/'.$className.'.php';
        
        } 
        }
    }
     
});
 
 
$app->run();
