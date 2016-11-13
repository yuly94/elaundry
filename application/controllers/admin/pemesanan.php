<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$app->get('/konsumen/pemesanan/','authKonsumen' ,function() use ($app){
           
            global $api_konsumen_id;

            // fetching all user tasks
            $result= PemesananModel::getPesanan($api_konsumen_id);
            
            if ($result != NULL) { 
                
            $response["error"] = false;
            $response["message"] = "alamat berhasil di dapatkan"; 
            $response["pasanan"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["message"] = "The requested resource doesn't exists";
        BantuanModel::echoRespnse(404, $response);
        }
    });

$app->post('/konsumen/pemesanan/tambah/','authKonsumen' ,function() use ($app){
           
            global $api_konsumen_id;

            // check for required params
            BantuanModel::verifyRequiredParams(array( 
                 `paket_id`, `pesanan_satuan`,
                `koordinat_id`, `catatan`));
            
            $response = array();

            // reading post params
            $paket_id = $app->request->post('paket_id');
            $pesanan_satuan = $app->request->post('pesanan_satuan');
            $koordinat_id = $app->request->post('koordinat_id');
            $catatan = $app->request->post('catatan');
            
            // fetching all user tasks
            $result= PemesananModel::tambahPesanan($api_konsumen_id, $paket_id,$pesanan_satuan,$koordinat_id,$catatan);
            
            if ($result != NULL) { 
                
            $response["error"] = false;
            $response["message"] = "pemesanan berhasil dilakukan"; 
            $response["pasanan"] = $result;
            BantuanModel::echoRespnse(200, $response);
}      
    else {
        $response["error"] = true;
        $response["message"] = "pemesanan gagal, silahkan coba kembali";
        BantuanModel::echoRespnse(404, $response);
        }
    });

