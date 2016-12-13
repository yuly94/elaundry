<?php
 
   
    /**
 * Listing all alamat of particual user
 * method GET
 * url /tasks          
 */

//$auth = AuthentikatorModel::authKurir();

$app->get('/kurir/alamat/','authKurir' ,function() use ($app){
           
            global $api_kurir_id;

            // fetching all user tasks
            $result= AlamatModel::getAlamat($api_kurir_id);
            
            if ($result != NULL) { 
                
            $response["error"] = false;
            $response["message"] = "alamat berhasil di dapatkan"; 
            $response["alamat"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        BantuanModel::echoRespnse(404, $response);
        }
    });

