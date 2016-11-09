<?php


class PasswordModel{
    
   
public static function ResetRequest($konsumen_email,$konsumen_id,$konsumen_nama){

        $app = \Slim\Slim::getInstance();
        //$user = array();
        
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

 	$sql = "INSERT INTO password_reset(email,user_id, password_sementara, salt, dibuat_pada) "
                . "values(:email, :user_id, :password_sementara, :salt, :dibuat_pada)";

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
             
     
            $kirim_email = new KirimEmailModel();
            $kirim_email->lupaPassword($konsumen_email,$konsumen_nama,  $konsumen_token);

            } else {
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure";
      		BantuanModel::echoRespnse(200, $response);
                
            }

        } else {
 
        $sql = "UPDATE password_reset SET email =:email,  password_sementara=:password_sementara, "
                . "salt=:salt, dibuat_pada=:dibuat_pada WHERE user_id=:user_id";

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
            
            $kirim_email = new KirimEmailModel();
            $kirim_email->lupaPassword($konsumen_email,$konsumen_nama,  $konsumen_token);

            } else {
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure";
      		BantuanModel::echoRespnse(200, $response);
            //return false;

            }

        } 

    }
    

    public static function meresetPassword($konsumen_email,$kode,$password_baru, $nama){
        
        $app = \Slim\Slim::getInstance();
 
        $sql = 'SELECT  password_sementara, user_id, salt, dibuat_pada FROM password_reset WHERE email = :email';

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$konsumen_email         
        ));

	$reset = $stmt->fetch();
        
        $password_sementara = $reset["password_sementara"];
        $konsumen_id = $reset["user_id"];
     
        $salt = $reset["salt"];
        $dibuat_pada = $reset["dibuat_pada"];
        
        if (GeneratorModel::verifyHash($kode.$salt, $password_sementara)) {
 
            $lampau = new DateTime($dibuat_pada);
            $sekarang = new DateTime(date("Y-m-d H:i:s"));
            $perbedaan_waktu = $sekarang->getTimestamp() - $lampau->getTimestamp();

            if($perbedaan_waktu < 180) {
                
                if (PasswordModel::menggantiPassword($konsumen_id, $password_baru)){
                    $kirim_email = new KirimEmailModel();
                    $kirim_email->resetPassword($konsumen_email);
                   
                } else {
                    
                    return 1;
                }
       
                } else {
 
                    return 2;
                }
                } else {
 
                    return 3;
            }
    }

    /* ------------- `fungsi ganti password konsumen` ------------------ */	
	
public static function menggantiPassword($konsumen_id, $password_baru) {
    
        $app = \Slim\Slim::getInstance();
        // fetching user by email
        // 
        // Generating password hash
        $konsumen_password = GeneratorModel::hash($password_baru);

        // Generating API key
        $konsumen_kunci_api = GeneratorModel::generateApiKey();
		
	$sql = "UPDATE konsumen SET konsumen_password =:konsumen_password, konsumen_kunci_api =:konsumen_kunci_api, konsumen_update_pada = NOW(), konsumen_status_reset =:konsumen_status_reset WHERE konsumen_id = :konsumen_id";
        //$sekarang = new DateTime(date("Y-m-d H:i:s"));
        $stmt = $app->db->prepare($sql);
        $mengganti=$stmt->execute(array(
            'konsumen_password'=>$konsumen_password,
            'konsumen_kunci_api'=>$konsumen_kunci_api, 
            //'konsumen_update_pada'=>$sekarang,
            'konsumen_status_reset'=>"berhasil di reset",
            'konsumen_id'=>$konsumen_id
            
        ));
		
        // Check for successful insertion
        if ($mengganti) {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }

}

