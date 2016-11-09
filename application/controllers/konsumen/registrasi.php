<?php

$app->post('/konsumen/registrasi/', function () use ($app) {

            // check for required params
            BantuanModel::verifyRequiredParams(array('konsumen_nama', 'konsumen_alamat','konsumen_nohp','konsumen_email', 'konsumen_password'));
            
            $response = array();

            // reading post params
            $konsumen_nama = $app->request->post('konsumen_nama');
            $konsumen_alamat = $app->request->post('konsumen_alamat');
            $konsumen_nohp = $app->request->post('konsumen_nohp');
            $konsumen_email = $app->request->post('konsumen_email');
            $konsumen_password = $app->request->post('konsumen_password');

            // validating email address
            ValidasiModel::validasiEmail($konsumen_email);
            
            // validating email address
            ValidasiModel::validasiNama($konsumen_nama);
            
            // validating email address
            ValidasiModel::validasiPassword($konsumen_password);

            $result = RegistrasiModel::membuatUser($konsumen_nama, $konsumen_alamat, $konsumen_nohp, $konsumen_email, $konsumen_password);

            if ($result == USER_CREATED_SUCCESSFULLY) {

                $result = KonsumenModel::konsumenByEmail($konsumen_email);
                $response["error"] = false;
                $response["message"] = "pendaftaran account berhasil";
                $response["registrasi"] = $result;  
               
            } else if ($result == USER_CREATE_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registering";
                
            } else if ($result == USER_ALREADY_EXISTED) {
                $response["error"] = true;
                $response["message"] = "Sorry, this email already existed";
            } 
            // echo json response
            BantuanModel::echoRespnse(201, $response);           
            
        });



$app->get('/konsumen/registrasi/aktifasi/:nama/(:token(/))', function () use ($app) {

    $args = func_get_args();

    $app->view()->set('nama', empty($args[0])?'':$args[0]);
    $app->view()->set('token', empty($args[1])?'':$args[1]);

    $app->render('konsumen/aktifasi.php');
});

$app->get('/konsumen/registrasi/aktifasi/:nama/:token/(aktifkan(/))', function () use ($app) {

    $response = array();

    $args = func_get_args();

    $kode_aktifasi = (empty($args[1])?'':$args[1]);
        $app->view()->set('nama', empty($args[0])?'':$args[0]);

    $result = RegistrasiModel::aktifkanUser( $kode_aktifasi);

    if ($result) {

        $konsumen = RegistrasiModel::getUserByToken($kode_aktifasi);

            // konsumen di temukan mengirim data konsumen
            $response["error"] = FALSE;
            $response["message"] = "Selamat account anda berhasil di aktifkan";
            $response["konsumen"] = $konsumen;  
            
            $kirim_email = new KirimEmailModel();  
            $kirim_email->emailAktifasiSukses($konsumen["konsumen_email"], $konsumen["konsumen_nama"]);

            $app->redirect('sukses');

        } else {
            $response["error"] = true;
            $response["message"] = "Sorry, failed to activate, please check your token";
        } 
        // echo json response
        BantuanModel::echoRespnse(201, $response);
        
});


$app->get('/konsumen/registrasi/aktifasi/:nama/:token/aktifkan/(sukses(/))', function () use ($app) {

    $args = func_get_args();
    $app->view()->set('nama', empty($args[0])?'':$args[0]);
    $app->view()->set('token', empty($args[1])?'':$args[1]);

    $app->render('konsumen/aktifasi_sukses.php');
});

