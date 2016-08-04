<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
class Auth
{
    public static function handleLogin()
    {
        @session_start();
        $logged = Session::get('loggedIn');
        if ($logged==false) {
            Session::destroy();
            header('location: ../login');
            exit;
		}
        
    }
}


