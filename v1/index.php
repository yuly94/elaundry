<?php

require_once '.././src/v1/DbHandlerAuth.php';
require_once '.././src/v1/DbHandlerTasks.php';
require_once '.././src/v1/PassHash.php';


include('.././libs/phpmailer/mail.php');

//require '.././libs/Slim/Slim.php';
require '.././vendor/autoload.php';

\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();


require '.././src/v1/Route.php';


$app->run();
