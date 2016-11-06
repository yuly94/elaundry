<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class AlamatModel {
                /**
     * Fetching all alamat
     * @param String $konsumen_id
     */
    public function getAlamat($konsumen_id) {
        $sql = "SELECT alamat_id, konsumen_id, alamat_nama, alamat_jalan, "
                . "alamat_kota, alamat_provinsi, alamat_latitude, alamat_longitude, "
                . "alamat_dibuat_pada, alamat_update FROM alamat_konsumen "
                . "WHERE konsumen_id = :konsumen_id ?";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_id'=>$konsumen_id));
        
        $alamat=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
        {
            return $alamat;
        } else {
            return FALSE;
               }
        }
    
}