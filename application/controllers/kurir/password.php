<?php

$app->post('/kurir/password/lupa/', function () use ($app) {

        // check for required params
        BantuanModel::verifyRequiredParams(array('kurir_email'));

        // reading post params
        $kurir_email = $app->request()->post('kurir_email');

        // validating email address
        ValidasiModel::validasiEmail($kurir_email);
        $response = array();

        $cek_kurir = ValidasiModel::cekKurir($kurir_email);
        if ($cek_kurir == "0") {   //2

        $response["error"] = "true";
        $response["message"] = "Email tidak ditemukan";
        BantuanModel::echoRespnse(200, $response);


         }  //3
            else {
        $kurir_id = $cek_kurir["kurir_id"];
        $kurir_nama = $cek_kurir["kurir_nama"];
        PasswordModel::ResetRequest($kurir_email,$kurir_id,$kurir_nama);

        }
    }); //1



$app->post('/kurir/password/reset/', function () use ($app) {

    // check for required params
    BantuanModel::verifyRequiredParams(array('kurir_email','kurir_kode_reset','kurir_password'));

    // reading post params
    $kurir_email = $app->request()->post('kurir_email');
    $kurir_kode_reset = $app->request()->post('kurir_kode_reset');
    $kurir_password = $app->request()->post('kurir_password');

    $response = array();

    // validating email address
    ValidasiModel::validasiEmail($kurir_email);

    if ( ValidasiModel::cekKurir($kurir_email) ) {
        $result =  PasswordModel::meresetPassword($kurir_email,$kurir_kode_reset,$kurir_password);

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
 
      
    $app->post('/kurir/password/ganti/','authKurir' ,function() use ($app){
        
            // check for required params
            BantuanModel::verifyRequiredParams(array('kurir_password_lama', 'kurir_password_baru'));

            // reading post params
            $kurir_password_lama = $app->request()->post('kurir_password_lama');
            // reading post params
            $kurir_password_baru = $app->request()->post('kurir_password_baru');
           
            global $api_kurir_id;
            $kurir = KurirModel::cekPasswordById($api_kurir_id, $kurir_password_lama);
            if($kurir){
                
                // fetching all user tasks
                if (PasswordModel::menggantiPassword($api_kurir_id, $kurir_password_baru)) { 

                $response["error"] = false;
                $response["message"] = "password anda berhasil diganti"; 
                BantuanModel::echoRespnse(200, $response);

                $kirim_email = new KirimEmailModel();
                $kirim_email->gantiPassword($kurir["kurir_email"],$kurir["kurir_nama"]);
                    
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
