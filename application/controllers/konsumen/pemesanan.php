<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$app->post('/konsumen/pemesanan/menambahkan/' ,'authKonsumen' ,function() use ($app){
    
            global $api_konsumen_id;
            global $api_konsumen_email;
            global $api_konsumen_nama;

            // check for required params
            BantuanModel::verifyRequiredParams(array('pemesanan_latitude', 'pemesanan_longitude',
                'pemesanan_alamat','pemesanan_paket', 'pemesanan_catatan',
                'pemesanan_baju','pemesanan_celana', 'pemesanan_rok'));
            
            $response = array();

            // reading post params
            $pemesanan_latitude = $app->request->post('pemesanan_latitude');
            $pemesanan_longitude = $app->request->post('pemesanan_longitude');
            $pemesanan_alamat = $app->request->post('pemesanan_alamat');
            $pemesanan_paket = $app->request->post('pemesanan_paket');
            $pemesanan_catatan = $app->request->post('pemesanan_catatan');
            $pemesanan_baju = $app->request->post('pemesanan_baju');
            $pemesanan_celana = $app->request->post('pemesanan_celana');
            $pemesanan_rok = $app->request->post('pemesanan_rok');
            
            $result = PemesananModel::menambahkanPesanan($api_konsumen_id, $api_konsumen_email,$api_konsumen_nama,
                    $pemesanan_latitude, $pemesanan_longitude, 
                    $pemesanan_alamat, $pemesanan_paket, $pemesanan_catatan,
                    $pemesanan_baju, $pemesanan_celana, $pemesanan_rok);

                // get the user by email
                

            if ($result ==0) {

                $response["error"] = false;
                $response["message"] = "berhasil melakukan pemesanan";
                $response["pemesanan"] = PemesananModel::pemesananById($api_konsumen_id);
                
                $kirim_email = new PemesananModel();
                $kirim_email->kirimNotif($$api_konsumen_email, $api_konsumen_nama);
                
               
            } else if ($result == 1) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registering";
                
            } else if ($result == 2) {
                $response["error"] = true;
                $response["message"] = "Sorry, this email already existed";
            } 
            // echo json response
            BantuanModel::echoRespnse(201, $response);           
            
        });

        
        
$app->post('/konsumen/pemesanan/menambahkanx/' ,'authKonsumen' ,function() use ($app){
    
            global $api_konsumen_id;
           

            // check for required params
            BantuanModel::verifyRequiredParams(array('pemesanan_latitude', 'pemesanan_longitude',
                'pemesanan_alamat','pemesanan_paket', 'pemesanan_catatan',
                'pemesanan_baju','pemesanan_celana', 'pemesanan_rok'));
            
        $response = array();
        $response["error"] = false;
        $response["message"] = $api_konsumen_id;
        BantuanModel::echoRespnse(200, $response);
        }
    );

$app->get('/konsumen/pemesananx/','authKonsumen' ,function() use ($app){
           
            global $api_konsumen_id;

            // fetching all user tasks
            $result= PemesananModel::getPesanan($api_konsumen_id);
            
            if ($result != NULL) { 
                
            $response["error"] = false;
            $response["message"] = "alamat berhasil di dapatkan"; 
            $response["pesanan"] = $result;
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

