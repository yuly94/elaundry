<?php


class ValidasiModel{
    
  
/**
 * Validating email address
 */
public static function validasiEmail($email) {
    $app = \Slim\Slim::getInstance();
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $response["error"] = true;
        $response["message"] = 'Email address is not valid';
        Helper::echoRespnse(400, $response);
        $app->stop();
    }
}

 // basic name validation

public static function validasiNama($nama) {
    
        $app = \Slim\Slim::getInstance();
  if (empty($nama)) {

    $response["error"] = true;
    $response["message"] = 'Please enter your full name.';
    
    BantuanModel::echoRespnse(200, $response);
    $app->stop();
   
  } else if (strlen($nama) < 3) {

    $response["error"] = true;
    $response["message"] = 'Name must have atleat 3 characters.';
    
    BantuanModel::echoRespnse(200, $response);
     $app->stop();

   
  } else if (!preg_match("/^[a-zA-Z ]+$/",$nama)) {
      
   $response["error"] = true;
   $response["message"] = 'Name must contain alphabets and space.';   
   
   BantuanModel::echoRespnse(400, $response);
    $app->stop();
   
  }
}


public static function validasiPassword($password){
    
    $app = \Slim\Slim::getInstance();
      // password validation
  if (empty($password)){
   $response["error"] = true;
   $response["message"] = "Please enter password.";
  } else if(strlen($password) < 6) {

   $response["error"] = true;
   $response["message"] = 'Password must have atleast 6 characters.';   
   
   
   BantuanModel::echoRespnse(200, $response);
    $app->stop();
  }
}

    /* ------------- `fungsi check email agar tidak dobel dobel` ------------------ */

    /**
     * Checking for duplicate user by email address
     * @param String $kurir_email email to check in db
     * @return boolean
     */
    public static function cekKurir($kurir_email) {
        
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_no , kurir_nama, kurir_id from kurir WHERE kurir_email = :kurir_email";
             
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_email'=>$kurir_email));
        
        $cek=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {

        $kurir["kurir_nama"] = $cek['kurir_nama'];
        $kurir["kurir_id"] = $cek['kurir_id'];

                return $kurir;
        } else {
            return FALSE;
        }

    
}
}
