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

    $cek_konsumen = ValidasiModel::cekKonsumen($konsumen_email);

    if ( $cek_konsumen ) {
        $konsumen_nama = $cek_konsumen["konsumen_nama"];
        $result =  PasswordModel::resetPassword($konsumen_email,$konsumen_kode_reset,$konsumen_password, $konsumen_nama);

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
 