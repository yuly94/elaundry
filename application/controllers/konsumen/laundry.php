<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

   
    /**
 * Listing all maps of particual user
 * method GET
 * url /tasks          
 */
$app->get('/paket', 'authenticate', function() {
           
            $paket_status = "1";
            $response = array();
            $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllPaket($paket_status);
    
            if ($result != NULL) {
            $items = array();
            $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["paket"]["id"] = $rez["paket_id"];
            $response["paket"]["nama"] = $rez["paket_nama"];
            $response["paket"]["harga"] = $rez["paket_harga"];
            $response["paket"]["keterangan"] = $rez["paket_keterangan"];
            $response["paket"]["status"] = $rez["paket_status"];
        
            
            $items[] = $response;
    }

        echoRespnse(200, $items);
    }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }
    });

