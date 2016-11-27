<?php


$app->get('/home/', function () use ($app) {
    
    BantuanModel::response(FALSE, array('version'=>'1.0'),'welcome home',200);
    
    $response["error"] = FALSE;
    $response["version"] = '1.0';
    //Helper::echoRespnse (200, $response);
});


