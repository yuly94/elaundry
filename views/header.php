<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
  <title><?php echo SITE_NAME;?></title>
  
  <!-- Compiled and minified JavaScript -->
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

  <!-- Font  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  
  <!-- Compiled and minified CSS -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/css/materialize.min.css">

  <!-- Custom Compiled CSS -->
  <link rel="stylesheet" href="<?php echo URL;?>public/css/header.css">

  <!-- Compiled and minified JavaScript -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.7/js/materialize.min.js"></script>


	<?php 
		if (isset($this->js)) {
			foreach ($this->js as $js)
			{
				echo '  <script type="text/javascript" src="'.URL.'views/'.$js.'"></script>';
			}
		}
	?>

 
   
</head>


<body>
 <?php Session::init();?>  
 <?php 
 $url = isset($_GET['url']) ? $_GET['url']:null;
 $url = rtrim($url,'/');
 $url = explode('/', $url);
 
 ?>
 
 <!-- Dropdown 2 Structure -->
<ul id="dropdown" class="dropdown-content collection">
		<li class="collection-item avatar">
				<img src="http://materializecss.com/images/yuna.jpg" alt="" class="circle">
				<span class="title">Name</span>
				<p>First Line</p>
				<a href="#!" class="secondary-content"><i class="material-icons">contact_mail</i></a>
		</li>
		<li class="collection-item avatar">
				<img src="http://materializecss.com/images/yuna.jpg" alt="" class="circle">
				<span class="title">Name</span>
				<p>First Line</p>
				<a href="#!" class="secondary-content"><i class="material-icons">contact_mail</i></a>
		</li>
		<li class="collection-item avatar">
				<img src="http://materializecss.com/images/yuna.jpg" alt="" class="circle">
				<span class="title">Name</span>
				<p>First Line</p>
				<a href="#!" class="secondary-content"><i class="material-icons">contact_mail</i></a>
		</li>
</ul>
 
 	<!-- Dropdown 1 Structure -->
	<ul id="dropdown1" class="dropdown-content" >
  		<li><a href="#!">one</a></li>
  		<li><a href="#!">two</a></li>
  				
  		<li class="divider"></li>
  		<li><a href="dashboard/logout">Logout</a></li>  
	</ul>
 
  <nav class="light-blue lighten-1" role="navigation">
     <div class="nav-wrapper container">
	    <!-- <a id="logo-container" href="</?php echo URL; ?>" class="brand-logo center"></?php echo LOGO_NAME;?></a>
          -->
     	   <a href="<?php echo URL; ?>" class="breadcrumb">Home</a>
        	<?php if ($url[0]!=''):?>
        	<a href="<?php echo $url[0]; ?>" class="breadcrumb"><?php echo $url[0];?></a>
        	<?php endif;?>
        	<?php if ($url[1]==false):?>
        	<a href="<?php echo $url[1]; ?>" class="breadcrumb"><?php echo $url[1];?></a>
        	<?php endif;?>
     
      <ul class="right hide-on-med-and-down">
      <?php if (Session::get('loggedIn')==false):?>
         <li><a href="badges.html">Help</a></li> 
         <li><a href="badges.html">Index</a></li> 
         <?php endif;?> 
         <li><a href="">Message<span class="new badge">4</span></a></li>
        	<?php if (Session::get('loggedIn')==true):?>
         		<li><a href="dashboard">Dashboard</a></li>
         		
         		<?php if (Session::get('role')=='owner'):?>
         		<li><a href="<?php echo URL;?>user">User</a></li>
         		<?php endif;?>
         		 <!-- Dropdown Trigger -->
         		 
      			<li><a class="dropdown-button" href="#!" data-activates="dropdown1" data-beloworigin="true"> <i class="material-icons right">more_vert</i><?php echo $_SESSION['users']; ?></a></li>
         		
         		<!-- Dropdown Trigger 2 
				<li><a class="dropdown-button" href="#!" data-activates="dropdown" data-beloworigin="true">Contacts<i class="material-icons right">arrow_drop_down</i></a></li>
  				-->
        		<?php else:?>
        			<?php if ($url[0]!='login'):?>
	         			<li><a href="login">Login</a></li>  
         		<?php endif;?>
    	  	<?php endif;?>
      </ul>
    <ul id="nav-mobile" class="side-nav">
         <li><a href="badges.html">Components</a></li>  
            <li><div class="userView">
    	<!--<img class="background" src="images/office.jpg"> -->
     	<!--<a href="#!user"><img class="circle" src="images/yuna.jpg"></a> -->
      		<a href="#!name"><span class="white-text name">John Doe</span></a>
      		<a href="#!email"><span class="white-text email">jdandturk@gmail.com</span></a>
    		</div></li>
            <li><div class="divider"></div></li>
        	<?php if (Session::get('loggedIn')==true):?>
         	<li><a class="waves-effect" href="dashboard/logout"><i class="material-icons">lock_open</i>Logout</a></li>
        	<?php else:?>
        		<?php if ($url[0]!='login'):?>
	         <li><a href="login">Login</a></li>  
         		<?php endif;?>
    	  	<?php endif;?>
    	  	

    		<li><a class="waves-effect" href="#!"><i class="material-icons">lock_open</i>Logout</a></li>
    		<li><a href="#!">Second Link</a></li>
    		<li><div class="divider"></div></li>
    		<li><a class="subheader">Subheader</a></li>
    		<li><a class="waves-effect" href="#!">Third Link With Waves</a></li>
    	  	
      </ul>     
      <a href="#" data-activates="nav-mobile" class="button-collapse"><i class="material-icons">menu</i></a>
    </div>
  </nav>
</body>
  <!-- Compiled and minified JavaScript -->
  <script type="text/javascript" src="<?php echo URL;?>public/js/header.js"></script>
 
 

</html>

