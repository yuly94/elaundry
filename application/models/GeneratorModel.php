<?php


class GeneratorModel{
    
    
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
    
    
    
    
    public function generateHash($random_string) {
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
