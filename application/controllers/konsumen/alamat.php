<?php
 
   
    /**
 * Listing all alamat of particual user
 * method GET
 * url /tasks          
 */
$app->get('/konsumen/alamat/', function() {
           
           // global $konsumen_id;
            $response = array();

            // fetching all user tasks
            $result = AlamatModel::getAlamat($konsumen_id);
  
            if ($result != NULL) {
            $items = array();
            $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["alamat"]["alamat_id"] = $rez["alamat_id"];
            $response["alamat"]["konsumen_id"] = $rez["konsumen_id"];
            $response["alamat"]["alamat_nama"] = $rez["alamat_nama"];
            $response["alamat"]["alamat_jalan"] = $rez["alamat_jalan"];
            $response["alamat"]["alamat_kota"] = $rez["alamat_kota"];
            $response["alamat"]["alamat_provinsi"] = $rez["alamat_provinsi"];
            $response["alamat"]["alamat_latitude"] = $rez["alamat_latitude"];
            $response["alamat"]["alamat_longitude"] = $rez["alamat_longitude"];
            $response["alamat"]["alamat_dibuat_pada"] = $rez["alamat_dibuat_pada"];
            $response["alamat"]["alamat_diupdate_pada"] = $rez["alamat_diupdate_pada"];
            
            $items[] = $response;
    }

        BantuanModel::echoRespnse(200, $items);
    }       
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        BantuanModel::echoRespnse(404, $response);
        }
    });

