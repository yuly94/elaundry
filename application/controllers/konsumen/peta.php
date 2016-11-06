<?php
 

/**
 * Listing all maps of particual user
 * method GET
 * url /tasks          
 */
$app->get('/maps', 'authenticate', function() {
           
            $maps_status = "1";
            $response = array();
            $dbt = new DbHandlerTasks();
            // fetching all user tasks
            $result = $dbt->getAllMaps($maps_status);
  
  
            if ($result != NULL) {
            $items = array();
            $response["error"] = false;
            foreach ($result as $rez) {
            
            $response["maps"]["maps_id"] = $rez["maps_id"];
            $response["maps"]["name"] = $rez["name"];
            $response["maps"]["address"] = $rez["address"];
            $response["maps"]["city"] = $rez["city"];
            $response["maps"]["province"] = $rez["province"];
            $response["maps"]["latitude"] = $rez["latitude"];
            $response["maps"]["longitude"] = $rez["longitude"];
            
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

