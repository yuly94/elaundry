<?php 
include("config.php");
include('class/userClass.php');
$userClass = new userClass();

$errorMsgReg='';
$errorMsgLogin='';
if (!empty($_POST['loginSubmit'])) 
{
$usernameEmail=$_POST['usernameEmail'];
$password=$_POST['password'];
 if(strlen(trim($usernameEmail))>1 && strlen(trim($password))>1 )
   {
    $uid=$userClass->userLogin($usernameEmail,$password);
    if($uid)
    {
        $url=BASE_URL.'home.php';
        header("Location: $url");
    }
    else
    {
        $errorMsgLogin="Please check login details.";
    }
   }
}

 

?>

<style>
.errorMsg{color: #cc0000;margin-bottom: 10px}
</style>

<!DOCTYPE html>
<html lang="en">



  <!-- #Head. -->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <title>Materialize Landing Page</title>
          	<link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <link type="text/css"  href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" type="text/css" rel="stylesheet" media="screen,projection"/>
     <link href="https://yuly94.github.io/assets/css/login-style.css"       type="text/css" rel="stylesheet" media="screen,projection"/>
 
     <script src="http://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>


    
    <link rel="stylesheet" href="css/normalize.css">

    
        <link rel="stylesheet" href="css/awan-style.css">

    
    
 
  </head>






  <!-- #Let's Begin -->
  <body>





    <!-- #Dropdown Structure (Must Be Before Nav) -->
    <ul id='dropdown1' class='dropdown-content'>
      <li><a id="cta__why" href="#!">Why?</a></li>
      <li><a class="modal-trigger" href="#modal-price">Pricing</a></li>
      <li><a id="cta__faq" href="#!">FAQ</a></li>
      <li class="divider">&nbsp;</li>
      <li><a id="cta__nav" href="#!"><strong>Hire Me</strong></a></li>
    </ul>




    <!-- #Main Nav (Must Be After Dropdown Structure) -->
    <nav role="navigation">
      <div class="container">
        <div class="nav-wrapper">
          <ul class="right">
            <li><a class="modal-trigger" href="#modal-about">
                About
            </a></li>
            <!-- Dropdown Trigger ** -->
            <li> <a class="dropdown-button" href="#!" data-activates="dropdown1">
                Hire Me
                <i class="mdi-navigation-arrow-drop-down right">&nbsp;</i>
              </a> </li>
          </ul>
        </div>
      </div> 
    </nav>



 
 <script src='js/epmrgo.js'></script>

            <div id="Clouds">
  <div class="Cloud Foreground"></div>
  <div class="Cloud Background"></div>
  <div class="Cloud Foreground"></div>
  <div class="Cloud Background"></div>
  <div class="Cloud Foreground"></div>
  <div class="Cloud Background"></div>
  <div class="Cloud Background"></div>
  <div class="Cloud Foreground"></div>
  <div class="Cloud Background"></div>
  <div class="Cloud Background"></div>
<!--  <svg viewBox="0 0 40 24" class="Cloud"><use xlink:href="#Cloud"></use></svg>-->
</div>

    <!-- #Hero Section -->
    <div class="section section__hero" id="index-banner">
    
    
      <div class="container">
      

      
                <div class="row">
          <div class="col s12 m5 section__login" id="login">
            
            
            <form class=" card z-depth-3" method="post" action="" name="login">
            <div class="row">
  <div class="input-field | col s12">
<input type="text" name="usernameEmail" autocomplete="off" />
  <label for="usernameEmail">Email <span class="required">(required)</span></label>
              </div>
              </div>
              <div class="row">
 <div class="input-field | col s12">
<input type="password" name="password" autocomplete="off"/>
<label for="password">Password <span class="optional" ></span></label>
</div>
</div>
<div class="errorMsg"><?php echo $errorMsgLogin; ?></div>
<input type="submit" class="btn btn-large tooltipped" data-position="bottom" data-delay="50" data-tooltip="Silahkan tekan untuk masuk" name="loginSubmit" value="Login">
<br></br>
 <a id="cta__main" class =" tooltipped" data-position="bottom" data-delay="50" data-tooltip="Silahkan tekan jika anda lupa password" href="#!" >Lupa Password &raquo;</a>
</form>
          
          
         
          <!-- End of the form -->
          </div>
          
          
              <div class="col s12 m7 gorilla"> </div>
              
                      <div class="row center">



  <a id="cta__main" href="signup.php" class="btn btn-large tooltipped" data-position="bottom" data-delay="50" data-tooltip="Silahkan tekan untuk mendaftar" >Daftar sekarang &raquo;</a>
        </div>
        </div>

      </div>
    </div>




    <!-- #About Me Modal -->
    <div id="modal-about" class="modal">
      <div class="modal-content center">
        <h4>About</h4>
        <p>Halaman login untuk admin </p>
        <small>
          <a href="#" class="btn__modal | modal-action modal-close">
            <i class="large mdi-navigation-close flow-text">&nbsp;</i>
          </a>
        </small>
      </div>
    </div>
    
        <!-- #About Me Modal -->
    <div id="modal-price" class="modal">
      <div class="modal-content center">
        


   <!-- #Pricing -->
    <div id="pricing" class="section section__pricing | center"> 
      <div class="row">
         <h2 class="center">Pricing</h2>
        <div class="col s12 m12 l6"> 
          <div class="card">
            <h4>Option #1</h4>
            <i class="small mdi-action-done-all">&nbsp;</i>
            <p class="bold">Information about this option.</p>
            <a class="btn btn-large | activator" href="#!">Pricing</a>
            <!-- Hidden Pricing/Info Panel -->
            <div class="card-reveal">
              <span class="card-title"> <i class="mdi-navigation-close right">&nbsp;</i><br />More information.</span>
              <p>Beep.</p>
              <p class="price">Beep.</p>
            </div>
          </div>
        </div>
        <div class="col s12 m12 l6"> 
          <div class="card">
           <h4>Option #1</h4>
            <i class="small mdi-action-done">&nbsp;</i>
            <p class="bold">Information here about this option.</p>
            <a class="btn btn-large | activator" href="#!">Pricing</a>
            <!-- Hidden Pricing/Info Panel -->
            <div class="card-reveal">
              <span class="card-title"> <i class="mdi-navigation-close right"></i><br /> More information.</span>
              <p> Beep. </p>
              <p class="price">Beep.</p>
            </div>
          </div>
        </div>
      </div>          
    </div> 


        


        <small>
          <a href="#" class="btn__modal | modal-action modal-close">
            <i class="large mdi-navigation-close flow-text">&nbsp;</i>
          </a>
        </small>
      </div>
    </div>
    
 
 



      <!--  #Scripts -->
      <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
      <script src="https://yuly94.github.io/assets/js/landingpage-init.js"></script>
      

    </body>
  </html>

