<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function authKonsumen(\Slim\Route $route) {
    
    $app = \Slim\Slim::getInstance();
    // Getting request headers
    $headers = apache_request_headers();
    $response = array();
    
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

            global $api_konsumen_no;
            global $api_konsumen_id;
	    global $api_konsumen_email;
            global $api_konsumen_nama;

            // get user primary key id
            $konsumen = KonsumenModel::getKonsumenByApi($api_key);

            $api_konsumen_no = $konsumen["konsumen_no"];
            $api_konsumen_id = $konsumen["konsumen_id"];
            $api_konsumen_email = $konsumen["konsumen_email"];
            $api_konsumen_nama = $konsumen["konsumen_nama"];

                }
                
	} else {
        // api key is missing in header
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        BantuanModel::echoRespnse(401, $response);
        $app->stop();
       
    }
}
