<?php

$app->post('/kurir/registrasi/', function () use ($app) {

            // check for required params
            BantuanModel::verifyRequiredParams(array('kurir_nama', 'kurir_alamat','kurir_nohp','kurir_email', 'kurir_password'));
            
            $response = array();

            // reading post params
            $kurir_nama = $app->request->post('kurir_nama');
            $kurir_alamat = $app->request->post('kurir_alamat');
            $kurir_nohp = $app->request->post('kurir_nohp');
            $kurir_email = $app->request->post('kurir_email');
            $kurir_password = $app->request->post('kurir_password');

            // validating email address
            ValidasiModel::validasiEmail($kurir_email);
            
            // validating email address
            ValidasiModel::validasiNama($kurir_nama);
            
            // validating email address
            ValidasiModel::validasiPassword($kurir_password);

            $result = RegistrasiModel::membuatUser($kurir_nama, $kurir_alamat, $kurir_nohp, $kurir_email, $kurir_password);

            if ($result == USER_CREATED_SUCCESSFULLY) {

                $result = KurirModel::kurirByEmail($kurir_email);
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



$app->get('/kurir/registrasi/aktifasi/:nama/(:token(/))', function () use ($app) {

    $args = func_get_args();

    $app->view()->set('nama', empty($args[0])?'':$args[0]);
    $app->view()->set('token', empty($args[1])?'':$args[1]);

    $app->render('kurir/aktifasi.php');
});

$app->get('/kurir/registrasi/aktifasi/:nama/:token/(aktifkan(/))', function () use ($app) {

    $response = array();

    $args = func_get_args();

    $kode_aktifasi = (empty($args[1])?'':$args[1]);
        $app->view()->set('nama', empty($args[0])?'':$args[0]);

    $result = RegistrasiModel::aktifkanUser( $kode_aktifasi);

    if ($result) {

        $kurir = RegistrasiModel::getUserByToken($kode_aktifasi);

            // kurir di temukan mengirim data kurir
            $response["error"] = FALSE;
            $response["message"] = "Selamat account anda berhasil di aktifkan";
            $response["registrasi"] = $kurir;  
            
            $kirim_email = new KirimEmailModel();  
            $kirim_email->emailAktifasiSukses($kurir["kurir_email"], $kurir["kurir_nama"]);

            $app->redirect('sukses');

        } else {
            $response["error"] = true;
            $response["message"] = "Sorry, failed to activate, please check your token";
        } 
        // echo json response
        BantuanModel::echoRespnse(201, $response);
        
});


$app->get('/kurir/registrasi/aktifasi/:nama/:token/aktifkan/(sukses(/))', function () use ($app) {

    $args = func_get_args();
    $app->view()->set('nama', empty($args[0])?'':$args[0]);
    $app->view()->set('token', empty($args[1])?'':$args[1]);

    $app->render('kurir/aktifasi_sukses.php');
});

