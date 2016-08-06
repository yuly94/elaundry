 
<div class="container">
<div class="section">
<h1>User</h1>
	<form action="<?php echo URL; ?>user/editSave/<?php echo $this->user_data['id'] ;?>" method="post">
		<div class="row">
			<div class="input-field col s12">
				<input id="username" type="email" name="username" value="<?php echo $this->user_data['user'] ;?>" class="validate">
			<label for="username"><?php echo $this->label_username;?></label>
        	</div>
      	  </div>
    	  <div class="row">
        	<div class="input-field col s12">
          		<input id="password" type="password" name="password" value="<?php echo $this->user_data['password'] ;?>" class="validate">
          		<label for="password"><?php echo $this->label_password;?></label>
       		 </div>
      	</div>
      	 <div class="row">
        	<div class="input-field col s12">
          		<select name="role">
          			<option value="default"><?php if ($this->user_data['role']=='default')echo 'Selected' ;?> Default</option>
          			<option value="admin"><?php if ($this->user_data['role']=='admin')echo 'Selected' ;?> Admin</option>
          			<option value="owner"><?php if ($this->user_data['role']=='owner')echo 'Selected' ;?> Owner</option>

          		</select>
          		<label for="password"><?php echo $this->label_role;?></label>
       		 </div>
      	</div>
		<input class="btn btn-large" type="submit" />
	</form>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
    $('select').material_select();
});
</script>