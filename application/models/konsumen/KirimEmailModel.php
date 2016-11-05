<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



class KirimEmailModel{
    
    /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public static function kirimReset($email,  $nama, $token) {
			$to = $email;
			$subject = "Password Reset Nofification";
			$body = 'Hai '.$nama.',<br><br> Kode untuk mereset password kamu adalah : <b>'.$token.'
			</b> Kode ini akan kadaluarsa setelah 180 detik. 
			Masukkan kode ini sebelum 180 detik untuk mereset password anda.<br><br>Thanks';

			$sent = EmailModel::sentEmail($to,$subject,$body);

				if($sent) { //4
         			$response["error"] = "false";
      				$response["message"] = "Email Reset Password Success";
      				HelperModel::echoRespnse(200, $response);

  						    } //4
				else {

				$response["error"] = "true";
      				$response["message"] = "Email Reset Password Failure sent";
      				HelperModel::echoRespnse(200, $response);
				}
                                
}

    
}
