 <div class="container">
   <div class="section">

	<form action="login/run" method="post">
   		<div class="row">
        	<div class="input-field col s12">
          		<input id="username" type="email" name="username" class="validate">
        	  	<label for="username"><?php echo $this->label_username;?></label>
        	</div>
      	  </div>
    	  <div class="row">
        	<div class="input-field col s12">
          		<input id="password" type="password" name="password" class="validate">
          		<label for="password"><?php echo $this->label_password;?></label>
       		 </div>
      	</div>
		<input class="btn btn-large" type="submit" />
	</form>

	</div>
</div>