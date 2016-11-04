<?php

//require '../vendor/slim/slim/Slim/Slim.php';
//\Slim\Slim::registerAutoloader();

require '../vendor/autoload.php';

require_once '../package/Package.php';
\Package\Package::registerAutoloader();

$app = new \Slim\Slim(array(
    'debug' => true,
    'templates.path' => '../application/views/'
));

require_once '../application/controllers/index.php';
require_once '../application/controllers/error.php';

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

$app->hook('slim.before', function () use ($app) {
    $app->view()->appendData(array('baseUrl' => '/base/url/here'));
});

$app->container->singleton('db', function () use ($app) {

    return \My\PDOMySQLConnection::newInstance($app);
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
    }
     
});

//
//$app->container->singleton('log', function () {
//   // $log = new \Monolog\Logger();
//   // $log->pushHandler(new \Monolog\Handler\StreamHandler('../data/log/log.txt'));
//    
//    $log = new \Flynsarmy\SlimMonolog\Log\MonologWriter(array(
//    'handlers' => array(
//        new \Monolog\Handler\StreamHandler('../data/log/'.date('Y-m-d').'.log'),
//    ),
//));
//
//    
//    return $log;
//});

// Get request object
$req = $app->request;

//Get root URI
$rootUri = $req->getRootUri();

//Get resource URI
$resourceUri = $req->getResourceUri();
$uri = substr(preg_replace('/(\/+)/','/', $app->request->getResourceUri()), 1);//

    



echo 'ini 1 '. $resourceUri;
echo 'ini 2 '. $uri;


    $ltrims = ltrim($uri, '\\');
    //ltrim($className, '\\');
    $sub = substr($uri, 1);
    $expl = explode("/", $resourceUri);
    print_r (explode("/",$resourceUri));
     print_r  (str_replace(' ', '\\', $resourceUri)) ;

echo 'ini 3 '. $ltrims;
echo 'ini 4 '. $sub;
echo 'ini 5 '. $expl[1];

$app->run();
