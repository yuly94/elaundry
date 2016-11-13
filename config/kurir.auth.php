<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function authKurir(\Slim\Route $route) {
    
    $app = \Slim\Slim::getInstance();
    // Getting request headers
    $headers = apache_request_headers();
    $response = array();
    
    // Verifying Authorization Header
    if (isset($headers['Authorization'])) {

        // get the api key
        $api_key = $headers['Authorization'];
        // validating api key
        if (!KurirModel::isValidApiKey($api_key)) {
            
            // api key is not present in users table
            $response["error"] = true;
            $response["message"] = "Access Denied. Invalid Api key";
            
            BantuanModel::echoRespnse(401, $response);   
            
            $app->stop();
            
        } else {

            global $api_kurir_no;
            global $api_kurir_id;
	    global $api_kurir_email;

            // get user primary key id
            $kurir = KurirModel::getKurirByApi($api_key);

            $api_kurir_no = $kurir["kurir_no"];
            $api_kurir_id = $kurir["kurir_id"];
            $api_kurir_email = $kurir["kurir_email"];

                }
                
	} else {
        // api key is missing in header
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        BantuanModel::echoRespnse(401, $response);
        $app->stop();
       
    }
}
