<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$app->get('/kurir/transaksi/','authKurir' ,function() use ($app){
           
            global $api_kurir_id;

            // fetching all user tasks
            $result= TransaksiModel::getAllTransaksi($api_kurir_id);
            
            if ($result != NULL) { 
                
            $response["error"] = false;
            $response["message"] = "transaksi berhasil di dapatkan"; 
            $response["transaksi"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        BantuanModel::echoRespnse(404, $response);
        }
    });

