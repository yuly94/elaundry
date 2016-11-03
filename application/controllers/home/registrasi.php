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


