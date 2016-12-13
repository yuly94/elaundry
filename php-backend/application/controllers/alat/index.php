<?php


$app->get('/alat/', function () use ($app) {
    
  $app->render('alat/index.php');
});



$app->get('/alat/djikstra/', function () use ($app) {
    
  $app->render('alat/djikstra.php');
});
