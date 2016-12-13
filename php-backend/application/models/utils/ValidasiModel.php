<?php


class ValidasiModel{
     
    /**
     * Validating email address
     */
    function validasiEmail($email) {
        $app = \Slim\Slim::getInstance();
        if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
            $response["error"] = true;
            $response["message"] = 'Email address is not valid';
            BantuanModel::echoRespnse(400, $response);
            $app->stop();
        }
    }

    /**
     * Validating nama
     */
    function validasiNama($nama) {

        if (empty($nama)) {

        $response["error"] = true;
        $response["message"] = 'Please enter your full name.';

        BantuanModel::echoRespnse(400, $response);

        } else if (strlen($name) < 3) {

        $response["error"] = true;
        $response["message"] = 'Name must have atleat 3 characters.';

        BantuanModel::echoRespnse(400, $response);

      } else if (!preg_match("/^[a-zA-Z ]+$/",$name)) {

        $response["error"] = true;
        $response["message"] = 'Name must contain alphabets and space.';   

        BantuanModel::echoRespnse(400, $response);

      }
    }

    /**
    * Validating password
    */

    public function validasiPassword($password){

       // password validation
        if (empty($password)){

        $response["error"] = true;
        $response["message"] ="Please enter password.";

        } else if(strlen($password) < 6) {

        $response["error"] = true;
        $response["message"] = 'Password must have atleast 6 characters.';   

        BantuanModel::echoRespnse(400, $response);
        }
    }

    /* ------------- `fungsi check email agar tidak dobel dobel` ------------------ */

    /**
     * Checking for duplicate user by email address
     * @param String $konsumen_email email to check in db
     * @return boolean
     */
    public function isUserExists($konsumen_email) {
        
        $app = \Slim\Slim::getInstance();
        
        $sql = "SELECT id , konsumen_nama, konsumen_id from konsumen WHERE konsumen_email = :konsumen_email";
             
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('konsumen_email'=>$konsumen_email));
        
        $res=$stmt->fetch(PDO::FETCH_ASSOC);
        
        if($stmt->rowCount() > 0)
            {

        $konsumen["konsumen_nama"] = $res['konsumen_nama'];
        $konsumen["konsumen_id"] = $res['konsumen_id'];

            return $konsumen;
        } else {
            return FALSE;
        }  
    }
}
