<?php

    
/**
 * User Login
 * url - /home/login
 * method - POST
 * params - email, password
 */
$app->post('/konsumen/login/', function() use ($app) {
            // check for required params
            BantuanModel::verifyRequiredParams(array('konsumen_email', 'konsumen_password'));

            // reading post params
            $login_email = $app->request()->post('konsumen_email');
            $login_password = $app->request()->post('konsumen_password');
            $response = array();
    
            // validating email address
            ValidasiModel::validasiEmail($login_email);
            	
            // check brute force
            if ( LoginModel::cekPemaksaan($login_email) =="6"){
            $response["error"] = true;
            $response['message'] = 'Login gagal, anda melebihi jumlah gagal yang di izinkan, silahkan coba lagi 60 menit kedepan';
       
		}
                    else {
            
            // check for correct email and password
            if (LoginModel::cekLogin($login_email, $login_password)) {
				
		if (LoginModel::updateKunciApi($login_email)) {

            // get the user by email
             
            if (($konsumen = KonsumenModel::konsumenByEmail($login_email))) {
	 
                $response = $konsumen ;
                
                
                
            
                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "gagal untuk mendapatkan data konsumen silahkan coba lagi";
                }
				
               	} else {
					// unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "Update kunci api gagal, silahkan coba lagi";
				}			
            } else {
                // user credentials are wrong
		if (KonsumenModel::loginFailed($email))
		{		
			// check brute force
            if ( KonsumenModel::checkAttemp($email) =="6"){
            $response["error"] = true;
            $response['message'] = 'Login gagal, anda melebihi jumlah gagal yang di izinkan, silahkan coba lagi 60 menit kedepan';
             
			} else // check brute force
            if ( KonsumenModel::checkAttemp($email) =="5"){
            $response["error"] = true;
            $response['message'] = 'Login gagal, kesempatan kurang 1 lagi, harap berhati hati';
             
			} else // check brute force
            if ( LoginModel::checkAttemp($email) =="4"){
            $response["error"] = true;
            $response['message'] = 'Login gagal, kesempatan kurang 2 lagi, silahkan coba kembali';
             
			} else // check brute force
            if ( LoginModel::checkAttemp($email) =="3"){
            $response["error"] = true;
            $response['message'] = 'Login gagal, kesempatan kurang 3 lagi, silahkan coba kembali';
             
			} else  // check brute force
            if ( LoginModel::checkAttemp($email) =="2"){
            $response["error"] = true;
            $response['message'] = 'Login gagal, kesempatan kurang 4 lagi, silahkan coba kembali';
             
			} else // check brute force
            if ( LoginModel::checkAttemp($email) =="1"){
            $response["error"] = true;
            $response['message'] = 'Login gagal, kesempatan kurang 5 lagi, silahkan coba kembali';
			}			
			} else {
	    $response['error'] = true;
            $response['message'] = 'Login gagal, email atau password anda salah, silahkan coba kembali';	
					
			};
        
            }    
	}			             
           // echoRespnse(200, $response);
           BantuanModel::echoRespnse (200, $response);
});

    
 