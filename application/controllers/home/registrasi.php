<?php

use \FB;
use \My\Helper;


$app->post('/home/registrasi/', function () use ($app) {

            // check for required params
            Helper::verifyRequiredParams(array('nama', 'alamat','nohp','email', 'password'));
            
            $response = array();

            // reading post params
            $nama = $app->request->post('nama');
            $alamat = $app->request->post('alamat');
            $nohp = $app->request->post('nohp');
            $email = $app->request->post('email');
            $password = $app->request->post('password');

            // validating email address
            ValidasiModel::validasiEmail($email);


            $result = RegistrasiModel::membuatUser($nama, $alamat, $nohp, $email, $password);

            if ($result == USER_CREATED_SUCCESSFULLY) {

                $user = RegistrasiModel::getUserByEmail($email);
                
                 // use is found
                $response["error"] = FALSE;
                $response["uid"] = $user["konsumen_id"];
                $response["user"]["nama"] = $user["nama"];
                $response["user"]["alamat"] = $user["alamat"];
                $response["user"]["nohp"] = $user["nohp"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["api_key"] = $user["api_key"];
                $response["user"]["status"] = $user["status"];
                $response["user"]["created_at"] = $user["created_at"];
                $response["user"]["updated_at"] = $user["updated_at"];
                
            } else if ($result == USER_CREATE_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registering";
            } else if ($result == USER_ALREADY_EXISTED) {
                $response["error"] = true;
                $response["message"] = "Sorry, this email already existed";
            } 
            // echo json response
            Helper::echoRespnse(201, $response);
        });


$app->get('/home/registrasix/aktifasi/:id/', function ($id) use ($app) {

    $article = ArticleModel::getArticle($id);

    if(empty($article)){

        return Helper::response(false, array(), 'Article not found', 404);
    }

    return Helper::response(true, $article);
});

$app->get('/home/registrasi/aktifasi/:nama/(:token(/))', function () use ($app) {

    $args = func_get_args();

    $app->view()->set('nama', empty($args[0])?'':$args[0]);
    $app->view()->set('token', empty($args[1])?'':$args[1]);

    $app->render('aktifasi.php');
});

$app->get('/home/registrasi/aktifasi/:nama/:token/(:aktifkan(/))', function () use ($app) {

            $response = array();
            
            $args = func_get_args();

            $token = (empty($args[1])?'':$args[1]);

            $result = RegistrasiModel::aktifkanUser($token);

            if ($result) {

                $user = RegistrasiModel::getUserByToken($token);
                
                 // use is found
                $response["error"] = FALSE;
                $response["uid"] = $user["konsumen_id"];
                $response["user"]["nama"] = $user["nama"];
                $response["user"]["alamat"] = $user["alamat"];
                $response["user"]["nohp"] = $user["nohp"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["api_key"] = $user["api_key"];
                $response["user"]["status"] = $user["status"];
                $response["user"]["created_at"] = $user["created_at"];
                $response["user"]["updated_at"] = $user["updated_at"];
                
            } else {
                $response["error"] = true;
                $response["message"] = "Sorry, failed to activate, please check your token";
            } 
            // echo json response
            Helper::echoRespnse(201, $response);
        
});


$app->get('/home/registrasi/success/(:secret(/))', function () use ($app) {

    $args = func_get_args();

    $app->view()->set('secret', empty($args[0])?'':$args[0]);

    $app->render('aktifasi_sukses.php');
});

