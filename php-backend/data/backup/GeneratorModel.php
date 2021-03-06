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
    public function generateApiKey() {
        return sha1(md5(uniqid(rand(), true)));
    }

    /**
     * Generating random Unique MD5 String for konsumen id
     */

    public function generateUID() {
        return md5(uniqid(rand(), true));
    }
    

    
     public function getHash($password, $salt) {
     $encrypted = password_hash($password.$salt, PASSWORD_DEFAULT);
     $hash = array("salt" => $salt, "encrypted" => $encrypted);
     return $hash;
}
public function verifyHash($password, $hash) {
    return password_verify ($password, $hash);
}
    
    
    public function generateHash($random_string) {
    if (defined("CRYPT_BLOWFISH") && CRYPT_BLOWFISH) {
        $salt = '$2y$11$' . substr(md5(uniqid(rand(), true)), 0, 22);
        return crypt($password, $salt);
        }
    }

 
    public function randStrGen($len){
    $result = "";
    $chars = "abcdefghijklmnopqrstuvwxyz0123456789";
    $charArray = str_split($chars);
    for($i = 0; $i < $len; $i++){
	    $randItem = array_rand($charArray);
	    $result .= "".$charArray[$randItem];
    }
    return $result;
}


    
}
