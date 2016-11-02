<?php

class AccountModel{

    public static function getAccount($accId){

        $app = \Slim\Slim::getInstance();

        $sql = "SELECT * FROM account WHERE acc_id=:acc_id";
        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('acc_id'=>$accId));

        return $stmt->fetch();
    }

    public static function getAccountByAPISecretKey($secret, $activeKeyOnly = true){

        $app = \Slim\Slim::getInstance();

        $sql = "SELECT * FROM account_api_keys k
        JOIN account a ON k.acc_id=a.acc_id
        WHERE k.key_secret=:key_secret";

        if($activeKeyOnly){

            $sql .= " AND k.key_status='On' AND a.locked='No' AND a.deleted='No'";
        }

        $stmt = $app->db->prepare($sql);
        $stmt->execute(array('key_secret'=>$secret));

        return $stmt->fetch();
    }

}
