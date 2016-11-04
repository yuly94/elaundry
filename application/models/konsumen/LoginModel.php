<?php

use \My\PassHash;
use \My\Helper;


class LoginModel{
    
    /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public static function Login($email, $password) {
        // fetching user by email
        $app = \Slim\Slim::getInstance();
        
        $sql= "SELECT password_hash FROM konsumen WHERE email =:email";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('email'=>$email));
        
        while($result=$stmt->fetch()){ //for each result, do the following
         $password_hash=$result['password_hash'];
        }

        if ($stmt->rowCount() > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                                
               // Password is not correct
               // We record this attempt in the database
               // loginFailed($email);
                return FALSE;
               
            }
        } else {
            $stmt->close();

            // user not existed with the email
            return FALSE;
        }
    }


    public static function getAccountByAPISecretKey($secret, $activeKeyOnly = true){

        $app = \Slim\Slim::getInstance();

        $sql = "SELECT * FROM account_api_keys k
        JOIN account a ON k.acc_id=a.acc_id
        WHERE k.key_secret=:key_secret";

        if($activeKeyOnly){

            $sql .= " AND k.key_status='On' AND a.locked='No' AND a.deleted='No'";
        }

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('key_secret'=>$secret));

        return $stmt->fetch();
    }
    
    
    public static function checkbrute($email) {
        
    $app = \Slim\Slim::getInstance();
    
    // Get timestamp of current time 
    $now = time();
 
    // All login attempts are counted from the past  hours. 
    $valid_attempts = $now - (1 * 60 * 60);
 
        $sql = "SELECT waktu FROM login_attemps WHERE email=:email AND waktu > :valid_attempts";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('email'=>$email,'valid_attempts'=>$valid_attempts));
              
        // If there have been more than 5 failed logins 
        if ($stmt->rowCount() > 5) {
            return true;
            
        } else {
            return false;
        }
     
}

    
    /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function checkLogin($email, $password) {
        
        $app = \Slim\Slim::getInstance();
    
        // fetching user by email
        
        $sql = "SELECT password_hash FROM konsumen WHERE email =:email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('email'=>$email));
        
        while($result=$stmt->fetch()){ //for each result, do the following
         $password_hash=$result['password_hash'];
        }

        if ($stmt->rowCount() > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                                
               // Password is not correct
               // We record this attempt in the database
               // loginFailed($email);
                return FALSE;
               
            }
        } else {
           // $stmt->close();

            // user not existed with the email
            return FALSE;
        }
    }

    
    
    /* ------------- `fungsi update api konsumen ketika login` ------------------ */

public function updateApi($email) {

        // Generating API key
        $api_key = GeneratorModel::generateApiKey();
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "UPDATE konsumen SET api_key = :api_key, last_login = NOW() WHERE email =:email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('api_key'=>$api_key,'email'=>$email));
		
        // Check for successful insertion
        if ($stmt->rowCount() > 0) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            //$stmt->close();
            return FALSE;
        }
    }
	
    
    
    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id, nama, alamat, nohp, email, api_key, status, created_at, last_login, updated_at FROM konsumen WHERE email =:email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('email'=>$email));
        
        $user=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {

          //  $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

    

public function loginFailed($email) {
    
    $app = \Slim\Slim::getInstance();
    
    $now = time();
	      
    $sql = "INSERT INTO login_attemps(email, waktu)  VALUES ( :email, :now);";
    
    $stmt = $app->db->prepare($sql);
   
    $result = $stmt->execute(array('email'=>$email,'now'=>$now));

   // $stmt->close();	
		// Check for successful insertion
        if ($result) {		
            // User successfully inserted
             return TRUE;	
            } else {
            // Failed to create user
               return FALSE;
            }
    }
	
    

    public function checkAttemp($email) {
        
    $app = \Slim\Slim::getInstance();    
    
    // All login attempts are counted from the past  hours. 
    $valid_attempts = time() - (1 * 60 * 60);
 
	$sql = "SELECT waktu FROM login_attemps WHERE email = :email AND waktu > :valid_attempts";
 
        $stmt = $app->db->prepare($sql);
        
        // Execute the prepared query. 
        $stmt->execute(array('email'=>$email,'valid_attempts'=>$valid_attempts));

      //  $stmt->store_result();
 
        // If there have been more than 5 failed logins 
        if ($stmt->rowCount() > 5) {
            return "6";
                   
        } else  if ($stmt->rowCount() == 5) {
            return "5";
                  
        } else  if ($stmt->rowCount() == 4) {
            return "4";
            
        } else  if ($stmt->rowCount() == 3) {
            return "3";
                     
        } else  if ($stmt->rowCount() == 2) {
            return "2";
                     
        } else  if ($stmt->rowCount() == 1) {
            return "1";
                  
        } else {
            return false;
        }
}
    
}
