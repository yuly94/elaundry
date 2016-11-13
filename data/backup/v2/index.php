<?php

require '../vendor/SlimFramework/Slim/Slim.php';
\Slim\Slim::registerAutoloader();

require_once '../package/Package.php';
\Package\Package::registerAutoloader();

$app = new \Slim\Slim(array(
    'debug' => true,
    'templates.path' => '../application/view/'
));

require_once '../application/controller/index.php';
require_once '../application/controller/error.php';

$app->hook('slim.before.router', function () use ($app) {

    $uri = substr(preg_replace('/(\/+)/','/', $app->request->getResourceUri()), 1);

    $pieces = explode('/', $uri);

    $basePath = $path = realpath('../application/controller').'/';

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

$filesList = scandir('../config_2');

foreach($filesList as $fileName){

    if(substr($fileName, -11)=='.config.php'){

        $key = substr($fileName, 0, -11);
        $value = include '../config_2/'.$fileName;
        $app->config($key, $value);
    }
}

$app->container->singleton('db', function () use ($app) {

    return \My\PDOMySQLConnection::newInstance($app);
});

spl_autoload_register(function($className){

    if(substr($className, -5)=='Model' && file_exists('../application/model/'.$className.'.php')){

        require_once '../application/model/'.$className.'.php';
    }
});

$app->run();
