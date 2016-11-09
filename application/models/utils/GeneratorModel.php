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
    
    
    public static function generateHash($password) {
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

    public static function openSslGen($lenght, $crypto_strong = true){

    $bytes = openssl_random_pseudo_bytes($lenght, $crypto_strong);
    $hex   = bin2hex($bytes);
    return $hex;
    
    }
    
    //Generate XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX style unique id, 
    //(8 letters)-(4 letters)-(4 letters)-(4 letters)-(12 letters)
    public static function notaGen() { 
    $s = strtoupper(md5(uniqid(rand(),true))); 
    $guidText = 
        substr($s,0,8) . '-' . 
        substr($s,8,4) . '-' . 
        substr($s,12,4). '-' . 
        substr($s,16,4). '-' . 
        substr($s,20); 
    return $guidText;
}


    public static function keyGen(){

    // The length we want the unique reference number to be  
    $unique_ref_length = 8;  

    // A true/false variable that lets us know if we've  
    // found a unique reference number or not  
    $unique_ref_found = false;  

    // Define possible characters.  
    // Notice how characters that may be confused such  
    // as the letter 'O' and the number zero don't exist  
    $possible_chars = "23456789BCDFGHJKMNPQRSTVWXYZ";  

    // Until we find a unique reference, keep generating new ones  
    while (!$unique_ref_found) {  

        // Start with a blank reference number  
        $unique_ref = "";  

        // Set up a counter to keep track of how many characters have   
        // currently been added  
        $i = 0;  

        // Add random characters from $possible_chars to $unique_ref   
        // until $unique_ref_length is reached  
        while ($i < $unique_ref_length) {  

            // Pick a random character from the $possible_chars list  
            $char = substr($possible_chars, mt_rand(0, strlen($possible_chars)-1), 1);  

            $unique_ref .= $char;  

            $i++;  

        }  

        // Our new unique reference number is generated.  
        // Lets check if it exists or not  
        $query = "SELECT `order_ref_no` FROM `orders` 
                  WHERE `order_ref_no`='".$unique_ref."'";  
        $result = mysql_query($query) or die(mysql_error().' '.$query);  
        if (mysql_num_rows($result)==0) {  

            // We've found a unique number. Lets set the $unique_ref_found  
            // variable to true and exit the while loop  
            $unique_ref_found = true;  

        }  
  
    }  
    echo 'Our unique reference number is: '.$unique_ref; 
    }
    
    
}
