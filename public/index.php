<?php

require '../vendor/slim/slim/Slim/Slim.php';
\Slim\Slim::registerAutoloader();

//require '.././vendor/autoload.php';

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

$app->container->singleton('db', function () use ($app) {

    return \MyConn\PDOMySQLConnection::newInstance($app);
});

spl_autoload_register(function($className){

    if(substr($className, -5)=='Model' && file_exists('../application/models/'.$className.'.php')){

        require_once '../application/models/'.$className.'.php';
    }
});


$app->container->singleton('log', function () {
    $log = new \Monolog\Logger();
    $log->pushHandler(new \Monolog\Handler\StreamHandler('../data/log/log.txt'));
    return $log;
});




$app->run();
