<?php

/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 *
 * @author Yuly
 * @link URL Tutorial link
 */
 
 //require 'libs/PHPMailer/PHPMailerAutoload.php';
 
class DbHandlerAuth {

    private $conn;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /* ------------- `konsumen` table method ------------------ */

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
	//validateEmail($email);
        
        // First check if user already existed in db
        if (!$this->isUserExists($email)) {
            // Generating password hash
            $password_hash = PassHash::hash($password);

            // Generating API key
            $api_key = $this->generateApiKey();
	    // Generating UID key
	    $konsumen_id = $this->generateUID();
            // insert query
            $stmt = $this->conn->prepare("INSERT INTO konsumen(konsumen_id, nama, alamat, nohp, email, password_hash, api_key, created_at, status) values(?, ?, ?, ?, ?, ?, ?, NOW(),1)");
            $stmt->bind_param("sssssss", $konsumen_id, $nama, $alamat, $nohp, $email, $password_hash, $api_key);

            $result = $stmt->execute();

            $stmt->close();

            // Check for successful insertion
            if ($result) {
                // User successfully inserted
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

            $stmt->close();

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
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT konsumen_id, nama, alamat, nohp, email, api_key, status, created_at, updated_at FROM konsumen WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($konsumen_id, $nama, $alamat, $nohp, $email, $api_key, $status, $created_at, $updated_at);
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
            $user["updated_at"] = $updated_at;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

	 public function UpdateUserByEmail($email) {
        $stmt = $this->conn->prepare("UPDATE last_login FROM konsumen WHERE email = ?");
        $stmt->bind_param("s", $email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($konsumen_id, $nama, $alamat, $nohp, $email, $api_key, $status, $created_at, $updated_at);
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
     * Fetching user id by api key
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
	 
        $stmt = $this->conn->prepare("INSERT INTO login_attemps(email, waktu) values(?, ?)");
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
	
	
	
public function changePassword($email, $old_password, $new_password) {

  $db = $this -> db;

  if (!empty($email) && !empty($old_password) && !empty($new_password)) {

    if(!$db -> checkLogin($email, $old_password)){

      $response["result"] = "failure";
      $response["message"] = 'Invalid Old Password';
      return json_encode($response);

    } else {


    $result = $db -> changePassword($email, $new_password);

      if($result) {

        $response["result"] = "success";
        $response["message"] = "Password Changed Successfully";
        return json_encode($response);

      } else {

        $response["result"] = "failure";
        $response["message"] = 'Error Updating Password';
        return json_encode($response);

      }

    } 
  } else {

      return $this -> getMsgParamNotEmpty();
  }

}

public function resetPasswordRequest($email){

  $db = $this -> db;

  if ($db -> checkUserExist($email)) {

    $result =  $db -> passwordResetRequest($email);

    if(!$result){

      $response["result"] = "failure";
      $response["message"] = "Reset Password Failure";
      return json_encode($response);

    } else {

      $mail_result = $this -> sendEmail($result["email"],$result["temp_password"]);

      if($mail_result){

        $response["result"] = "success";
        $response["message"] = "Check your mail for reset password code.";
        return json_encode($response);

      } else {

        $response["result"] = "failure";
        $response["message"] = "Reset Password Failure";
        return json_encode($response);
      }
    }


  } else {

    $response["result"] = "failure";
    $response["message"] = "Email does not exist";
    return json_encode($response);

  }

}

public function resetPassword($email,$code,$password){

  $db = $this -> db;

  if ($db -> checkUserExist($email)) {

    $result =  $db -> resetPassword($email,$code,$password);

    if(!$result){

      $response["result"] = "failure";
      $response["message"] = "Reset Password Failure";
      return json_encode($response);

    } else {

      $response["result"] = "success";
      $response["message"] = "Password Changed Successfully";
      return json_encode($response);

    }


  } else {

    $response["result"] = "failure";
    $response["message"] = "Email does not exist";
    return json_encode($response);

  }

}
	
	
	public function sendEmail($email,$temp_password){

  $mail = $this -> mail;
  $mail->isSMTP();
  $mail->Host = 'smtp.gmail.com';
  $mail->SMTPAuth = true;
  $mail->Username = 'your.email@gmail.com';
  $mail->Password = 'Your Password';
  $mail->SMTPSecure = 'ssl';
  $mail->Port = 465;
 
  $mail->From = 'your.email@gmail.com';
  $mail->FromName = 'Your Name';
  $mail->addAddress($email, 'Your Name');
 
  $mail->addReplyTo('your.email@gmail.com', 'Your Name');
 
  $mail->WordWrap = 50;
  $mail->isHTML(true);
 
  $mail->Subject = 'Password Reset Request';
  $mail->Body    = 'Hi,<br><br> Your password reset code is <b>'.$temp_password.'</b> . This code expires in 120 seconds. Enter this code within 120 seconds to reset your password.<br><br>Thanks,<br>Learn2Crack.';

  if(!$mail->send()) {

   return $mail->ErrorInfo;

  } else {

    return true;

  }

}

public function sendPHPMail($email,$temp_password){

  $subject = 'Password Reset Request';
  $message = 'Hi,\n\n Your password reset code is '.$temp_password.' . This code expires in 120 seconds. Enter this code within 120 seconds to reset your password.\n\nThanks,\nLearn2Crack.';
  $from = "your.email@example.com";
  $headers = "From:" . $from;

  return mail($email,$subject,$message,$headers);

}



}
