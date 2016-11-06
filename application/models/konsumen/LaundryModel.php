<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class LaundryModel {
                /**
     * Fetching all alamat
     * @param String $konsumen_id
     */
    public function getAllPaket($paket_status) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT * FROM paket WHERE paket_status = :paket_status ";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('paket_status'=>$paket_status));
        
        $paket=$stmt->fetchAll(PDO::FETCH_OBJ);
        
        if($stmt->rowCount() > 0)
        {  
            return $paket;
        } else {
            return NULL;
               }
        }
    
}