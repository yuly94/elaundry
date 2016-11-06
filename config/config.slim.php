<?php

$logger = new \Flynsarmy\SlimMonolog\Log\MonologWriter(array(
    'handlers' => array(
        new \Monolog\Handler\StreamHandler('../data/log/'.date('Y-m-d').'.log'),
    ),
));

return array(
    'debug' => true,
    'templates.path' => '../application/views/',
    'log.writer' => $logger
);
