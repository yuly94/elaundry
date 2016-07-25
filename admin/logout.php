<?php 
     
              require 'includes/header.php'; 

if (isset($_SESSION['user_session'])) {
    session::destroy('user_session');

    // We also unset the cookie
    if( isset($_COOKIE['membership']) )
    {
        setcookie('membership', '', time() - 3600, '/', '', false, true);
    }

    // Redirect with header()
    if (!headers_sent()){    
        header("location:index.php");

    }else{  
        // if header() is already sent use javascript to redirect
        echo '<script type="text/javascript">';
        echo 'window.location.href="index.php";';
        echo '</script>';
        echo '<noscript>';
        echo '<meta http-equiv="refresh" content="0;url='.$url.'" />';
        echo '</noscript>';
    }

}

        require 'includes/footer.php'; 
        
        
        
    ?>