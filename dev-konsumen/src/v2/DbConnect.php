<?php

/**
 * Handling database connection
 *
 * @author Wachid.sst
 * @link URL Tutorial link
 */
class DbConnect {

    private $conn;

    function __construct() {        
    }

    /**
     * Establishing database connection
     * @return database connection handler
     */
    function connect() {
        include_once dirname(__FILE__) . '/Config.php';
/* 
        // Connecting to mysql database
        $this->conn = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);

        // Check for database connection error
        if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }

		  $dbhost = "localhost";
    $dbuser = "root";
    $dbpass = "";
    $dbname = "register_login";
 */
    $mysql_conn_string = "mysql:host=DB_HOST;dbname=DB_NAME";
    $dbConnection = new PDO($mysql_conn_string, DB_USERNAME, DB_PASSWORD);
    $dbConnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	
    return $dbConnection;
		
        // returing connection resource
       // return $this->conn;
    }

}

?>
