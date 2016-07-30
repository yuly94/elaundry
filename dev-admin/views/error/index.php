<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
  <title><?php echo $this->SITE_NAME;?></title>
  		<!-- Custom Font -->
  		<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css'>
  		<!-- Custom CSS -->
  		<link rel="stylesheet" href="<?php echo URL; ?>public/css/pagenotfound-style.css">
  		<!-- Compiled and minified CSS -->
  		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.6/css/materialize.min.css">
  
  </head>
  <body>
  	<div class='description'>
  		<h1><b>404</b></h1>
  		<hr>
    	<h3><?php echo $this->msg1;?></h3>
  		<?php echo $this->msg2;?>
  			<p><?php echo $this->msg3;?></p>
  			 <br/>
  			 <br/>
                <div class="row">
					<a id="cta__main" href="<?php echo URL; ?>" class="btn btn-large"><?php echo $this->button;?></a>
        		</div>
	</div>
  
	<div class='solar-syst'>
  		<div class='sun'></div>
  		<div class='mercury'></div>
  		<div class='venus'></div>
  		<div class='earth'></div>
  		<div class='mars'></div>
  		<div class='jupiter'></div>
  		<div class='saturn'></div>
  		<div class='uranus'></div>
  		<div class='neptune'></div>
  		<div class='pluto'></div>
  		<div class='asteroids-belt'></div>
	</div>
     
  </body>
        <!--  Java Scripts -->
 
</html>
