<?php
 

/**
 * Listing all maps of particual user
 * method GET
 * url /tasks          
 */ 

$app->get('/konsumen/peta/', function () use ($app) {
           
            global $api_konsumen_id;
                $maps_status = "1";

            // fetching all user tasks
            $result= PetaModel::getAllPeta($maps_status);
 
            if ($result != NULL) { 
                
            $response["error"] = false;
            $response["message"] = "peta berhasil di dapatkan"; 
            $response["peta"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        BantuanModel::echoRespnse(404, $response);
        }
    });

    
     
$app->get('/konsumen/peta/map_url_list', function () use ($app) {

    $app->render('/konsumen/linkpeta.php');
});
