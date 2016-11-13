<?php

/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 *
 * @author Yuly
 * @link URL Tutorial link
 */
 

class DbHandlerAuth {

    private $conn;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /* ------------- `registrasi konsumen` ------------------ */

    /**
     * Creating new user
     * @param String $name User full name
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function createUser($nama, $alamat, $nohp, $email, $password) {
        require_once 'PassHash.php';
        $response = array();

        // Validating Email
	validateEmail($email);

	//create the activasion code
	$activasion = md5(uniqid(rand(),true));
        
        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
        // Generating password hash
        $password_hash = PassHash::hash($password);

        // Generating API key
        $api_key = $this->generateApiKey();
	    // Generating UID key
	    $konsumen_id = $this->generateUID();
            // insert query
            $stmt = $this->conn->prepare("INSERT INTO konsumen(konsumen_id, nama, alamat, nohp, email, password_hash, api_key, activation,  created_at, status) values(?, ?, ?, ?, ?, ?, ?, ?, NOW(),1)");
            $stmt->bind_param("ssssssss", $konsumen_id, $nama, $alamat, $nohp, $email, $password_hash, $api_key,$activasion);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted

			//send email
			$to = $email;
			$subject = "Pendaftaran berhasil";
			$body = "<p>Selamat datang $nama.</p>
			<p>Pendaftaran anda di elaundry telah berhasil dilakukan</p>
			<p>Silahkan melakukan aktifasi dengan mengklik link berikut ini : <a href='".DIR."v1/index.php/activasion?activator=$activasion'>".DIR."v1/index.php/activasion?activator=$activasion</a></p>
            <p>Regards Site Admin</p>";

			sentEmail($to,$subject,$body);

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

    
    // Email validation function	
function validateEmail($email) {
    $app = \Slim\Slim::getInstance();

	/* Frameworks Like CodeIgniter and Slim they provide "FILTER_VALIDATE_EMAIL" to verify email */
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
		
		return EMAIL_IS_INVALID;
	}	
}
    
    /* ------------- `aktifasi konsumen` ------------------ */

    /**
     * Activate new user
     * @param String $activator User activation code
     */
    
public function activatedUser($activator) {

	$sql = "UPDATE konsumen SET actived =? WHERE activation = ?";
	$stmt = $this->conn->prepare($sql);
	$active = "1";
        $stmt->bind_param("ss", $active, $activator);

        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
		
        // Check for successful insertion
        if ($num_affected_rows > 0) {
                //Update user password success
                return TRUE;

//send email
            $to = $email;
            $subject = "aktifasi berhasil";
            $body = "<p>Selamat datang $nama.</p>
            <p>Selamat Aktifasi anda di Elaundry telah berhasil</p>
            <p></p>
            <p>Regards Site Admin</p>";

            sentEmail($to,$subject,$body);


            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }
	

    /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function checkLogin($email, $password) {
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password_hash FROM konsumen WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->bind_result($password_hash);
        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                                
               // Password is not correct
               // We record this attempt in the database
               // loginFailed($email);
                return FALSE;
               
            }
        } else {
            $stmt->close();

            // user not existed with the email
            return FALSE;
        }
    }

    /* ------------- `ganti password konsumen` ------------------ */	

public function checkChange($konsumen_id, $password_lama) {
        // fetching user by email
        $stmt = $this->conn->prepare("SELECT password_hash FROM konsumen WHERE konsumen_id = ?");

        $stmt->bind_param("s", $konsumen_id);
        $stmt->execute();
        $stmt->bind_result($password_hash);
        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password
            $stmt->fetch();
            $stmt->close();
			
        if (PassHash::check_password($password_hash, $password_lama)) {
        // IF Old user password is correct
			
                return TRUE;
            } else {
                // Old user password is incorrect
                return FALSE;            
            }
        } else {
            $stmt->close();
            // user not existed with the id
            return FALSE;
        }
    }



    /* ------------- `fungsi update api konsumen ketika login` ------------------ */

public function updateApi($email) {

        // Generating API key
        $api_key = $this->generateApiKey();
		
	$update = time();
	$sql = "UPDATE konsumen SET api_key =?, last_login = NOW() WHERE email = ?";
	$stmt = $this->conn->prepare($sql);
        $stmt->bind_param("ss", $api_key, $email);

        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
		
        // Check for successful insertion
        if ($num_affected_rows > 0) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }
	

	
    /* ------------- `fungsi ganti password konsumen` ------------------ */	
	
public function changePass($konsumen_id, $password_baru) {
        // fetching user by email
  // Generating password hash
        $password_hash = PassHash::hash($password_baru);

        // Generating API key
        $api_key = $this->generateApiKey();
		
	$update = time();
	$sql = "UPDATE konsumen SET password_hash =?, api_key =?, updated_at = NOW() WHERE konsumen_id = ?";
	$stmt = $this->conn->prepare($sql);
        $stmt->bind_param("sss", $password_hash, $api_key,  $konsumen_id);

        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
		
        // Check for successful insertion
        if ($num_affected_rows > 0) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }
	

	
    /* ------------- `fungsi ganti password konsumen` ------------------ */	
	
public function updateProfile($nama, $alamat, $email, $telepon, $konsumen_id) {
        // fetching user by email

	$update = time();
	$sql = "UPDATE konsumen SET nama =?, alamat =?, email =?, nohp =?, updated_at = NOW() WHERE konsumen_id = ?";
	$stmt = $this->conn->prepare($sql);
        $stmt->bind_param("sssss", $nama, $alamat, $email, $telepon, $konsumen_id);

        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
		
        // Check for successful insertion
        if ($num_affected_rows > 0) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }
	

    /* ------------- `fungsi check email agar tidak dobel dobel` ------------------ */

    /**
     * Checking for duplicate user by email address
     * @param String $email email to check in db
     * @return boolean
     */
    private function isUserExists($email) {
        $stmt = $this->conn->prepare("SELECT id from konsumen WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }



    /**
     * Checking for duplicate user by email address
     * @param String $email email to check in db
     * @return boolean
     */
    public function CheckisUserExists($email) {
        $stmt = $this->conn->prepare("SELECT id from konsumen WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows;
    }


    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT konsumen_id, nama, alamat, nohp, email, api_key, status, created_at, last_login, updated_at FROM konsumen WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($konsumen_id, $nama, $alamat, $nohp, $email, $api_key, $status, $created_at, $last_login, $updated_at);
            $stmt->fetch();
            $user = array();
            
            // user is found
            $user["uid"] = $konsumen_id;
            $user["nama"] = $nama;
	    $user["alamat"] = $alamat;
	    $user["nohp"] = $nohp;
            $user["email"] = $email;
            $user["api_key"] = $api_key;
            $user["status"] = $status;
            $user["created_at"] = $created_at;
	    $user["last_login"] = $last_login;
            $user["updated_at"] = $updated_at;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

	 public function UpdateUserByEmailx($email) {
        $stmt = $this->conn->prepare("UPDATE last_login FROM konsumen WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($konsumen_id, $nama, $alamat, $nohp, $email, $api_key, $status, $created_at, $last_login, $updated_at);
            $stmt->fetch();
            $user = array();
            
            // user is found
            $user["uid"] = $konsumen_id;
            $user["nama"] = $nama;
	    $user["alamat"] = $alamat;
	    $user["nohp"] = $nohp;
            $user["email"] = $email;
            $user["api_key"] = $api_key;
            $user["status"] = $status;
            $user["created_at"] = $created_at;
	    $user["last_login"] = $last_logint;
            $user["updated_at"] = $updated_at;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }
	
	
    /**
     * Fetching user api key
     * @param String $user_id user id primary key in user table
     */
    public function getApiKeyById($user_id) {
        $stmt = $this->conn->prepare("SELECT api_key FROM konsumen WHERE id = ?");
        $stmt->bind_param("i", $user_id);
        if ($stmt->execute()) {
            // $api_key = $stmt->get_result()->fetch_assoc();
            // TODO
            $stmt->bind_result($api_key);
            $stmt->close();
            return $api_key;
        } else {
            return NULL;
        }
    }

     
    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getKonsumenId($api_key) {
        $stmt = $this->conn->prepare("SELECT id FROM konsumen WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $stmt->bind_result($user_id);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user_id;
        } else {
            return NULL;
        }
    }
    
    /**
     * Fetching konsumen id by api key
     * @param String $api_key user api key
     */
    public function getKonsumenUnikId($api_key) {
        $stmt = $this->conn->prepare("SELECT konsumen_id FROM konsumen WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $stmt->bind_result($konsumen_id);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $konsumen_id;
        } else {
            return NULL;
        }
    }

    /**
     * Fetching email by api key
     * @param String $api_key user api key
     */
    public function getKonsumenEmail($api_key) {
        $stmt = $this->conn->prepare("SELECT email FROM konsumen WHERE api_key = ?");

        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $stmt->bind_result($api_email);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $api_email;
        } else {
            return NULL;
        }
    }


    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $api_key user api key
     * @return boolean
     */
    public function isValidApiKey($api_key) {
        $stmt = $this->conn->prepare("SELECT id from konsumen WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function generateApiKey() {
        return sha1(md5(uniqid(rand(), true)));
    }

    /**
     * Generating random Unique MD5 String for konsumen id
     */

    private function generateUID() {
        return md5(uniqid(rand(), true));
    }
    
    public function checkbrute($email) {
    // Get timestamp of current time 
    $now = time();
 
    // All login attempts are counted from the past  hours. 
    $valid_attempts = $now - (1 * 60 * 60);
 
		$stmt = $this->conn->prepare("SELECT waktu FROM login_attemps WHERE email = ? AND waktu > ?");
        $stmt->bind_param('ss', $email, $valid_attempts);
 
        // Execute the prepared query. 
        $stmt->execute();
        $stmt->store_result();
 
        // If there have been more than 5 failed logins 
        if ($stmt->num_rows > 5) {
            return true;
            
        } else {
            return false;
        }
     
} 

 
    public function checkAttemp($email) {
    // Get timestamp of current time 
    $now = time();
 
    // All login attempts are counted from the past  hours. 
    $valid_attempts = $now - (1 * 60 * 60);
 
		$stmt = $this->conn->prepare("SELECT waktu FROM login_attemps WHERE email = ? AND waktu > ?");
        $stmt->bind_param('ss', $email, $valid_attempts);
 
        // Execute the prepared query. 
        $stmt->execute();
        $stmt->store_result();
 
        // If there have been more than 5 failed logins 
        if ($stmt->num_rows > 5) {
            return "6";
                   
        } else  if ($stmt->num_rows == 5) {
            return "5";
                  
        } else  if ($stmt->num_rows == 4) {
            return "4";
            
        } else  if ($stmt->num_rows == 3) {
            return "3";
                     
        } else  if ($stmt->num_rows == 2) {
            return "2";
                     
        } else  if ($stmt->num_rows == 1) {
            return "1";
                  
        } else {
            return false;
        }
}
 

public function loginFailed($email) {
	      
    $now = time();
	$sql = "INSERT INTO login_attemps(email, waktu) values(?, ?)";
    $stmt = $this->conn->prepare($sql);
    $stmt->bind_param("ss", $email, $now);
    $result = $stmt->execute();
    $stmt->close();	
		// Check for successful insertion
        if ($result) {		
            // User successfully inserted
             return TRUE;	
            } else {
            // Failed to create user
               return FALSE;
            }
 
    }
	


function generateHash($random_string) {
    if (defined("CRYPT_BLOWFISH") && CRYPT_BLOWFISH) {
        $salt = '$2y$11$' . substr(md5(uniqid(rand(), true)), 0, 22);
        return crypt($password, $salt);
    }
}

 public function getHash($password) {

     $salt = sha1(rand());
     $salt = substr($salt, 0, 10);
     $encrypted = password_hash($password.$salt, PASSWORD_DEFAULT);
     $hash = array("salt" => $salt, "encrypted" => $encrypted);

     return $hash;

}


public function verifyHash($password, $hash) {

    //return password_verify ($password, $hash);
    return password_hash ($password, $hash);
}

public function passwordResetRequest($konsumen_email){
 
	$user = array();

      //  $random_string = substr(str_shuffle(str_repeat("0123456789abcdefghijklmnopqrstuvwxyz", 6)), 0, 6);
      //  $hash = $this->getHash($random_string);
      //  $encrypted_temp_password = $hash["encrypted"];
      //  $salt = $hash["salt"];
        
        require_once 'PassHash.php';
        
        

    // Generating password hash
        $salt = "salat";
        $token = PassHash::resetpass_unique_salt();
        $encrypted_temp_password = PassHash::resetpass_hash($token, $salt);
        
	$tanggal = date("Y-m-d H:i:s");
 	$sql = "SELECT * FROM password_reset_request WHERE email = ?";
	$stmt = $this->conn->prepare($sql);
        $stmt->bind_param('s', $konsumen_email);
 
        $result = $stmt->execute();


       if($result){

        $stmt->store_result();
        $num_rows = $stmt->num_rows;
    	$stmt->close();
 
        if ($num_rows == 0){

 	$sql = "INSERT INTO password_reset_request(email, encrypted_temp_password, salt, created_at) values(?, ?, ?, ?)";
	$stmt = $this->conn->prepare($sql);
        $stmt->bind_param("ssss", $konsumen_email,$encrypted_temp_password,$salt, $tanggal);
 
        $insert_query = $stmt->execute();
    	$stmt->close();

            if ($insert_query) {

                              $user["email_user"] = $konsumen_email;
              $user["temppass_user"] = "$token";

                return $user;

            } else {

                return false;

            }


        } else {
 
	$sql = "UPDATE password_reset_request SET email =?, encrypted_temp_password=?, salt=?, created_at=?";
 	$stmt = $this->conn->prepare($sql);
        $stmt->bind_param('ssss', $konsumen_email,$encrypted_temp_password,$salt, $tanggal);
        $stmt->execute();
    	$update_query = $stmt->execute();
    	$stmt->close();

         if ($update_query) {
        
              $user["email_user"] = $konsumen_email;
              $user["temppass_user"] = "$token";
              
             return $user;

            } else {

   return false;

            }

        }
    } else {

        return false;
    }


 }


    public function resetPassword($email,$code,$password){
 
        $sql = 'SELECT password_sementara, salt, created_at FROM password_reset WHERE email = ?';
	$stmt = $this->conn->prepare($sql);

        $stmt->bind_param("s", $email);

        $stmt->execute();
 	$stmt->bind_result($encrypted_temp_password,$salt, $created_at);
	$stmt->store_result();
	$stmt->fetch();

  	$stmt->close();

//$verify=verifyHash($code.$salt,$encrypted_temp_password);
       // if ($verify) {
           // if ($this -> verifyHash($code.$salt,$encrypted_temp_password) ) {
        
        if (PassHash::check_password($password_hash, $encrypted_temp_password)) {
 
            $old = new DateTime($created_at);
            $now = new DateTime(date("Y-m-d H:i:s"));
            $diff = $now->getTimestamp() - $old->getTimestamp();

 
            if($diff < 180) {
                
			if ( $this->changePassByEmail($email, $password)) {

			$to = $email;
			$subject = "Pemberitahuan pembaruan password";
			$body = "<p>Password account anda berhasil diperbarui</p>
			<p> Jika anda tidak merasa memperbarui password anda atau memiliki account, maka abaikan saja email ini</p>";

			sentEmail($to,$subject,$body);

			return true;
		  
			} else {
			return false;

		  	  }

            } else {
 
                return false;
            }
        } else {
 
            return false;
        }
    }


	
public function changePassByEmail($email, $password_baru) {
        // fetching user by email
  // Generating password hash
        $password_hash = PassHash::hash($password_baru);

        // Generating API key
        $api_key = $this->generateApiKey();
		
	$update = time();
	$sql = "UPDATE konsumen SET password_hash =?, api_key =?, updated_at = NOW() WHERE email = ?";
	$stmt = $this->conn->prepare($sql);
        $stmt->bind_param("sss", $password_hash, $api_key,  $email);

        $stmt->execute();
        $num_affected_rows = $stmt->affected_rows;
		
        // Check for successful insertion
        if ($num_affected_rows > 0) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }
	
 

function sentEmail($to,$subject,$body){

			$mail = new Mail();
			$mail->setFrom(SITEEMAIL);
			$mail->addAddress($to);
			$mail->subject($subject);
			$mail->body($body);
			$mail->send();

if(!$mail->send()) {

   return $mail->ErrorInfo;

  } else {

    return true;

  }

}

function randStrGen($len){
    $result = "";
    $chars = "abcdefghijklmnopqrstuvwxyz$_?!-0123456789";
    $charArray = str_split($chars);
    for($i = 0; $i < $len; $i++){
	    $randItem = array_rand($charArray);
	    $result .= "".$charArray[$randItem];
    }
    return $result;
}



}
