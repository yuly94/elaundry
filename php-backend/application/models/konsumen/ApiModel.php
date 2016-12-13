<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class ApiModel{
    
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