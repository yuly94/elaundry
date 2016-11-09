<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class KonsumenModel{
    
    /**
     * Fetching user by email
     * @param String $login_email User email id
     */
    public static function konsumenByEmail($login_email) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id, konsumen_nama, konsumen_alamat, "
                . "konsumen_nohp, konsumen_email, konsumen_kunci_api,"
                . " konsumen_dibuat_pada, konsumen_status_aktifasi,"
                . "konsumen_status_reset,konsumen_login_terahir, "
                . "konsumen_update_pada FROM konsumen "
                . "WHERE konsumen_email =:login_email";        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('login_email'=>$login_email));
        
        $konsumen=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
    // fetching all user tasks
     

              // echo json response
            return $konsumen;
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
    public static function cekPassword($login_email, $login_password) {
        
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

      /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public static function cekPasswordById($konsumen_id, $konsumen_password) {
        
        $app = \Slim\Slim::getInstance();
    
        // fetching user by email
        
        $sql = "SELECT konsumen_password FROM konsumen WHERE konsumen_id =:konsumen_id";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_id'=>$konsumen_id));
        
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

    public static function updateKunciApiById($konsumen_id) {

        // Generating API key
        $konsumen_kunci_api = GeneratorModel::generateApiKey();
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "UPDATE konsumen SET konsumen_kunci_api = :konsumen_kunci_api, konsumen_login_terahir = NOW() WHERE konsumen_id =:konsumen_id";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api,'konsumen_id'=>$konsumen_id));
		
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
    
        
    /* ------------- `fungsi update api konsumen ketika login` ------------------ */

    public static function updateKunciApiByEmail($login_email) {

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
	
    
    
        /**
     * Fetching user by email
     * @param String $login_email User email id
     */
    public static function konsumenByKodeAktifasi($kode_aktifasi) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id, konsumen_nama, konsumen_alamat, "
                . "konsumen_nohp, konsumen_email, konsumen_kunci_api,"
                . " konsumen_dibuat_pada, konsumen_status_aktifasi,"
                . "konsumen_status_reset,konsumen_login_terahir, "
                . "konsumen_update_pada FROM konsumen "
                . "WHERE konsumen_kode_aktifasi =:konsumen_kode_aktifasi";        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kode_aktifasi'=>$kode_aktifasi));
        
        $konsumen=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
    // fetching all user tasks   

              // echo json response
            return $konsumen;
        } else {
            return false;
        }
    }
    
    
    public static function konsumenByApi($secret, $activeKeyOnly = true){

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


    	
    /**
     * Fetching user api key
     * @param String $konsumen_id user id primary key in user table
     */
    public static function getApiKeyById($konsumen_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_kunci_api FROM konsumen WHERE konsumen_id = :konsumen_id?";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_id'=>$konsumen_id));
        
        $kunci_api=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
              // echo json response

            return $kunci_api;
        } else {
            return FALSE;
        }
    }

    
        /**
     * Fetching user id by api key
     * @param String $konsumen_kunci_api user api key
     */
    public static function getKonsumenByApi($konsumen_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_no, konsumen_id, konsumen_email FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api";
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api));
        
        $konsumen=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $konsumen;
        } else {
            return FALSE;
        }
    }
    
    
    /**
     * Fetching user id by api key
     * @param String $konsumen_kunci_api user api key
     */
    public static function getKonsumenIdByApi($konsumen_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_no FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api";
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api));
        
        $konsumen_id=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $konsumen_id["konsumen_no"];
        } else {
            return FALSE;
        }
    }
    
    /**
     * Fetching konsumen id by api key
     * @param String $konsumen_kunci_api user api key
     */
    public static function getKonsumenUnikId($konsumen_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api));
        
        $konsumen_id=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $konsumen_id["konsumen_id"];
        } else {
            return FALSE;
        }
    }

    /**
     * Fetching email by api key
     * @param String $konsumen_kunci_api user api key
     */
    public static function getKonsumenEmail($konsumen_kunci_api) {
        
        $app = \Slim\Slim::getInstance();

        $sql = "SELECT konsumen_email FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api));
        
        $konsumen_email=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $konsumen_email;
        } else {
            return NULL;
        }
    }


    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $konsumen_kunci_api user api key
     * @return boolean
     */
    public static function isValidApiKey($konsumen_kunci_api) {
        
        $app = \Slim\Slim::getInstance();

        $sql = "SELECT konsumen_id FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api));
        
        $stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return true;
        }

    }
    
}