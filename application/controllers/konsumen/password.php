<?php

$app->post('/konsumen/password/lupa/', function () use ($app) {

            // check for required params
            HelperModel::verifyRequiredParams(array('email'));

            // reading post params
            $email = $app->request()->post('email');
    
            // validating email address
            ValidasiModel::validasiEmail($email);
            $response = array();
            
	    $checkuser = ValidasiModel::isUserExists($email);
  	    if ($checkuser == "0") {   //2
           
		$response["error"] = "true";
      		$response["message"] = "Email Not Found";
      		HelperModel::echoRespnse(200, $response);
           

		     }  //3
			else {
                    $kon_id = $checkuser["konsumen_id"];
                    $nama = $checkuser["nama"];
		    $user = PasswordModel::ResetRequest($email,$kon_id,$nama);
		 //   $email_user =  $user["email_user"];
	    	  //  $token = $user["token"];
     			
		}
    }); //1



$app->post('/konsumen/password/reset/', function () use ($app) {

            // check for required params
            HelperModel::verifyRequiredParams(array('email','code','password'));

            // reading post params
            $email = $app->request()->post('email');
            $code = $app->request()->post('code');
            $password = $app->request()->post('password');

            $response = array();
            
            // validating email address
            ValidasiModel::validasiEmail($email);

	      $checkuser = ValidasiModel::isUserExists($email);
  if ( $checkuser ) {
    $nama = $checkuser["nama"];
    $result =  PasswordModel::resetPassword($email,$code,$password, $nama);



    if($result==1){

      $response["error"] = "true";
      $response["message"] = "Reset Password Failure, please try again";
      HelperModel::echoRespnse(200, $response);

    }  else if($result==2){

      $response["error"] = "true";
      $response["message"] = "Reset Password Failure, Your reset code expired, please resent your reset";
      HelperModel::echoRespnse(200, $response);

    }  else  if($result==3){

      $response["error"] = "true";
      $response["message"] = "Reset Password Failure, Your token incorrect";
      HelperModel::echoRespnse(200, $response);

    }
    else {

      $response["error"] = "false";
      $response["message"] = "Password Changed Successfully";
      HelperModel::echoRespnse(200, $response);

    }


  } else {

    $response["error"] = "true";
    $response["message"] = "Email does not exist";
    HelperModel::echoRespnse(200, $response);

      }

    }); //1
 