<?php

use \My\PassHash;
use \My\Helper;

class PasswordModel{
    
   
public function ResetRequest($konsumen_email,$kon_id,$nama){

        $app = \Slim\Slim::getInstance();
        $user = array();

        // Generating password hash
        $random = GeneratorModel::randStrGen(20);
        $token = GeneratorModel::randStrGen(6);
        $hash = GeneratorModel::getHash($token, $random);
        
        $encrypted_temp_password = $hash["encrypted"];
        $salt = $hash["salt"];
        
	$tanggal = date("Y-m-d H:i:s");
 	$sql = "SELECT * FROM password_reset_request WHERE email = :email";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$konsumen_email           
        ));

        if ($stmt->rowCount() == 0){

 	$sql = "INSERT INTO password_reset_request(email,konsumen_id, encrypted_temp_password, salt, created_at) values(:email, :konsumen_id, :encrypted_temp_password, :salt, :created_at)";
        // insert query        
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$konsumen_email,
            'konsumen_id'=>$kon_id,
            'encrypted_temp_password'=>$encrypted_temp_password,
            'salt'=>$salt,
            'created_at'=>$tanggal
        ));

        if($stmt->rowCount() > 0)
            {
             
            KirimEmailModel::kirimReset($konsumen_email, $nama,  $token);

            } else {
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure";
      		HelperModel::echoRespnse(200, $response);
                
                //return false;

            }


        } else {
 

        $sql = "UPDATE password_reset_request SET email =:email, konsumen_id=:konsumen_id, encrypted_temp_password=:encrypted_temp_password, salt=:salt, created_at=:created_at";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$konsumen_email,
            'konsumen_id'=>$kon_id,
            'encrypted_temp_password'=>$encrypted_temp_password,
            'salt'=>$salt,
            'created_at'=>$tanggal
        ));
        
        if($stmt->rowCount() > 0)
            {

          //  $stmt->close();
            
           //  $user["email_user"] = $konsumen_email;
            // $user["token"] = $token;
            
            //return $user;
            
             KirimEmailModel::kirimReset($konsumen_email,$nama,  $token);

            } else {
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure";
      		HelperModel::echoRespnse(200, $response);
            //return false;

            }

        } 

    }
    

    public function resetPassword($email,$code,$password, $nama){
        
        $app = \Slim\Slim::getInstance();
 
        $sql = 'SELECT  encrypted_temp_password, konsumen_id, salt, created_at FROM password_reset_request WHERE email = :email';

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$email           
        ));

	$reset = $stmt->fetch();
        
        $encrypted_temp_password = $reset["encrypted_temp_password"];
        $konsumen_id = $reset["konsumen_id"];
     
        $salt = $reset["salt"];
        $created_at = $reset["created_at"];
        
        if (GeneratorModel::verifyHash($code.$salt, $encrypted_temp_password)) {
 
            $old = new DateTime($created_at);
            $now = new DateTime(date("Y-m-d H:i:s"));
            $diff = $now->getTimestamp() - $old->getTimestamp();

 
            if($diff < 180) {
 
			if ( PasswordModel::changePass($konsumen_id, $password)) {

			$to = $email;
			$subject = "Pemberitahuan pembaruan password";
			$body = "'Hai '.$nama.',<br><br> <p>Password account anda berhasil diperbarui</p>
			<p> Jika anda tidak merasa memperbarui password anda atau memiliki account, maka abaikan saja email ini</p>";

			EmailModel::sentEmail($to,$subject,$body);

			return 0;
		  
			} else {
			return 1;

		  	  }

            } else {
 
                return 2;
            }
        } else {
 
            return 3;
        }
    }



    	
    /* ------------- `fungsi ganti password konsumen` ------------------ */	
	
public function changePass($konsumen_id, $password_baru) {
    
        $app = \Slim\Slim::getInstance();
        // fetching user by email
  // Generating password hash
        $password_hash = PassHash::hash($password_baru);

        // Generating API key
        $api_key = GeneratorModel::generateApiKey();
		
	$sql = "UPDATE konsumen SET password_hash =:password_hash, api_key =:api_key, updated_at = NOW() WHERE konsumen_id = :konsumen_id";
        
        $stmt = $app->db->prepare($sql);
        $change=$stmt->execute(array(
            'password_hash'=>$password_hash,
            'api_key'=>$api_key,
            'konsumen_id'=>$konsumen_id,
        ));
		
        // Check for successful insertion
        if ($change) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }
	
	
    
    
}
