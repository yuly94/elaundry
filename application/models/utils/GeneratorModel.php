<?php


class GeneratorModel{
    
    
    // blowfish
    private static $algo = '$2a';
    // cost parameter
    private static $cost = '$10';
    
            // mainly for internal use
    public static function unique_salt() {
        return substr(sha1(mt_rand()), 0, 22);
    }

    // this will be used to generate a hash
    public static function hash($password) {

        return crypt($password, self::$algo .
                self::$cost .
                '$' . self::unique_salt());
    }
    
    
    /**
     * Generating random Unique MD5 String for user Api key
     */
    public static function generateApiKey() {
        return sha1(md5(uniqid(rand(), true)));
    }

    /**
     * Generating random Unique MD5 String for konsumen id
     */

    public static function generateUID() {
        return md5(uniqid(rand(), true));
    }
    

    
    public static function getHash($password, $salt) {
     $encrypted = password_hash($password.$salt, PASSWORD_DEFAULT);
     $hash = array("salt" => $salt, "encrypted" => $encrypted);
     return $hash;
}

    public static function verifyHash($password, $hash) {
        return password_verify ($password, $hash);
}
    
    
    public static function generateHash($random_string) {
    if (defined("CRYPT_BLOWFISH") && CRYPT_BLOWFISH) {
        $salt = '$2y$11$' . substr(md5(uniqid(rand(), true)), 0, 22);
        return crypt($password, $salt);
        }
    }

 
    public static function randStrGen($len){
    $result = "";
    $chars = "abcdefghijklmnopqrstuvwxyz0123456789";
    $charArray = str_split($chars);
    for($i = 0; $i < $len; $i++){
	    $randItem = array_rand($charArray);
	    $result .= "".$charArray[$randItem];
    }
    return $result;
}


    public static function acakan_kunci($password){

                // Generating password hash
            $password_hash = GeneratorModel::hash($password);

            // Generating API key
            $api_key = GeneratorModel::generateApiKey();
        
	    // Generating UID key
	    $konsumen_id = GeneratorModel::generateUID();

            $acakan = array(
                "password_hash" => $password_hash, 
                "api_key" => $api_key, 
                "konsumen_id" => $konsumen_id);
            
        return $acakan ($password_hash, $api_key, $konsumen_id);
    
    }
}
