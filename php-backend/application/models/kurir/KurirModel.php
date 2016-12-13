<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class KurirModel{
    
    /**
     * Fetching user by email
     * @param String $login_email User email id
     */
    public static function kurirByEmail($login_email) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_id, kurir_nama, kurir_alamat, "
                . "kurir_nohp, kurir_email, kurir_kunci_api,"
                . "kurir_dibuat_pada, kurir_status_aktifasi,"
                . "kurir_status_reset,kurir_login_terahir, "
                . "kurir_update_pada FROM kurir "
                . "WHERE kurir_email =:login_email";        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('login_email'=>$login_email));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
    // fetching all user tasks
     

              // echo json response
            return $kurir;
        } else {
            return false;
        }
    }
    
       
       /**
     * Fetching user by email
     * @param String $kurir_id User email id
     */
    public static function kurirById($kurir_id) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_id, kurir_nama, kurir_alamat, "
                . "kurir_nohp, kurir_email, kurir_kunci_api,"
                . " kurir_dibuat_pada, kurir_status_aktifasi,"
                . "kurir_status_reset,kurir_login_terahir, "
                . "kurir_update_pada FROM kurir "
                . "WHERE kurir_id =:kurir_id";        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_id'=>$kurir_id));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
    // fetching all user tasks
     

              // echo json response
            return $kurir;
        } else {
            return false;
        }
    } 
       /**
     * Fetching user by email
     * @param String $kurir_kunci_api User email id
     */
    public static function kurirByApi($kurir_kunci_api) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_id, kurir_nama, kurir_alamat, "
                . "kurir_nohp, kurir_email, kurir_kunci_api,"
                . " kurir_dibuat_pada, kurir_status_aktifasi,"
                . "kurir_status_reset,kurir_login_terahir, "
                . "kurir_update_pada FROM kurir "
                . "WHERE kurir_kunci_api =:kurir_kunci_api";        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
    // fetching all user tasks
     

              // echo json response
            return $kurir;
        } else {
            return false;
        }
    }

    
     
    /* ------------- `login kurir` ------------------ */
    
    /**
     * Checking user login
     * @param String $login_email User login email id
     * @param String $login_password User login password
     * @return boolean User login status success/fail
     */
    public static function cekPassword($login_email, $login_password) {
        
        $app = \Slim\Slim::getInstance();
    
        // fetching user by email
        
        $sql = "SELECT kurir_password FROM kurir WHERE kurir_email =:login_email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('login_email'=>$login_email));
        
        //for each result, do the following
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            if (PassHashModel::cek_password($kurir["kurir_password"], $login_password)) {
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

 
    /**
     * Checking password dengan menggunakan 
     * @param String $kurir_id User login email id
     * @param String $password_lama User login password
     * @return boolean User login status success/fail
     */
    public static function cekPasswordById($kurir_id, $password_lama) {
        
        $app = \Slim\Slim::getInstance();
    
        // fetching user by email
        
        $sql = "SELECT kurir_password, kurir_email, kurir_nama FROM kurir WHERE kurir_id =:kurir_id";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_id'=>$kurir_id));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            // Found user with the email
            // Now verify the password

            if (PassHashModel::cek_password($kurir["kurir_password"], $password_lama)) {
                // User password is correct
                return $kurir;
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

    
    
    
       
    /* ------------- `fungsi update api kurir ketika login` ------------------ */

    public static function updateKunciApiById($kurir_id) {

        // Generating API key
        $kurir_kunci_api = GeneratorModel::generateApiKey();
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "UPDATE kurir SET kurir_kunci_api = :kurir_kunci_api, kurir_login_terahir = NOW() WHERE kurir_id =:kurir_id";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api,'kurir_id'=>$kurir_id));
		
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
    
        
    /* ------------- `fungsi update api kurir ketika login` ------------------ */

    public static function updateKunciApiByEmail($login_email) {

        // Generating API key
        $kurir_kunci_api = GeneratorModel::generateApiKey();
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "UPDATE kurir SET kurir_kunci_api = :kurir_kunci_api, kurir_login_terahir = NOW() WHERE kurir_email =:login_email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api,'login_email'=>$login_email));
		
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
     * @param String $kode_aktifasi User email id
     */
    public static function kurirByKodeAktifasi($kode_aktifasi) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_id, kurir_nama, kurir_alamat, "
                . "kurir_nohp, kurir_email, kurir_kunci_api,"
                . "kurir_dibuat_pada, kurir_status_aktifasi,"
                . "kurir_status_reset,kurir_login_terahir, "
                . "kurir_update_pada FROM kurir "
                . "WHERE kurir_kode_aktifasi =:kurir_kode_aktifasi";        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kode_aktifasi'=>$kode_aktifasi));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
    // fetching all user tasks   

              // echo json response
            return $kurir;
        } else {
            return false;
        }
    }
    
    
    public static function kurirByApixx($secret, $activeKeyOnly = true){

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
     * @param String $kurir_id user id primary key in user table
     */
    public static function getApiKeyById($kurir_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_kunci_api FROM kurir WHERE kurir_id = :kurir_id?";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_id'=>$kurir_id));
        
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
     * @param String $kurir_kunci_api user api key
     */
    public static function getKurirByApi($kurir_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_no, kurir_id, kurir_email FROM kurir WHERE kurir_kunci_api = :kurir_kunci_api";
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $kurir;
        } else {
            return FALSE;
        }
    }
    
    
    /**
     * Fetching user id by api key
     * @param String $kurir_kunci_api user api key
     */
    public static function getKurirIdByApi($kurir_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_no FROM kurir WHERE kurir_kunci_api = :kurir_kunci_api";
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api));
        
        $kurir_id=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $kurir_id["kurir_no"];
        } else {
            return FALSE;
        }
    }
    
    /**
     * Fetching kurir id by api key
     * @param String $kurir_kunci_api user api key
     */
    public static function getKurirUnikId($kurir_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_id FROM kurir WHERE kurir_kunci_api = :kurir_kunci_api";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api));
        
        $kurir_id=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $kurir_id["kurir_id"];
        } else {
            return FALSE;
        }
    }

    /**
     * Fetching email by api key
     * @param String $kurir_kunci_api user api key
     */
    public static function getKurirEmail($kurir_kunci_api) {
        
        $app = \Slim\Slim::getInstance();

        $sql = "SELECT kurir_email FROM kurir WHERE kurir_kunci_api = :kurir_kunci_api";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api));
        
        $kurir_email=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $kurir_email;
        } else {
            return NULL;
        }
    }


    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $kurir_kunci_api user api key
     * @return boolean
     */
    public static function isValidApiKey($kurir_kunci_api) {
        
        $app = \Slim\Slim::getInstance();

        $sql = "SELECT kurir_id FROM kurir WHERE kurir_kunci_api = :kurir_kunci_api";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kunci_api'=>$kurir_kunci_api));
        
        $stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return true;
        }

    }
    
}