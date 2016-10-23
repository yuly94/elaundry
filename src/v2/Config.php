<?php

//set timezone
date_default_timezone_set('Asia/Jakarta');


/**
 * Database configuration
 */
define('DB_USERNAME', 'root');
define('DB_PASSWORD', '');
define('DB_HOST', 'localhost');
define('DB_NAME', 'laundry');

define('USER_CREATED_SUCCESSFULLY', 0);
define('USER_CREATE_FAILED', 1);
define('USER_ALREADY_EXISTED', 2);
define('EMAIL_IS_INVALID', 3);


//application address
define('DIR','http://yuly.besaba.com/elaundry/');
define('SITEEMAIL','noreply@yuly.besaba.com');


include('.././libs/phpmailer/mail.php');

try {

	//create PDO connection
	$db = new PDO("mysql:host=".DB_HOST.";dbname=".DB_NAME, DB_USERNAME, DB_PASSWORD);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

} catch(PDOException $e) {
	//show error
    echo '<p class="bg-danger">'.$e->getMessage().'</p>';
    exit;
}

//include the user class, pass in the database connection
//include('classes/user.php');

include('../.././libs/phpmailer/mail.php');
$user = new User($db);
?>
