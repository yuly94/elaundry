<?php

class RegistrasiModel{
    
   
    /* ------------- `registrasi kurir` ------------------ */

    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public static function membuatUser($kurir_nama, $kurir_alamat, $kurir_nohp, $kurir_email, $kurir_password) {
        
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
                $kirim_email = new KirimEmailModel();
                $kirim_email->emailAktifasi($kurir_email, $token_aktifasi, $kurir_nama);

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

     /* ------------- `aktifasi kurir` ------------------ */

    /**
     * Activate new user
     * @param String $activator User activation code
     */
    
public static function aktifkanUser( $kurir_kode_aktifasi) {
    
        $app = \Slim\Slim::getInstance();

	$sql = "UPDATE kurir SET kurir_status =:kurir_status,"
                . " kurir_status_aktifasi =:kurir_status_aktifasi"
                . " WHERE kurir_kode_aktifasi = :kurir_kode_aktifasi";

	$kurir_status_aktifasi = "sudah diaktifasi";
        $kurir_status = "aktif";

        $stmt = $app->db->prepare($sql);
        $result = $stmt->execute(array(
            'kurir_status'=>$kurir_status,
            'kurir_status_aktifasi'=>$kurir_status_aktifasi,
            'kurir_kode_aktifasi'=>$kurir_kode_aktifasi       
        ));

        // Check for successful insertion
        if($result){


            //Update user password success

            return TRUE;

            } else {
               //Update user password failed
				
          //  $stmt->close();
            return FALSE;
        }
    }
      
    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public static function kurirByEmail($email) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_id, nama, alamat, nohp, email, api_key, status, created_at, last_login, updated_at FROM kurir WHERE email =:email";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('email'=>$email));
        
        $user=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {

          //  $stmt->close();
            return $user;
        } else {
            return FALSE;
        }
    }

    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public static function getUserByToken($kurir_kode_aktifasi) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT kurir_id, kurir_nama, kurir_alamat, kurir_nohp, kurir_email, kurir_kunci_api, kurir_status_aktifasi, kurir_dibuat_pada, kurir_login_terahir, kurir_update_pada FROM kurir WHERE kurir_kode_aktifasi =:kurir_kode_aktifasi";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('kurir_kode_aktifasi'=>$kurir_kode_aktifasi));
        
        $kurir=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {

          //  $stmt->close();
            return $kurir;
        } else {
            return FALSE;
        }
    }


    
}
