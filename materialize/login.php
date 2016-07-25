<?php
include_once 'db.php';
include_once 'functions.php'; 
#sec_session_start();
session_start();
if (login_check($db) == true) {
    $logged = 'in';
} else {
    $logged = 'out';
}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>Secure Login: Log In</title>
    
        <script type="text/JavaScript" src="js/sha512.js"></script> 
        <script type="text/JavaScript" src="js/forms.js"></script> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css" media="screen,projection">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    </head>
    <body>
      <!--Import jQuery before materialize.js-->
      <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
    <nav>
    <div class="nav-wrapper">
      <a href="index.php" class="brand-logo">Logo</a>
      <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
      <ul class="right hide-on-med-and-down">
        <li><a href="login.php">Login</a></li>
      </ul>
      <ul class="side-nav" id="mobile-demo">
        <li><a href="login.php">Login</a></li>
      </ul>
    </div>
  </nav> 
        <?php
        if (isset($_GET['error'])) {
            echo '<p class="error">Error Logging In!</p>';
        }
        ?> 
        <div class="container">
           <form action="process_login.php" method="post" name="login_form"> 
            <div class="col s12 m6">
              <div class="card  darken-1">
                <div class="card-content ">
                  <span class="card-title">Login</span>
                </div>
             <div class="card-action">
              <div class="row">
                <div class="input-field col s12">
                    <input type="text" name="email" id="email" class="validate"/>
                    <label for="title">Enter Email</label>
                </div>
              </div>
               <div class="row">
                <div class="input-field col s12">
                    <input type="password" name="password" id="password" class="validate"/>
                    <label for="title">Enter Password</label>
                </div>
              </div>
            <input type="button" value="Login" onclick="formhash(this.form, this.form.password);" class="waves-effect waves-light btn" /> 
            <a href="register.php" class="btn waves-effect waves-light">Register</a>

            <?php
            if (login_check($db) == true) {
               echo '<p class=\"flow-text\">Currently logged ' . $logged . ' as ' . htmlentities($_SESSION['username']) . '.</p>';
               echo '<p class=\"flow-text\">Do you want to change user? <a href="logout.php">Log out</a>.</p>';
              } else {
                        echo '<p class=\"flow-text\">Currently logged ' . $logged . '.</p>';
                        #echo "<p>If you don't have a login, please <a href='register.php'>register</a></p>";
                      }
                ?>      
                </div>
              </div>
            </div>
        </form>
        
                             
            
        

<script>
  $( document ).ready(function(){
    $('#email').val('');
    $('#password').trigger('autoresize');
    $(".button-collapse").sideNav();
  })
</script>
    </body>
</html>
