<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class AlamatModel {
                /**
     * Fetching all alamat
     * @param String $kurir_id
     */
    public static function getAlamat($kurir_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT * FROM kurir_alamat WHERE kurir_id = :kurir_id ";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_id'=>$kurir_id));
        
        $alamat=$stmt->fetchAll(PDO::FETCH_OBJ);
        
        if($stmt->rowCount() > 0)
        {  
            return $alamat;
        } else {
            return NULL;
               }
        }
    
}