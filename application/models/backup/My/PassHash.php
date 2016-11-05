<?php

namespace My;

class PassHash {

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

    // this will be used to compare a password against a hash
    public static function check_password($hash, $password) {
        $full_salt = substr($hash, 0, 29);
        $new_hash = crypt($password, $full_salt);
        return ($hash == $new_hash);
    }

    
        // mainly for internal use
    public static function resetpass_unique_salt() {
        
        $random_string = substr(str_shuffle(str_repeat("0123456789abcdefghijklmnopqrstuvwxyz", 6)), 0, 6);
      //	$hash = $this->getHash($random_string);
        return $random_string;
    }
    

    // this will be used to generate a hash
    public static function resetpass_hash($token, $salt) {

        return crypt($token, self::$algo .
                self::$cost .
                '$' . $salt);
    }

    // this will be used to compare a password against a hash
    public static function resetpass_check_password($hash, $password) {
        $full_salt = substr($hash, 0, 29);
        $new_hash = crypt($password, $full_salt);
        return ($hash == $new_hash);
    }
    
    
}

