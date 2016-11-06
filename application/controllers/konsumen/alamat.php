<?php
 
   
    /**
 * Listing all alamat of particual user
 * method GET
 * url /tasks          
 */

//$auth = AuthentikatorModel::authKonsumen();

$app->get('/konsumen/alamat/','authKonsumen' ,function() use ($app){
           
            global $api_konsumen_id;
            $response = array();

            // fetching all user tasks
            $result = AlamatModel::getAlamat($api_konsumen_id);
  
            if ($result != NULL) {
            $items = array();
            $response["error"] = false;
            foreach ($result as $rez) {
            $response = $rez;
 
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

