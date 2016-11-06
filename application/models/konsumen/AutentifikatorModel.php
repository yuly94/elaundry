<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function authenticate(\Slim\Route $route) {
    // Getting request headers
    $headers = apache_request_headers();
    $response = array();
    $app = \Slim\Slim::getInstance();

    // Verifying Authorization Header
    if (isset($headers['Authorization'])) {

        // get the api key
        $api_key = $headers['Authorization'];
        // validating api key
        if (!KonsumenModel::isValidApiKey($api_key)) {
            // api key is not present in users table
            $response["error"] = true;
            $response["message"] = "Access Denied. Invalid Api key";
            BantuanModel::echoRespnse(401, $response);
            
            $app->stop();
            
        } else {

            // get user primary key id
            $api_email = KonsumenModel::getKonsumenEmail($api_key);
            // get user primary key id
            $konsumen_id = KonsumenModel::getKonsumenUnikId($api_key);
            // get user primary key id
            $id = KonsumenModel::getKonsumenId($api_key);
            
            global $id;
            global $konsumen_id;
	    global $api_email;

                }
                
	} else {
        // api key is missing in header
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        BantuanModel::echoRespnse(401, $response);
        $app->stop();
    }
}