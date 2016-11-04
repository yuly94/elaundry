<?php

use \FB;
use \My\Helper;
 

    
/**
 * User Login
 * url - /home/login
 * method - POST
 * params - email, password
 */
$app->post('/konsumen/login/', function() use ($app) {
            // check for required params
            Helper::verifyRequiredParams(array('email', 'password'));

            // reading post params
            $email = $app->request()->post('email');
            $password = $app->request()->post('password');
            $response = array();
    
            // validating email address
            ValidasiModel::validasiEmail($email);
            	
            // check brute force
            if ( LoginModel::checkbrute($email) =="6"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp exceed, please try again in 60 menit future';
       
		}
                    else {
            
            // check for correct email and password
            if (LoginModel::checkLogin($email, $password)) {
				
		if (LoginModel::updateApi($email)) {

            // get the user by email
            $user = LoginModel::getUserByEmail($email);

            if ($user != NULL) {
	
                // user is found
                $response["error"] = FALSE;
                $response["uid"] = $user["konsumen_id"];
                $response["user"]["nama"] = $user["nama"];
		$response["user"]["alamat"] = $user["alamat"];
		$response["user"]["nohp"] = $user["nohp"];
                $response["user"]["email"] = $user["email"];
                $response["user"]["api_key"] = $user["api_key"];
                $response["user"]["status"] = $user["status"];
                $response["user"]["created_at"] = $user["created_at"];
		$response["user"]["last_login"] = $user["last_login"];
                $response["user"]["updated_at"] = $user["updated_at"];
        
                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "Fetching user failed. Please try again";
                }
				
               	} else {
					// unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "Update Api failed. Please try again";
				}			
            } else {
                // user credentials are wrong
		if (KonsumenModel::loginFailed($email))
		{		
			// check brute force
            if ( KonsumenModel::checkAttemp($email) =="6"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp Exceed, please try again in 60 menit future';
             
			} else // check brute force
            if ( KonsumenModel::checkAttemp($email) =="5"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 1, please be carefull';
             
			} else // check brute force
            if ( LoginModel::checkAttemp($email) =="4"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 2, please try again';
             
			} else // check brute force
            if ( LoginModel::checkAttemp($email) =="3"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 3, please try again';
             
			} else  // check brute force
            if ( LoginModel::checkAttemp($email) =="2"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 4, please try again';
             
			} else // check brute force
            if ( LoginModel::checkAttemp($email) =="1"){
            $response["error"] = true;
            $response['message'] = 'Login failed. Login attemp remain 5, please try again';
			}			
			} else {
	    $response['error'] = true;
            $response['message'] = 'Login failed. Incorrect credentials';	
					
			};
        
            }    
	}			             
           // echoRespnse(200, $response);
           Helper::echoRespnse (200, $response);
});

    
 