<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class PemesananModel {
                /**
     * Fetching all alamat
     * @param String $kurir_id
     */
    public static function getPesanan() {
        $app = \Slim\Slim::getInstance();
        
        //$sql = "SELECT * FROM laundry_pemesanan";
        
        $sql = "SELECT
            pemesanan_no,
      pemesanan_id, 
      l.konsumen_id,
      pemesanan_latitude,
      pemesanan_longitude,
      pemesanan_alamat,
      pemesanan_catatan,
      pemesanan_paket,
      pemesanan_baju,
      pemesanan_celana,
      pemesanan_rok,
      pemesanan_harga,
      pemesanan_tanggal,
      pemesanan_status,
      konsumen_nama,
      konsumen_nohp
FROM laundry_pemesanan l
JOIN konsumen k
ON l.konsumen_id = k.konsumen_id WHERE l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        
        $pemesanan_status = "baru memesan";
        $stmt->execute(array('pemesanan_status'=>$pemesanan_status));
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_OBJ);

        if($stmt->rowCount() > 0)
        {          
             
            return $pemesanan;
        } else {
            return NULL;
               }
        }
        
        
        /**
     * Fetching detail pesanan
     * @param String $pemesanan_id
     */
    public static function getDetailPesanan($pemesanan_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT
            pemesanan_no,
      pemesanan_id, 
      l.konsumen_id,
      pemesanan_latitude,
      pemesanan_longitude,
      pemesanan_alamat,
      pemesanan_catatan,
      pemesanan_paket,
      pemesanan_baju,
      pemesanan_celana,
      pemesanan_rok,
      pemesanan_harga,
      pemesanan_tanggal,
      pemesanan_status,
      konsumen_nama,
      konsumen_nohp
FROM laundry_pemesanan l
JOIN konsumen k
ON l.konsumen_id = k.konsumen_id WHERE l.pemesanan_id = :pemesanan_id AND  l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        $pemesanan_status = "baru memesan";
        $stmt->execute(array(
            'pemesanan_id'=>$pemesanan_id,
            'pemesanan_status'=>$pemesanan_status));
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
        {  
            return $pemesanan;
        } else {
            return NULL;
               }
        }
        
        
    public static function updatePemesanan($pemesanan_id, $kurir_id, $pemesanan_status) {
    
        $app = \Slim\Slim::getInstance();
        // fetching user by email

	//$update = time();
	$sql = "UPDATE laundry_pemesanan SET kurir_id =:kurir_id,pemesanan_status =:pemesanan_status, pemesanan_diupdate_pada = NOW() WHERE pemesanan_id = :pemesanan_id";
        
        $stmt = $app->db->prepare($sql);
        $mengupdate = $stmt->execute(array(
            'kurir_id'=>$kurir_id,
            'pemesanan_status'=>$pemesanan_status,
            'pemesanan_id'=>$pemesanan_id
        ));
		
	
      //  $stmt->fetch(PDO::FETCH_ASSOC);
        
        if($mengupdate)
            {
                //Update user password success
                return TRUE;
            
        } else {
            return NULL;
               }
        }

         

    public static function pengambilanPesanan() {
        $app = \Slim\Slim::getInstance();
        
        //$sql = "SELECT * FROM laundry_pemesanan";
        
        $sql = "SELECT
            pemesanan_no,
      pemesanan_id, 
      l.konsumen_id,
      pemesanan_latitude,
      pemesanan_longitude,
      pemesanan_alamat,
      pemesanan_catatan,
      pemesanan_paket,
      pemesanan_baju,
      pemesanan_celana,
      pemesanan_rok,
      pemesanan_harga,
      pemesanan_tanggal,
      pemesanan_status,
      konsumen_nama,
      konsumen_nohp
FROM laundry_pemesanan l
JOIN konsumen k
ON l.konsumen_id = k.konsumen_id WHERE l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        
        $pemesanan_status = "pengambilan laundry";
        $stmt->execute(array('pemesanan_status'=>$pemesanan_status));
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_OBJ);

        if($stmt->rowCount() > 0)
        {         
            return $pemesanan;
        } else {
            return NULL;
               }
        }
        
        
        /**
     * Fetching detail pesanan
     * @param String $pemesanan_id
     */
    public static function pengambilanDetailPesanan($pemesanan_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT
            pemesanan_no,
      pemesanan_id, 
      l.konsumen_id,
      pemesanan_latitude,
      pemesanan_longitude,
      pemesanan_alamat,
      pemesanan_catatan,
      pemesanan_paket,
      pemesanan_baju,
      pemesanan_celana,
      pemesanan_rok,
      pemesanan_harga,
      pemesanan_tanggal,
      pemesanan_status,
      konsumen_nama,
      konsumen_nohp
FROM laundry_pemesanan l
JOIN konsumen k
ON l.konsumen_id = k.konsumen_id WHERE l.pemesanan_id = :pemesanan_id AND  l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        $pemesanan_status = "pengambilan laundry";
        $stmt->execute(array(
            'pemesanan_id'=>$pemesanan_id,
            'pemesanan_status'=>$pemesanan_status));
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
        {  
            return $pemesanan;
        } else {
            return NULL;
               }
        }
        
        
                   /**
     * Fetching all alamat
     * @param String $kurir_id
     */
    public static function mendapatkanPesanan($pemesanan_status) {
        $app = \Slim\Slim::getInstance();
        
        //$sql = "SELECT * FROM laundry_pemesanan";
        
        $sql = "SELECT
            pemesanan_no,
      pemesanan_id, 
      l.konsumen_id,
      pemesanan_latitude,
      pemesanan_longitude,
      pemesanan_alamat,
      pemesanan_catatan,
      pemesanan_paket,
      pemesanan_baju,
      pemesanan_celana,
      pemesanan_rok,
      pemesanan_harga,
      pemesanan_tanggal,
      pemesanan_status,
      konsumen_nama,
      konsumen_nohp
FROM laundry_pemesanan l
JOIN konsumen k
ON l.konsumen_id = k.konsumen_id WHERE l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('pemesanan_status'=>$pemesanan_status));
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_OBJ);

        if($stmt->rowCount() > 0)
        {          
             
            return $pemesanan;
        } else {
            return NULL;
               }
        }
        
        
        /**
     * Fetching detail pesanan
     * @param String $pemesanan_id
     */
    public static function mendapatkanDetailPesanan($pemesanan_id, $pemesanan_status) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT
            pemesanan_no,
      pemesanan_id, 
      l.konsumen_id,
      pemesanan_latitude,
      pemesanan_longitude,
      pemesanan_alamat,
      pemesanan_catatan,
      pemesanan_paket,
      pemesanan_baju,
      pemesanan_celana,
      pemesanan_rok,
      pemesanan_harga,
      pemesanan_tanggal,
      pemesanan_status,
      konsumen_nama,
      konsumen_nohp
FROM laundry_pemesanan l
JOIN konsumen k
ON l.konsumen_id = k.konsumen_id WHERE l.pemesanan_id = :pemesanan_id AND  l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'pemesanan_id'=>$pemesanan_id,
            'pemesanan_status'=>$pemesanan_status));
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
        {  
            return $pemesanan;
        } else {
            return NULL;
               }
        }
            

}