<?php


class PasswordModel{
    
   
public function ResetRequest($konsumen_email,$konsumen_id,$konsumen_nama){

        $app = \Slim\Slim::getInstance();
        $user = array();
        
        // Generating password hash
        $random = GeneratorModel::randStrGen(20);
        $konsumen_token = GeneratorModel::randStrGen(6);
        $hash = GeneratorModel::getHash($konsumen_token, $random);
        
        $password_sementara = $hash["encrypted"];
        $salt = $hash["salt"];
        
	$tanggal = date("Y-m-d H:i:s");
 	$sql = "SELECT * FROM password_reset WHERE email = :email";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$konsumen_email           
        ));

        if ($stmt->rowCount() == 0){

 	$sql = "INSERT INTO password_reset(email,user_id, password_sementara, salt, dibuat_pada) values(:email, :user_id, :password_sementara, :salt, :dibuat_pada)";
        // insert query        
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$konsumen_email,
            'user_id'=>$konsumen_id,
            'password_sementara'=>$password_sementara,
            'salt'=>$salt,
            'dibuat_pada'=>$tanggal
        ));

        if($stmt->rowCount() > 0)
            {
             
            KirimEmailModel::kirimReset($konsumen_email, $konsumen_nama,  $konsumen_token);

            } else {
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure";
      		BantuanModel::echoRespnse(200, $response);
                
            }

        } else {
 
        $sql = "UPDATE password_reset SET email =:email, user_id=:user_id, password_sementara=:password_sementara, salt=:salt, dibuat_pada=:dibuat_pada";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$konsumen_email,
            'user_id'=>$konsumen_id,
            'password_sementara'=>$password_sementara,
            'salt'=>$salt,
            'dibuat_pada'=>$tanggal
        ));
        
        if($stmt->rowCount() > 0)
            {
            
             KirimEmailModel::kirimReset($konsumen_email,$konsumen_nama,  $konsumen_token);

            } else {
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure";
      		BantuanModel::echoRespnse(200, $response);
            //return false;

            }

        } 

    }
    

    public function resetPassword($email,$code,$reset_password, $nama){
        
        $app = \Slim\Slim::getInstance();
 
        $sql = 'SELECT  password_sementara, user_id, salt, dibuat_pada FROM password_reset WHERE email = :email';

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$email           
        ));

	$reset = $stmt->fetch();
        
        $password_sementara = $reset["password_sementara"];
        $konsumen_id = $reset["user_id"];
     
        $salt = $reset["salt"];
        $dibuat_pada = $reset["dibuat_pada"];
        
        if (GeneratorModel::verifyHash($code.$salt, $password_sementara)) {
 
            $lampau = new DateTime($dibuat_pada);
            $sekarang = new DateTime(date("Y-m-d H:i:s"));
            $perbedaan_waktu = $sekarang->getTimestamp() - $lampau->getTimestamp();

            if($perbedaan_waktu < 180) {
 
		KirimEmailModel::ganti_password($konsumen_id, $reset_password);

            } else {
 
                return 2;
            }
            } else {
 
                return 3;
            }
    }

    /* ------------- `fungsi ganti password konsumen` ------------------ */	
	
public function ganti_password($konsumen_id, $password_baru) {
    
        $app = \Slim\Slim::getInstance();
        // fetching user by email
        // 
        // Generating password hash
        $konsumen_password = GeneratorModel::hash($password_baru);

        // Generating API key
        $konsumen_kunci_api = GeneratorModel::generateApiKey();
		
	$sql = "UPDATE konsumen SET password_hash =:konsumen_password, konsumen_kunci_api =:konsumen_kunci_api, konsumen_update_pada = NOW() WHERE konsumen_id = :konsumen_id";
        
        $stmt = $app->db->prepare($sql);
        $change=$stmt->execute(array(
            'konsumen_password'=>$konsumen_password,
            'konsumen_kunci_api'=>$konsumen_kunci_api,
            'konsumen_id'=>$konsumen_id,
        ));
		
        // Check for successful insertion
        if ($change) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }

}
