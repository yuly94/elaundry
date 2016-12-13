<?php

use \FB;
use \MyConn\Helper;

$app->get('/api-v1.0/', function () use ($app) {

    Helper::response(true, array('version'=>'1.0'));
});
