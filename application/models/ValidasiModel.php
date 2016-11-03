<?php

use \My\Helper;

class ValidasiModel{
    
  
/**
 * Validating email address
 */
function validasiEmail($email) {
    $app = \Slim\Slim::getInstance();
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $response["error"] = true;
        $response["message"] = 'Email address is not valid';
        Helper::echoRespnse(400, $response);
        $app->stop();
    }
}

 // basic name validation

function validasiNama($email) {
  if (empty($name)) {

    $response["error"] = true;
    $response["message"] = 'Please enter your full name.';
    
    Helper::echoRespnse(400, $response);
   
  } else if (strlen($name) < 3) {

    $response["error"] = true;
    $response["message"] = 'Name must have atleat 3 characters.';
    
    Helper::echoRespnse(400, $response);

   
  } else if (!preg_match("/^[a-zA-Z ]+$/",$name)) {
      
   $response["error"] = true;
   $response["message"] = 'Name must contain alphabets and space.';   
   
   Helper::echoRespnse(400, $response);
   
  }
}


public function validasiPassword($password){
      // password validation
  if (empty($password)){
   $error = true;
   $passError = "Please enter password.";
  } else if(strlen($password) < 6) {

   $response["error"] = true;
   $response["message"] = 'Password must have atleast 6 characters.';   
   
   
   Helper::echoRespnse(400, $response);
  }
}

    /* ------------- `fungsi check email agar tidak dobel dobel` ------------------ */

    /**
     * Checking for duplicate user by email address
     * @param String $email email to check in db
     * @return boolean
     */
    public function isUserExists($email) {
        
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT id from konsumen WHERE email = :email";
             
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('email'=>$email));
        
        $stmt->fetch(PDO::FETCH_ASSOC);
   
        if($stmt->rowCount() > 0)
        {
	
        return TRUE;
        }

    
}
}
