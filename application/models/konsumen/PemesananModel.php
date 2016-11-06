<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class PemesananModel {
                /**
     * Fetching all alamat
     * @param String $konsumen_id
     */
    public function getPesanan($konsumen_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT * FROM pemesanan WHERE konsumen_id = :konsumen_id ";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_id'=>$konsumen_id));
        
        $alamat=$stmt->fetchAll(PDO::FETCH_OBJ);
        
        if($stmt->rowCount() > 0)
        {  
            return $alamat;
        } else {
            return NULL;
               }
        }
    
}