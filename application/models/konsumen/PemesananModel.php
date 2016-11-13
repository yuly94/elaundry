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
    public static function getPesanan($konsumen_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT * FROM laundry_pemesanan WHERE konsumen_id = :konsumen_id ";

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
    
    
        /* ------------- `melakukan pemesanan` ------------------ */

    /**
     * Melakukan pemesanan
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public static function tambahPesanan($api_konsumen_id, $paket_id,$pesanan_satuan,$koordinat_id,$catatan) {
        
        $app = \Slim\Slim::getInstance();
       
        $response = array();

        // First check if user already existed in db
        if (!ValidasiModel::cekKonsumen($konsumen_email)) {
            
            $token_aktifasi = GeneratorModel::randStrGen(50);
            // insert query        
            $sql = "INSERT INTO konsumen(konsumen_id, konsumen_nama, konsumen_alamat, "
                    . "konsumen_nohp, konsumen_email, konsumen_password, konsumen_kunci_api, "
                    . "konsumen_kode_aktifasi, konsumen_kode_reset, konsumen_dibuat_pada, konsumen_status_aktifasi,konsumen_status,konsumen_status_reset) "
                    . "values(:konsumen_id, :konsumen_nama, :konsumen_alamat, :konsumen_nohp,"
                    . ":konsumen_email, :konsumen_password, :konsumen_kunci_api, :konsumen_kode_aktifasi,"
                    . ":konsumen_kode_reset,   NOW(), :konsumen_status_aktifasi,:konsumen_status,:konsumen_status_reset)";
       
            $stmt = $app->db->prepare($sql);
            $result = $stmt->execute(array(
            'konsumen_id'=>GeneratorModel::generateUID(),
            'konsumen_nama'=>$konsumen_nama,
            'konsumen_alamat'=>$konsumen_alamat,
            'konsumen_nohp'=>$konsumen_nohp,
            'konsumen_email'=>$konsumen_email,
            'konsumen_password'=> GeneratorModel::hash($konsumen_password),
            'konsumen_kunci_api'=> GeneratorModel::generateApiKey(),
            'konsumen_kode_aktifasi'=> $token_aktifasi,
            'konsumen_kode_reset'=> NULL,
            'konsumen_status_aktifasi'=>"belum aktifasi",
            'konsumen_status'=>"menunggu aktifasi",
            'konsumen_status_reset'=>"none"
        ));
            
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                
                KirimEmailModel::emailAktifasi($konsumen_email, $token_aktifasi, $konsumen_nama);

                return USER_CREATED_SUCCESSFULLY;
            } else {
                // Failed to create user
                return USER_CREATE_FAILED;
            }
        } else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED;
        }

        return $response;
    }

}