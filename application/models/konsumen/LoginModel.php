<?php


class LoginModel{
    
    /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public static function Login($login_email, $login_password) {
        // fetching user by email
        $app = \Slim\Slim::getInstance();
        
        $sql= "SELECT konsumen_password FROM konsumen WHERE email =:email";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_email'=>$login_email));
        
        while($result=$stmt->fetch()){ //for each result, do the following
         $konsumen_password=$result['konsumen_password'];
        }

        if ($stmt->rowCount() > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            if (GeneratorModel::check_password($konsumen_password, $login_password)) {
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

    
    /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function cekLogin($login_email, $login_password) {
        
        $app = \Slim\Slim::getInstance();
    
        // fetching user by email
        
        $sql = "SELECT konsumen_password FROM konsumen WHERE konsumen_email =:login_email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('login_email'=>$login_email));
        
        while($result=$stmt->fetch()){ //for each result, do the following
         $konsumen_password=$result['konsumen_password'];
        }

        if ($stmt->rowCount() > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            if (PassHashModel::cek_password($konsumen_password, $login_password)) {
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

public function updateKunciApi($login_email) {

        // Generating API key
        $konsumen_kunci_api = GeneratorModel::generateApiKey();
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "UPDATE konsumen SET konsumen_kunci_api = :konsumen_kunci_api, konsumen_login_terahir = NOW() WHERE konsumen_email =:login_email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api,'login_email'=>$login_email));
		
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
	
    

public function loginGagal($login_email) {
    
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
	
    

    public function cekPercobaanLogin($login_email) {
        
    $app = \Slim\Slim::getInstance();    
    
    // All login attempts are counted from the past  hours. 
    $percobaan_login = time() - (1 * 60 * 60);
 
	$sql = "SELECT waktu FROM mencoba_login WHERE email = :email AND waktu > :valid_attempts";
 
        $stmt = $app->db->prepare($sql);
        
        // Execute the prepared query. 
        $stmt->execute(array('email'=>$login_email,'valid_attempts'=>$percobaan_login));

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
