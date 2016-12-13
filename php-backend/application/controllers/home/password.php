<?php

use \FB;
use \My\Helper;


$app->post('/home/password/lupa/', function () use ($app) {

            // check for required params
            Helper::verifyRequiredParams(array('email'));

            // reading post params
            $email = $app->request()->post('email');
    
            // validating email address
            ValidasiModel::validasiEmail($email);
            $response = array();
            
	    $checkuser = ValidasiModel::isUserExists($email);
  	    if ($checkuser == "0") {   //2
           
		$response["error"] = "true";
      		$response["message"] = "Email Not Found";
      		Helper::echoRespnse(200, $response);
           

		     }  //3
			else {
                    $kon_id = $checkuser["konsumen_id"];
                    $nama = $checkuser["nama"];
		    $user = PasswordModel::ResetRequest($email,$kon_id,$nama);
		    $email_user =  $user["email_user"];
	    	    $token = $user["token"];
     			


		}
    }); //1



$app->post('/home/password/reset/', function () use ($app) {

            // check for required params
            Helper::verifyRequiredParams(array('email','code','password'));

            // reading post params
            $email = $app->request()->post('email');
            $code = $app->request()->post('code');
            $password = $app->request()->post('password');

            $response = array();
            
            // validating email address
            ValidasiModel::validasiEmail($email);

	      
  if ( ValidasiModel::isUserExists($email)) {

    $result =  PasswordModel::resetPassword($email,$code,$password);



    if(!$result){

      $response["error"] = "true";
      $response["message"] = "Reset Password Failure";
      Helper::echoRespnse(200, $response);

    } else {

      $response["error"] = "false";
      $response["message"] = "Password Changed Successfully";
      Helper::echoRespnse(200, $response);

    }


  } else {

    $response["error"] = "true";
    $response["message"] = "Email does not exist";
    Helper::echoRespnse(200, $response);

      }

    }); //1
 