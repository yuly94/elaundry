<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
  <title><?php echo SITE_NAME;?></title>
  
  <!-- Compiled and minified JavaScript -->
  <script   src="https://code.jquery.com/jquery-2.2.4.min.js" ></script>

  <!-- Font  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  <!-- Compiled and minified CSS -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/css/materialize.min.css">

  <!-- Compiled and minified JavaScript -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>
 

   
</head>
<body>
 <?php Session::init();?>  
 <?php 
 $url = isset($_GET['url']) ? $_GET['url']:null;
 $url = rtrim($url,'/');
 $url = explode('/', $url);
 
 ?>
  <nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container"><a id="logo-container" href="<?php echo URL; ?>" class="brand-logo"><?php echo LOGO_NAME;?></a>
      <ul class="right hide-on-med-and-down">
         <li><a href="badges.html">Components</a></li>  
        	<?php if (Session::get('loggedIn')==true):?>
         <li><a href="dashboard/logout">Logout</a></li>  
        	<?php else:?>
        		<?php if ($url[0]!='login'):?>
	         <li><a href="login">Login</a></li>  
         		<?php endif;?>
    	  	<?php endif;?>
      </ul>
    <ul id="nav-mobile" class="side-nav">
         <li><a href="badges.html">Components</a></li>  
        	<?php if (Session::get('loggedIn')==true):?>
         <li><a href="dashboard/logout">Logout</a></li>  
        	<?php else:?>
        		<?php if ($url[0]!='login'):?>
	         <li><a href="login">Login</a></li>  
         		<?php endif;?>
    	  	<?php endif;?>
      </ul>     
      <a href="#" data-activates="nav-mobile" class="button-collapse"><i class="material-icons">menu</i></a>
    </div>
  </nav>
</body>
</html>