 <?php
include('config.php');
include('session.php');
$userDetails=$userClass->userDetails($session_uid);
//print_r($userDetails);
?> 



<html>
    <head>
    	<title>Materialize CSS Framework Demo</title>
    	<meta name="viewport" content="width=device-width, initial-scale=1"/>
    	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    	
       <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    	<link type="text/css" rel="stylesheet" href="css/style.css">
    </head>
    <body>
		<nav>
			<div class="nav-wrapper z-depth-1">
				<div class="container">
					<div class="col s12 m12 l12">
				         
				          <a href="#!" class="brand-logo">Elaundry</a>
      <ul class="right hide-on-med-and-down">
        <li><a href="sass.html"><i class="material-icons left">
person_pin</i> <?php echo $userDetails->name; ?></a></li>
          <li><a href="mobile.html" class="dropdown-button" href='#' data-activates='dropdown1'><i class="material-icons">more_vert</i></a></li>
	        
		<!-- Dropdown Structure -->
  <ul id='dropdown1' class='dropdown-content'>
    <li><a href="#!">one</a></li>
    <li><a href="#!">two</a></li>
    <li class="divider"></li>
    <li><a href="logout.php">Log Out</a></li>
  </ul>
        

				        
		      		</div>
				</div>
		  	</div>
		</nav>
		
		


		<div class="container">
			<div class="row">
		      <div class="col s12 m8 l8">
		      	<div class="post-index z-depth-1">
		      		<h5>Learn CSS3 at QNimate</h5>
		      		<h6>08/01/2015</h6>
		      		<img class="responsive-img materialboxed" data-caption="CSS3 Image" src="images/css3.png" />
		      		<p><h1>Welcome <?php echo $userDetails->name; ?></h1>

<h4><a href="<?php echo BASE_URL; ?>logout.php">Logout</a></h4>
 </p>
		      	</div>
		      	<div class="post-index z-depth-1">
		      		<h5>Learn HTML5 at QNimate</h5>
		      		<h6>08/01/2015</h6>
		      		<img class="responsive-img materialboxed" data-caption="HTML5 Image" src="images/html5.png" />
		      		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at odio orci. Vestibulum sit amet augue orci. Duis eget ullamcorper lacus. Nullam quis sollicitudin velit, quis dapibus urna. Mauris ut lacus nulla. Phasellus cursus volutpat egestas. Donec sit amet condimentum nunc. Pellentesque ut ligula id lacus maximus luctus. Aenean faucibus pharetra neque eget porta. Aliquam non metus ac justo laoreet facilisis. Pellentesque sit amet fermentum augue. Pellentesque quis lectus consequat, bibendum leo non, iaculis purus.</p>
		      	</div>
		      	<div class="post-index z-depth-1">
		      		<h5>Learn WordPress at QNimate</h5>
		      		<h6>08/01/2015</h6>
		      		<img class="responsive-img materialboxed" data-caption="WordPress Image" src="images/wordpress.png" />
		      		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec at odio orci. Vestibulum sit amet augue orci. Duis eget ullamcorper lacus. Nullam quis sollicitudin velit, quis dapibus urna. Mauris ut lacus nulla. Phasellus cursus volutpat egestas. Donec sit amet condimentum nunc. Pellentesque ut ligula id lacus maximus luctus. Aenean faucibus pharetra neque eget porta. Aliquam non metus ac justo laoreet facilisis. Pellentesque sit amet fermentum augue. Pellentesque quis lectus consequat, bibendum leo non, iaculis purus.</p>
		      	</div>
		      </div>
		      <div class="col s12 m4 l4" >
 
		      	<div class="widget-item z-depth-1"  id="right_menu">
		      		<b>Categories</b>
		      		<div>
		      			<ul class="collection">
					        <a href="#!" class="collection-item active">HTML<span class="badge">1</span></a>
					        <a href="#!" class="collection-item">CSS<span class="badge">4</span></a>
					        <a href="#!" class="collection-item">JavaScript<span class="badge">14</span></a>
					        <a href="#!" class="collection-item">PHP<span class="badge">2</span></a>
					        <a href="#!" class="collection-item">WordPress<span class="badge">9</span></a>
					        <a href="#!" class="collection-item">SASS<span class="badge">34</span></a>
					      </ul>
		      		</div>
		      	</div>
		      </div>
		    </div>
        </div>

  	  	<footer>
          <div class="footer-copyright">
            <div class="container">
            © 2014 Copyright Text
            <a class="grey-text text-lighten-4 right" href="#!">About Me</a>
            </div>
          </div>
        </footer>

        <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/js/materialize.min.js"></script>
      	<script type="text/javascript">
      		$(document).ready(function(){
		      $('.materialboxed').materialbox();
		      $('#right_menu').pushpin({ top: $('#right_menu').offset().top });
		    });

		    setInterval(function(){ toast('New Post', 4000) }, 7000);
 
		            
      	</script>
    </body>
</html>
