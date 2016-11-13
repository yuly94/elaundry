<?php

use \My\PassHash;
use \My\Helper;

class RegistrasiModel{
    
   
    /* ------------- `registrasi konsumen` ------------------ */

    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function membuatUser($nama, $alamat, $nohp, $email, $password) {
        
        $app = \Slim\Slim::getInstance();
       
        $response = array();

        // Validating Email
	ValidasiModel::validasiEmail($email);

	//create the activasion code
	$token_aktifasi = md5(uniqid(rand(),true));
        
        // First check if user already existed in db
        if (!ValidasiModel::isUserExists($email)) {
            
            // Generating password hash
            $password_hash = PassHash::hash($password);

            // Generating API key
            $api_key = GeneratorModel::generateApiKey();
        
	    // Generating UID key
	    $konsumen_id = GeneratorModel::generateUID();
            
            // insert query        
            $sql = "INSERT INTO konsumen(konsumen_id, nama, alamat, nohp, email, password_hash, api_key, activation, reset_token, actived, reset_complete, created_at, status) values(:konsumen_id, :nama, :alamat, :nohp, :email, :password_hash, :api_key, :activation,:reset_token, :actived, :reset_complete,NOW(),1)";
       
            $stmt = $app->db->prepare($sql);
            $result = $stmt->execute(array(
            'konsumen_id'=>$konsumen_id,
            'nama'=>$nama,
            'alamat'=>$alamat,
            'nohp'=>$nohp,
            'email'=>$email,
            'password_hash'=>$password_hash,
            'api_key'=>$api_key,
            'activation'=>$token_aktifasi,
            'reset_token'=>'0',
            'actived'=>'0',
            'reset_complete'=>'0'
        ));
            
            // Check for successful insertion
            if ($result) {
                // User successfully inserted
                
                //send email
                $to = $email;
		$subject = "Pendaftaran berhasil";
		$body = "<p>Selamat datang $nama.</p>
		<p>Pendaftaran anda di elaundry telah berhasil dilakukan</p>
		<p>Silahkan melakukan aktifasi dengan mengklik link berikut ini :
                <a href='".DIR."public/home/registrasi/aktifasi/$nama/$token_aktifasi'>".DIR."public/home/registrasi/aktifasi/$nama/$token_aktifasi</a></p>
                <p>Regards Site Admin</p>";

                EmailModel::sentEmail($to,$subject,$body);

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
    
public function aktifkanUser($activator) {
    
        $app = \Slim\Slim::getInstance();

	$sql = "UPDATE konsumen SET actived =:actived WHERE activation = :activation";

	$active = "1";

        $stmt = $app->db->prepare($sql);
        $result = $stmt->execute(array(
            'actived'=>$active,
            'activation'=>$activator       
        ));

        // Check for successful insertion
        if($result){

            //send email
            $to = "wachid.sst@gmail.com"; 
            $subject = "aktifasi berhasil";
            $body = "<p>Selamat datang . </p>
            <p>Selamat Aktifasi anda di Elaundry telah berhasil</p>
            <p></p>
            <p>Regards Site Admin</p>";

            EmailModel::sentEmail($to,$subject,$body);

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
    public function getUserByEmail($email) {
		
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
    public function getUserByToken($token) {
		
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT konsumen_id, nama, alamat, nohp, email, api_key, status, created_at, last_login, updated_at FROM konsumen WHERE activation =:activation";        
                
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('activation'=>$token));
        
        $user=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {

          //  $stmt->close();
            return $user;
        } else {
            return FALSE;
        }
    }


    
}
