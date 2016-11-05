<?php

 

$app->get('/', function () use ($app) {

    $app->render('index.php');
});

$app->get('/error/', function () use ($app) {

    $app->render('error.php');
});


$app->get('/api-test/test/(:secret(/))', function () use ($app) {

    $args = func_get_args();

    $app->view()->set('secret', empty($args[0])?'':$args[0]);

    $app->render('test.php');
});
