<?php

use \FB;
use \My\Helper;

$app->get('/login/', function () use ($app) {
    
    Helper::response(FALSE, array('version'=>'1.0'),'test login',200);
    
    $response["error"] = FALSE;
    $response["version"] = '1.0';
    //Helper::echoRespnse (200, $response);
});
