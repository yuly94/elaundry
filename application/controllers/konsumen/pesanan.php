<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    
    /**
 * Listing all pesanan of particual user
 * method GET
 * url /tasks          
 */
$app->get('/pesanan', 'authenticate', function() {
           
            global $konsumen_id; $response = array();  $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllPesanan($konsumen_id);
  
  
            if ($result != NULL) { $items = array(); $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["pesanan"]["pesanan_id"] = $rez["pesanan_id"];
            $response["pesanan"]["konsumen_id"] = $rez["konsumen_id"];
            $response["pesanan"]["kurir_pengambil_id"] = $rez["kurir_pengambil_id"];
            $response["pesanan"]["kurir_pengantar_id"] = $rez["kurir_pengantar_id"];
            $response["pesanan"]["lokasi_id"] = $rez["lokasi_id"];
            $response["pesanan"]["paket_id"] = $rez["paket_id"];
            $response["pesanan"]["tanggal_pemesanan"] = $rez["tanggal_pemesanan"];
            $response["pesanan"]["tanggal_pengantaran"] = $rez["tanggal_pengantaran"];
            $response["pesanan"]["status_cucian"] = $rez["status_cucian"];
            $response["pesanan"]["status_pengantaran"] = $rez["status_pengantaran"];
            
            $items[] = $response;    }

        echoRespnse(200, $items);  }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        echoRespnse(404, $response);
        }    });

    
    
 