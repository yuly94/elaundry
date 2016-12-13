<?php


class PasswordModel{
    
   
public static function ResetRequest($kurir_email,$kurir_id,$kurir_nama){

        $app = \Slim\Slim::getInstance();
        //$user = array();
        
        // Generating password hash
        $random = GeneratorModel::randStrGen(20);
        $kurir_token = GeneratorModel::randStrGen(6);
        $hash = GeneratorModel::getHash($kurir_token, $random);
        
        $password_sementara = $hash["encrypted"];
        $salt = $hash["salt"];
        
	$tanggal = date("Y-m-d H:i:s");
 	$sql = "SELECT * FROM password_reset WHERE email = :email";
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$kurir_email           
        ));

        if ($stmt->rowCount() == 0){

 	$sql = "INSERT INTO password_reset(email,user_id, password_sementara, salt, dibuat_pada) "
                . "values(:email, :user_id, :password_sementara, :salt, :dibuat_pada)";

        // insert query        
   
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$kurir_email,
            'user_id'=>$kurir_id,
            'password_sementara'=>$password_sementara,
            'salt'=>$salt,
            'dibuat_pada'=>$tanggal
        ));

        if($stmt->rowCount() > 0)
            {
             
     
            $kirim_email = new KirimEmailModel();
            $kirim_email->lupaPassword($kurir_email,$kurir_nama,  $kurir_token);

            } else {
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure";
      		BantuanModel::echoRespnse(200, $response);
                
            }

        } else {
 
        $sql = "UPDATE password_reset SET email =:email, password_sementara=:password_sementara, "
                . "salt=:salt, diupdate_pada=:diupdate_pada WHERE user_id=:user_id";

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$kurir_email,
            'user_id'=>$kurir_id,
            'password_sementara'=>$password_sementara,
            'salt'=>$salt,
            'diupdate_pada'=>$tanggal
        ));
        
        if($stmt->rowCount() > 0)
            {
            
            $kirim_email = new KirimEmailModel();
            $kirim_email->lupaPassword($kurir_email,$kurir_nama, $kurir_token);

            } else {
                
		$response["error"] = "true";
      		$response["message"] = "Email Reset Password Failure x";
      		BantuanModel::echoRespnse(200, $response);
                //return false;
                
            }

        } 

    }
    

    public static function meresetPassword($kurir_email,$kode,$password_baru){
        
        $app = \Slim\Slim::getInstance();
 
        $sql = 'SELECT  password_sementara, user_id, salt, dibuat_pada, kurir_nama '
                . 'FROM password_reset pas JOIN kurir kur ON pas.user_id = kur.kurir_id '
                . 'WHERE pas.email = :email';
        
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array(
            'email'=>$kurir_email         
        ));

	$reset = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (GeneratorModel::verifyHash($kode.$reset["salt"], $reset["password_sementara"])) {
 
            $lampau = new DateTime($reset["dibuat_pada"]);
            $sekarang = new DateTime(date("Y-m-d H:i:s"));
            $perbedaan_waktu = $sekarang->getTimestamp() - $lampau->getTimestamp();

            if($perbedaan_waktu < 180) {
                
                if (PasswordModel::menggantiPassword($reset["user_id"], $password_baru)){
                    
                    $kirim_email = new KirimEmailModel();
                    $kirim_email->resetPassword($kurir_email, $reset["kurir_nama"]);
                   
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

    /* ------------- `fungsi ganti password kurir` ------------------ */	
	
public static function menggantiPassword($kurir_id, $password_baru) {
    
        $app = \Slim\Slim::getInstance();
        // fetching user by email
        // 
        // Generating password hash
        $kurir_password = GeneratorModel::hash($password_baru);

        // Generating API key
        $kurir_kunci_api = GeneratorModel::generateApiKey();
		
	$sql = "UPDATE kurir SET kurir_password =:kurir_password, "
                . "kurir_kunci_api =:kurir_kunci_api, kurir_update_pada = NOW(),"
                . "kurir_status_reset =:kurir_status_reset WHERE kurir_id = :kurir_id";
        //$sekarang = new DateTime(date("Y-m-d H:i:s"));
        $stmt = $app->db->prepare($sql);
        $mengganti=$stmt->execute(array(
            'kurir_password'=>$kurir_password,
            'kurir_kunci_api'=>$kurir_kunci_api, 
            //'kurir_update_pada'=>$sekarang,
            'kurir_status_reset'=>"berhasil di reset",
            'kurir_id'=>$kurir_id
            
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

