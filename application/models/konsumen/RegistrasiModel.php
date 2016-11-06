<?php

class RegistrasiModel{
    
   
    /* ------------- `registrasi konsumen` ------------------ */

    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function membuatUser($konsumen_nama, $konsumen_alamat, $konsumen_nohp, $konsumen_email, $konsumen_password) {
        
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

     /* ------------- `aktifasi konsumen` ------------------ */

    /**
     * Activate new user
     * @param String $activator User activation code
     */
    
public function aktifkanUser( $konsumen_kode_aktifasi) {
    
        $app = \Slim\Slim::getInstance();

	$sql = "UPDATE konsumen SET konsumen_status =:konsumen_status, konsumen_status_aktifasi =:konsumen_status_aktifasi WHERE konsumen_kode_aktifasi = :konsumen_kode_aktifasi";

	$konsumen_status_aktifasi = "sudah diaktifasi";
        $konsumen_status = "aktif";

        $stmt = $app->db->prepare($sql);
        $result = $stmt->execute(array(
            'konsumen_status'=>$konsumen_status,
            'konsumen_status_aktifasi'=>$konsumen_status_aktifasi,
            'konsumen_kode_aktifasi'=>$konsumen_kode_aktifasi       
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
    public function konsumenByEmail($email) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id, nama, alamat, nohp, email, api_key, status, created_at, last_login, updated_at FROM konsumen WHERE email =:email";        
                
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
    public function getUserByToken($konsumen_kode_aktifasi) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id, konsumen_nama, konsumen_alamat, konsumen_nohp, konsumen_email, konsumen_kunci_api, konsumen_status_aktifasi, konsumen_dibuat_pada, konsumen_login_terahir, konsumen_update_pada FROM konsumen WHERE konsumen_kode_aktifasi =:konsumen_kode_aktifasi";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_kode_aktifasi'=>$konsumen_kode_aktifasi));
        
        $konsumen=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {

          //  $stmt->close();
            return $konsumen;
        } else {
            return FALSE;
        }
    }

    public function redirect(){
         $app->redirect("sukses");
    }

    
}
