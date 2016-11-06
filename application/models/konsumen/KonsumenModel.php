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
    public function konsumenByEmail($login_email) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id, konsumen_nama, konsumen_alamat, "
                . "konsumen_nohp, konsumen_email, konsumen_password, konsumen_kunci_api, "
                . "konsumen_kode_aktifasi, konsumen_kode_reset, konsumen_dibuat_pada, "
                . "konsumen_status_aktifasi,konsumen_status_reset,konsumen_login_terahir, konsumen_update_pada FROM konsumen "
                . "WHERE konsumen_email =:login_email";        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('login_email'=>$login_email));
        
        $konsumen=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
                // konsumen di temukan mengirim data konsumen
                $response["error"] = FALSE;
                $response["konsumen_id"] = $konsumen["konsumen_id"];
                $response["konsumen"]["konsumen_nama"] = $konsumen["konsumen_nama"];
		$response["konsumen"]["konsumen_alamat"] = $konsumen["konsumen_alamat"];
		$response["konsumen"]["konsumen_nohp"] = $konsumen["konsumen_nohp"];
                $response["konsumen"]["konsumen_email"] = $konsumen["konsumen_email"];
                $response["konsumen"]["konsumen_kunci_api"] = $konsumen["konsumen_kunci_api"];
                $response["konsumen"]["konsumen_status_aktifasi"] = $konsumen["konsumen_status_aktifasi"];
                $response["konsumen"]["konsumen_dibuat_pada"] = $konsumen["konsumen_dibuat_pada"];
		$response["konsumen"]["konsumen_login_terahir"] = $konsumen["konsumen_login_terahir"];
                $response["konsumen"]["konsumen_update_pada"] = $konsumen["konsumen_update_pada"];
                
              // echo json response
            return $response;
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
    public function getApiKeyById($konsumen_id) {
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
    public function getKonsumenIdByApi($konsumen_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_no FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api?";
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api));
        
        $konsumen_id=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $konsumen_id;
        } else {
            return FALSE;
        }
    }
    
    /**
     * Fetching konsumen id by api key
     * @param String $konsumen_kunci_api user api key
     */
    public function getKonsumenUnikId($konsumen_kunci_api) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kunci_api'=>$konsumen_kunci_api));
        
        $konsumen_id=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {
            return $konsumen_id;
        } else {
            return FALSE;
        }
    }

    /**
     * Fetching email by api key
     * @param String $konsumen_kunci_api user api key
     */
    public function getKonsumenEmail($konsumen_kunci_api) {
        
        $app = \Slim\Slim::getInstance();

        $sql = "SELECT email FROM konsumen WHERE konsumen_kunci_api = :konsumen_kunci_api";

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
    public function isValidApiKey($konsumen_kunci_api) {
        
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