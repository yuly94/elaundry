<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$json = json_decode(file_get_contents('php://input'), true);

$app->get('/kurir/pemesanan/x'  ,'authKurir' , function() use ($app){
                       
            // check for required params
         //   BantuanModel::verifyRequiredParams(array('pemesanan_status'));
            
            // reading post params
            $pemesanan_status = "baru memesan";
            
            // fetching all user tasks
            $result= PemesananModel::mendapatkanPesanan($pemesanan_status);
            
            if ($result != NULL) { 
            //$response = array();
            $response["error"] = false;
            $response["kosong"] = false;
            $response["message"] = "daftar laundry berhasil didapatkan"; 
            $response["pemesanan"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["kosong"] = true;
        $response["message"] = "daftar laundry masih kosong";
       
        BantuanModel::echoRespnse(200, $response);
        }
    });

    
$app->post('/kurir/pemesanan/'  ,'authKurir' , function() use ($app){
                       
            // decoding the json array //bisa tp jadul
            //$post = json_decode(file_get_contents("php://input"), true);
            //$my_value = $post['pemesanan_status'];
    
            //http://stackoverflow.com/questions/39445006/read-json-input-in-slim-framework
            
            $bantuan = new BantuanModel();
            if($bantuan->isJSON($app->request->getBody())){
    
            //http://help.slimframework.com/discussions/problems/5918-cant-get-any-posted-json-data
            //http://stackoverflow.com/questions/28073480/how-to-access-a-json-request-body-of-a-post-request-in-slim 
            
            $json = $app->request->getBody();
            $data = json_decode($json); 
            
            // reading post params
            $pemesanan_status = $data->pemesanan_status;//['pemesanan_status'];//"baru memesan";
            
           
              } else {
                  
            // check for required params
            BantuanModel::verifyRequiredParams(array('pemesanan_status'));
            // reading post params
            $pemesanan_status = $app->request->post('pemesanan_status');
                  
             }
           
            // fetching all user tasks
            $result= PemesananModel::mendapatkanPesanan($pemesanan_status);
            
            if ($result != NULL) { 
            //$response = array();
            $response["error"] = false;
            $response["kosong"] = FALSE;
            $response["message"] = "daftar laundry berhasil didapatkan"; 
            $response["pemesanan"] = $result;
            // $response["body"] = $body['pemesanan_status'];
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["kosong"] = true;
        $response["message"] = "daftar laundry masih kosong";
       
        BantuanModel::echoRespnse(200, $response);
        }
    });




$app->post('/kurir/pemesanan/detail/:id/' ,'authKurir' , function($id) use ($app){
           

            //http://stackoverflow.com/questions/39445006/read-json-input-in-slim-framework
            
            $bantuan = new BantuanModel();
            if($bantuan->isJSON($app->request->getBody())){
    
            //http://help.slimframework.com/discussions/problems/5918-cant-get-any-posted-json-data
            //http://stackoverflow.com/questions/28073480/how-to-access-a-json-request-body-of-a-post-request-in-slim 
            
            $json = $app->request->getBody();
            $data = json_decode($json); 
            
            // reading post params
            $pemesanan_status = $data->pemesanan_status;//['pemesanan_status'];//"baru memesan";
            
           
              } else {
                  
            // check for required params
            BantuanModel::verifyRequiredParams(array('pemesanan_status'));
            // reading post params
            $pemesanan_status = $app->request->post('pemesanan_status');
                  
             }
            
            // fetching all user tasks
            $result= PemesananModel::mendapatkanDetailPesanan( $id, $pemesanan_status);
            
            if ($result != NULL) { 
            //$response = array();
            $response["error"] = false;
            $response["kosong"] = false;
            $response["message"] = "data pesanan berhasil di dapatkan"; 
            $response["pemesanan"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["kosong"] = true;
        $response["message"] = "tidak ada pesanan yang di ambil";
       
        BantuanModel::echoRespnse(200, $response);
        }
    });    
    
    
$app->post('/kurir/pemesanan/updatestatus/:id/' ,'authKurir' , function($id) use ($app){
           
            global $api_kurir_id;
            // check for required params
            BantuanModel::verifyRequiredParams(array('pemesanan_status'));
            
              // reading post params
            $pemesanan_status = $app->request->post('pemesanan_status');
            
            // fetching all user tasks
            $result= PemesananModel::updatePemesanan( $id, $api_kurir_id, $pemesanan_status);
            
            if ($result != NULL) { 
            //$response = array();
            $response["error"] = false;
            $response["message"] = "pemesanan berhasil di update"; 
            $response["pemesanan"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["kosong"] = true;
        $response["message"] = "pemesanan gagal di update";
       
        BantuanModel::echoRespnse(200, $response);
        }
    });    
        
    
    
$app->get('/kurir/pemesanan/pengambilan/'  ,'authKurir' , function() use ($app){
           
           // global $api_kurir_id;

            // fetching all user tasks
            $result= PemesananModel::pengambilanPesanan();
            
            
            if ($result != NULL) { 
            //$response = array();
            $response["error"] = false;
            $response["kosong"] = false;
            $response["message"] = "daftar laundry yang telah diambil berhasil didapatkan"; 
            $response["pemesanan"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["kosong"] = true;
        $response["message"] = "daftar laundry yang telah diambil masih kosong";
       
        BantuanModel::echoRespnse(200, $response);
        }
    });



$app->get('/kurir/pemesanan/pengambilan/detail/:id/' ,'authKurir' , function($id) use ($app){
           
            // fetching all user tasks
            $result= PemesananModel::pengambilanDetailPesanan( $id);
            
            if ($result != NULL) { 
            //$response = array();
            $response["error"] = false;
            $response["kosong"] = false;
            $response["message"] = "data laundry berhasil di dapatkan"; 
            $response["pemesanan"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["kosong"] = true;
        $response["message"] = "data laundry masih kosong";
        BantuanModel::echoRespnse(200, $response);
        }
    });    
    
    


