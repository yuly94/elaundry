<?php
include_once 'register.inc.php';
include_once 'functions.php';
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Secure Login: Registration Form</title>
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
        <!-- Registration form to be output if the POST variables are not
        set or if the registration script caused an error. -->
        <h3 class="flow-text" style="text-align:center;">Register with us</h3>
        <?php
        if (!empty($error_msg)) {
            echo $error_msg;
        }
        ?>
    <div class="container">
        <form action="<?php echo esc_url($_SERVER['PHP_SELF']); ?>" method="post" name="registration_form">
        <div class="col s12 m6">
              <div class="card  darken-1">
                <div class="card-content waves-effect waves-block waves-light ">
                  <span class="card-title activator">Click to See the Registration Rules</span>
                </div>
                <div class="card-reveal">
                        <span class="card-title grey-text text-darken-4">Registration Rules<i class="material-icons right">close</i></span>
                        <ul class="collection">
                            <li class="collection-item">Usernames may contain only digits, upper and lowercase letters and underscores</li>
                            <li class="collection-item">Emails must have a valid email format</li>
                            <li class="collection-item">Passwords must be at least 6 characters long</li>
                            <li class="collection-item">Passwords must contain
                            <ul>
                                <li class="collection-item">At least one uppercase letter (A..Z)</li>
                                <li class="collection-item">At least one lowercase letter (a..z)</li>
                                <li class="collection-item">At least one number (0..9)</li>
                            </ul>
                            </li>
                            <li class="collection-item">Your password and confirmation must match exactly</li>
                        </ul>
                </div>
             <div class="card-action">
              <div class="row">
                <div class="input-field col s12">
                    <input type="text" name="username" id="username" class="validate" />
                    <label for="username">Enter Username</label>
                </div>
              </div>
               <div class="row">
                <div class="input-field col s12">
                    <input type="text" name="email" id="email" class="validate"/>
                    <label for="email">Enter Email</label>
                </div>
              </div>
              <div class="row">
                <div class="input-field col s12">
                    <input type="password" name="password" id="password" class="validate"/>
                    <label for="password">Enter Password</label>
                </div>
              </div>
              <div class="row">
                <div class="input-field col s12">
                    <input type="password" name="confirmpwd" id="confirmpwd" class="validate"/>
                    <label for="comfirmpwd">Confirm Password</label>
                </div>
              </div>
              <input type="button" value="Register" onclick="return regformhash(this.form,this.form.username,this.form.email,this.form.password,this.form.confirmpwd);" class="btn waves-effect waves-light"/> 
        </form>
        <p class="text-flow">Return to the <a href="login.php">login page</a>.</p>
    </div>
        

        <script>
  $( document ).ready(function(){
    $('#email').val('');
    $('#password').trigger('autoresize');
    $(".button-collapse").sideNav();
  })
</script>
    </body>
</html>