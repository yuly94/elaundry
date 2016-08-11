<?php

class Login_Model extends Model
{
    public function __construct()
    {
        parent::__construct();
    }

    public function run()
    {
        $sth = $this->db->prepare("SELECT userid, username, role FROM users WHERE 
                username = :username AND password = :password");
        $sth->execute(array(
            ':username' => $_POST['username'],
            ':password' => Hash::create('sha256', $_POST['password'], HASH_PASSWORD_KEY)
        ));
        
        $data = $sth->fetch();
        
        $count =  $sth->rowCount();
        if ($count > 0) {
            // login
           
            Session::init();
            Session::set('role', $data['role']);
            Session::set('username', $data['username']);
            Session::set('loggedIn', true);
            Session::set('userid', $data['userid']);
            echo 'correct';
           // header('location: ../dashboard');
            
        } else {
            echo 'wrong';
           // header('location: ../login');

        }
        
        
        
    }
    
}