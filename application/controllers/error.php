<?php

use \FB;
use \My\Helper;

$app->notFound(function () use ($app) {
    $app->render('error/404.php');
});

