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
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_OBJ);
        
        if($stmt->rowCount() > 0)
        {  
            return $pemesanan;
        } else {
            return NULL;
               }
        }
        
        
        /**
             * Fetching all alamat
     * @param String $konsumen_id
     */
    public static function getDetailPesanan($konsumen_id, $pemesanan_id) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT * FROM laundry_pemesanan WHERE konsumen_id = :konsumen_id "
                . " AND pemesanan_id = :pemesanan_id";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_id'=>$konsumen_id,'pemesanan_id'=>$pemesanan_id));
        
        $pemesanan=$stmt->fetchAll(PDO::FETCH_OBJ);
        
        if($stmt->rowCount() > 0)
        {  
            return $pemesanan;
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

    
        public static function menambahkanPesanan($konsumen_id, $pemesanan_latitude, 
                $pemesanan_longitude, $pemesanan_alamat, $pemesanan_paket, $pemesanan_catatan,
                    $pemesanan_baju, $pemesanan_celana, $pemesanan_rok, $pemesanan_harga) {
        
        $app = \Slim\Slim::getInstance();
       
        $response = array();

        // First check if user already existed in db
        if ($konsumen_id) {
            
            $pemesanan_id = GeneratorModel::randAngka(10);
                   
            // insert query        
            $sql = "INSERT INTO laundry_pemesanan(pemesanan_id, konsumen_id, pemesanan_latitude, pemesanan_longitude, "
                    . "pemesanan_alamat, pemesanan_paket, pemesanan_catatan, pemesanan_baju, "
                    . "pemesanan_celana, pemesanan_rok, pemesanan_tanggal, pemesanan_harga,pemesanan_status) "
                    . "values(:pemesanan_id,:konsumen_id, :pemesanan_latitude, :pemesanan_longitude, "
                    . ":pemesanan_alamat, :pemesanan_paket, :pemesanan_catatan, :pemesanan_baju, "
                    . ":pemesanan_celana, :pemesanan_rok, NOW(),:pemesanan_harga, :pemesanan_status)";
       
            $stmt = $app->db->prepare($sql);
            $result = $stmt->execute(array(
            'pemesanan_id'=>$pemesanan_id,
            'konsumen_id'=>$konsumen_id,
            'pemesanan_latitude'=>$pemesanan_latitude,
            'pemesanan_longitude'=>$pemesanan_longitude,
            'pemesanan_alamat'=>$pemesanan_alamat,
            'pemesanan_paket'=>$pemesanan_paket,
            'pemesanan_catatan'=> $pemesanan_catatan,
            'pemesanan_baju'=> $pemesanan_baju,
            'pemesanan_celana'=> $pemesanan_celana,
            'pemesanan_rok'=> $pemesanan_rok,
            'pemesanan_harga'=> $pemesanan_harga,
            'pemesanan_status'=>"baru memesan"
        ));
            
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                
             

                return 0;
                
            } else {
                // Failed to create user
                return 1;
            }
        } else {
            // User with same email already existed in the db
            return 2;
        }

        return $response;
    }

           /**
     * Fetching user by email
     * @param String $konsumen_id User email id
     */
    public static function pemesananById($konsumen_id) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT pemesanan_id, konsumen_id, pemesanan_latitude, pemesanan_longitude, "
                    . "pemesanan_alamat, pemesanan_paket, pemesanan_catatan, pemesanan_baju, "
                    . "pemesanan_celana, pemesanan_rok, pemesanan_tanggal, pemesanan_harga,pemesanan_status FROM laundry_pemesanan "
                . "WHERE konsumen_id =:konsumen_id ORDER BY pemesanan_no DESC LIMIT 1" ;        

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_id'=>$konsumen_id));
        
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
    
    
          
                   /**
     * Fetching all alamat
     * @param String $pemesanan_status
     */
    public static function mendapatkanPesanan($konsumen_id, $pemesanan_status) {
        $app = \Slim\Slim::getInstance();
        
   
        $sql = "SELECT
            pemesanan_no,
      pemesanan_id,   l.konsumen_id,
      pemesanan_latitude,  pemesanan_longitude,  pemesanan_alamat,  pemesanan_catatan,
      pemesanan_paket,  pemesanan_baju,  pemesanan_celana,  pemesanan_rok,
      pemesanan_harga,  pemesanan_tanggal,  pemesanan_status,  konsumen_nama,  konsumen_nohp
FROM laundry_pemesanan l JOIN konsumen k
ON l.konsumen_id = k.konsumen_id WHERE k.konsumen_id=:konsumen_id AND l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'pemesanan_status'=>$pemesanan_status,
            'konsumen_id'=>$konsumen_id));
        
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
    public static function mendapatkanDetailPesanan($konsumen_id, $pemesanan_id, $pemesanan_status) {
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT
            pemesanan_no,  pemesanan_id, 
      l.konsumen_id,  pemesanan_latitude,  pemesanan_longitude,
      pemesanan_alamat,  pemesanan_catatan,  pemesanan_paket, pemesanan_baju, pemesanan_celana,
      pemesanan_rok, pemesanan_harga,  pemesanan_tanggal, pemesanan_status,  konsumen_nama, konsumen_nohp
        FROM laundry_pemesanan l JOIN konsumen k ON l.konsumen_id = k.konsumen_id WHERE k.konsumen_id=:konsumen_id AND l.pemesanan_id = :pemesanan_id AND  l.pemesanan_status=:pemesanan_status";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'konsumen_id'=>$konsumen_id,
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
            

    
      public function  kirimNotif( $konsumen_email, $konsumen_nama) {
      
            $to = $konsumen_email;
            $subject = "Pemberitahuan pembaruan password";
            $body = "'Hai $konsumen_nama',<br><br> 
            <p>Pesanan anda berhasil diproses</p>
            <p>Jika anda tidak merasa memperbarui password anda atau memiliki account, maka abaikan saja email ini</p>";

            $kirim_email = new EmailModel();   
            $kirim_email-> sentEmail($to,$subject,$body);
 }
    
}


