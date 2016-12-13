<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

   
 /**
 * Listing all paket of particual user
 * method GET
 * url /tasks          
 */

$app->get('/kurir/laundry/paket/','authKurir' ,function() use ($app){
            
            $paket_status = "1";

            // fetching all user tasks
            $result= LaundryModel::getAllPaket($paket_status);
            
            if ($result != NULL) { 
                
            $response["error"] = false;
            $response["message"] = "paket berhasil di dapatkan"; 
            $response["paket"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        BantuanModel::echoRespnse(404, $response);
        }
    });

