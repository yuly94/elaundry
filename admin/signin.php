<!DOCTYPE html>
<html lang="en">


  <!-- #Head. -->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <title>Materialize Landing Page</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" type="text/css" rel="stylesheet" media="screen,projection"/>
     <link href="https://yuly94.github.io/assets/css/login-style.css"       type="text/css" rel="stylesheet" media="screen,projection"/>
      <link href="https://yuly94.github.io/assets/css/weather-style.css"       type="text/css" rel="stylesheet" media="screen,projection"/>
 
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




    <!-- #Hero Section -->
    <div class="section section__hero" id="index-banner">
      <div class="container">
                <div class="row">
          <div class="col s12 m5 section__login">
            
          
             <form class=" card z-depth-3" action="" method="" id="form-element" >
           
            <div class="row">
              <div class="input-field | col s12">
                <input id="email" type="email" class="validate" name="email" >
                <label for="email">Email <span class="required">(required)</span></label>
              </div>
            </div>
            
              <div class="row">
              <div class="input-field | col s12">
                <input id="company" type="text" name="company">
                <label for="company">Company <span class="optional" >(optional)</span></label>
              </div>
            </div>
            
            <div class="row">
              <div class="col s12">
                <button class="btn btn-large" type="submit" id="send_mail">Submit</button>
              </div>
              <span id="email_message"></span>
            </div>
            
 <a id="lupa_pass" class"lupa_pass" href="#!" >Lupa Password &raquo;</a>

            
          </form>
          
         
          <!-- End of the form -->
          </div>
          
        
          
              <div class="col s12 m7 gorilla"> </div>
              
              
                	<div class="stormy"></div>
                      <div class="row center">



  <a id="cta__main" href="signup.php" class="btn btn-large">Daftar sekarang &raquo;</a>
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

