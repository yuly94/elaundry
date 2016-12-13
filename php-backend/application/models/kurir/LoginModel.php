<?php


class LoginModel{
      
    /**
     * Checking user login
     * @param String $login_email User login email id
     * @param String $login_password User login password
     * @return boolean User login status success/fail
     */
    public static function Login($login_email, $login_password) {
        // fetching user by email
        $app = \Slim\Slim::getInstance();
        
        $sql= "SELECT kurir_password FROM kurir WHERE kurir_email =:kurir_email";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_email'=>$login_email));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
   
            if (GeneratorModel::check_password($kurir["kurir_password"], $login_password)) {
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

       /**
     * Cek pemaksaan masuk
     * @param String $cek_email User login email id
     * @return boolean User login status success/fail
     */

    public static function cekPemaksaan($cek_email) {
        
    $app = \Slim\Slim::getInstance();
    
    // Get timestamp of current time 
    $now = time();
 
    // All login attempts are counted from the past  hours. 
    $percobaan_login = $now - (1 * 60 * 60);
 
        $sql = "SELECT waktu FROM mencoba_login WHERE email=:email AND waktu > :percobaan_login";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('email'=>$cek_email,'percobaan_login'=>$percobaan_login));
              
        // If there have been more than 5 failed logins 
        if ($stmt->rowCount() > 5) {
                       
            return true;
            
        } else {
            return false;
        }
     
}

    /**
     * Checking user login
     * @param String $login_email User login email id
     * @param String $login_password User login password
     * @return boolean User login status success/fail
     */
    public static function cekLogin($login_email, $login_password) {
        
        $app = \Slim\Slim::getInstance();
    
        // fetching user by email
        
        $sql = "SELECT kurir_password FROM kurir WHERE kurir_email =:login_email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('login_email'=>$login_email));
        
        while($result=$stmt->fetch()){ //for each result, do the following
         $kurir_password=$result['kurir_password'];
        }

        if ($stmt->rowCount() > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            if (PassHashModel::cek_password($kurir_password, $login_password)) {
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

  
    
    public static function loginGagal($login_email) {
    
    $app = \Slim\Slim::getInstance();
    
    $now = time();
	      
    $sql = "INSERT INTO mencoba_login(email, waktu)  VALUES ( :email, :now);";
    
    $stmt = $app->db->prepare($sql);
   
    $result = $stmt->execute(array('email'=>$login_email,'now'=>$now));

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
	
    
    public static function cekPercobaanLogin($login_email) {
        
    $app = \Slim\Slim::getInstance();    
    
    // All login attempts are counted from the past  hours. 
    $percobaan_login = time() - (1 * 60 * 60);
 
	$sql = "SELECT waktu FROM mencoba_login WHERE email = :email AND waktu > :valid_attempts";
 
        $stmt = $app->db->prepare($sql);
        
        // Execute the prepared query. 
        $stmt->execute(array(
            'email'=>$login_email,
            'valid_attempts'=>$percobaan_login));

      //  $stmt->store_result();
 
        // If there have been more than 5 failed logins 
        if ($stmt->rowCount() > 5) {
            return "6";
                   
        } else  if ($stmt->rowCount()) {
            
            return $stmt->rowCount();
                  
        } else {
            return false;
        }
    }    
}
