<?php

    
/**
 * User Login
 * url - /home/login
 * method - POST
 * params - email, password
 */
$app->post('/kurir/login/', function() use ($app) {
            // check for required params
            BantuanModel::verifyRequiredParams(array('kurir_email', 'kurir_password'));

            // reading post params
            $login_email = $app->request()->post('kurir_email');
            $login_password = $app->request()->post('kurir_password');
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
            if (KurirModel::cekPassword($login_email, $login_password)) {
				
		if (KurirModel::updateKunciApiByEmail($login_email)) {

            // get the user by email
             
            if (($kurir = KurirModel::kurirByEmail($login_email))) {
	        $response['error'] = false;
                $response['message'] = "selamat datang, anda berhasil login";
                $response["login"] = $kurir ;
                
                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "gagal untuk mendapatkan data kurir silahkan coba lagi";
                }
				
               	} else {
					// unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "Update kunci api gagal, silahkan coba lagi";
				}			
            } else {
                // user credentials are wrong
		if (LoginModel::loginGagal($login_email))
		{		
			// check brute force
                     $cek_percobaan = LoginModel::cekPercobaanLogin($login_email);
                if ( $cek_percobaan =="6"){
                $response["error"] = true;
                $response['message'] = 'Login gagal, anda melebihi jumlah gagal yang di izinkan, silahkan coba lagi 60 menit kedepan';

                            } else // check brute force
                if ( $cek_percobaan){
                $response["error"] = true;
                $response['message'] = 'Login gagal, kesempatan kurang '.(6 - $cek_percobaan).' silahkan coba kembali';

                            }  			
                            } else {
                $response['error'] = true;
                $response['message'] = 'Login gagal, email atau password anda salah, silahkan coba kembali';	

                            }
            }    
	}			             
           // echoRespnse(200, $response);
           BantuanModel::echoRespnse (200, $response);
});

    
 