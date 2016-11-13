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
    public static function getPesanan($kurir_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT * FROM pemesanan WHERE kurir_id = :kurir_id ";

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
    
    
        /* ------------- `melakukan pemesanan` ------------------ */

    /**
     * Melakukan pemesanan
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public static function tambahPesanan($api_kurir_id, $paket_id,$pesanan_satuan,$koordinat_id,$catatan) {
        
        $app = \Slim\Slim::getInstance();
       
        $response = array();

        // First check if user already existed in db
        if (!ValidasiModel::cekKurir($kurir_email)) {
            
            $token_aktifasi = GeneratorModel::randStrGen(50);
            // insert query        
            $sql = "INSERT INTO kurir(kurir_id, kurir_nama, kurir_alamat, "
                    . "kurir_nohp, kurir_email, kurir_password, kurir_kunci_api, "
                    . "kurir_kode_aktifasi, kurir_kode_reset, kurir_dibuat_pada, kurir_status_aktifasi,kurir_status,kurir_status_reset) "
                    . "values(:kurir_id, :kurir_nama, :kurir_alamat, :kurir_nohp,"
                    . ":kurir_email, :kurir_password, :kurir_kunci_api, :kurir_kode_aktifasi,"
                    . ":kurir_kode_reset,   NOW(), :kurir_status_aktifasi,:kurir_status,:kurir_status_reset)";
       
            $stmt = $app->db->prepare($sql);
            $result = $stmt->execute(array(
            'kurir_id'=>GeneratorModel::generateUID(),
            'kurir_nama'=>$kurir_nama,
            'kurir_alamat'=>$kurir_alamat,
            'kurir_nohp'=>$kurir_nohp,
            'kurir_email'=>$kurir_email,
            'kurir_password'=> GeneratorModel::hash($kurir_password),
            'kurir_kunci_api'=> GeneratorModel::generateApiKey(),
            'kurir_kode_aktifasi'=> $token_aktifasi,
            'kurir_kode_reset'=> NULL,
            'kurir_status_aktifasi'=>"belum aktifasi",
            'kurir_status'=>"menunggu aktifasi",
            'kurir_status_reset'=>"none"
        ));
            
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                
                KirimEmailModel::emailAktifasi($kurir_email, $token_aktifasi, $kurir_nama);

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