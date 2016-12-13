<?php

$app->post('/konsumen/password/lupa/', function () use ($app) {

        // check for required params
        BantuanModel::verifyRequiredParams(array('konsumen_email'));

        // reading post params
        $konsumen_email = $app->request()->post('konsumen_email');

        // validating email address
        ValidasiModel::validasiEmail($konsumen_email);
        $response = array();

        $cek_konsumen = ValidasiModel::cekKonsumen($konsumen_email);
        if ($cek_konsumen == "0") {   //2

        $response["error"] = "true";
        $response["message"] = "Email tidak ditemukan";
        BantuanModel::echoRespnse(200, $response);


         }  //3
            else {
        $konsumen_id = $cek_konsumen["konsumen_id"];
        $konsumen_nama = $cek_konsumen["konsumen_nama"];
        PasswordModel::ResetRequest($konsumen_email,$konsumen_id,$konsumen_nama);

        }
    }); //1



$app->post('/konsumen/password/reset/', function () use ($app) {

    // check for required params
    BantuanModel::verifyRequiredParams(array('konsumen_email','konsumen_kode_reset','konsumen_password'));

    // reading post params
    $konsumen_email = $app->request()->post('konsumen_email');
    $konsumen_kode_reset = $app->request()->post('konsumen_kode_reset');
    $konsumen_password = $app->request()->post('konsumen_password');

    $response = array();

    // validating email address
    ValidasiModel::validasiEmail($konsumen_email);

    if ( ValidasiModel::cekKonsumen($konsumen_email) ) {
        $result =  PasswordModel::meresetPassword($konsumen_email,$konsumen_kode_reset,$konsumen_password);

        if($result==1){

        $response["error"] = "true";
        $response["message"] = "Reset Password Failure, please try again";
        BantuanModel::echoRespnse(200, $response);

        }  else if($result==2){

        $response["error"] = "true";
        $response["message"] = "Reset Password Failure, Your reset code expired, please resent your reset";
        BantuanModel::echoRespnse(200, $response);

        }  else  if($result==3){

        $response["error"] = "true";
        $response["message"] = "Reset Password Failure, Your token incorrect";
        BantuanModel::echoRespnse(200, $response);

        }
        else {

        $response["error"] = "false";
        $response["message"] = "Password Changed Successfully";
        BantuanModel::echoRespnse(200, $response);

        }


      } else {

        $response["error"] = "true";
        $response["message"] = "Email does not exist";
        BantuanModel::echoRespnse(200, $response);

          }

    }); //1
 
      
    $app->post('/konsumen/password/ganti/','authKonsumen' ,function() use ($app){
        
            // check for required params
            BantuanModel::verifyRequiredParams(array('konsumen_password_lama', 'konsumen_password_baru'));

            // reading post params
            $konsumen_password_lama = $app->request()->post('konsumen_password_lama');
            // reading post params
            $konsumen_password_baru = $app->request()->post('konsumen_password_baru');
           
            global $api_konsumen_id;
            
            $konsumen = KonsumenModel::cekPasswordById($api_konsumen_id, $konsumen_password_lama);
            if($konsumen){
                
                // fetching all user tasks
                if (PasswordModel::menggantiPassword($api_konsumen_id, $konsumen_password_baru)) { 

                $response["error"] = FALSE;
                $response["message"] = "password anda berhasil diganti"; 
                $response["konsumen"] = KonsumenModel::konsumenById($api_konsumen_id);
                
                BantuanModel::echoRespnse(200, $response);

                $kirim_email = new KirimEmailModel();
                $kirim_email->gantiPassword($konsumen["konsumen_email"],$konsumen["konsumen_nama"]);
                    
                    }      
                    else {
                        $response["error"] = true;
                        $response["message"] = "password anda belum berhasil diganti, silahkan coba lagi";
                        BantuanModel::echoRespnse(404, $response);
                        }
                
            } else {
                
                    $response["error"] = true;
                    $response["message"] = "password lama anda salah"; 
                    BantuanModel::echoRespnse(200, $response);
                
            }
            

    });
