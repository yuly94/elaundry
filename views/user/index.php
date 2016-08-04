 <div class="container">
   <div class="section">
<h1>User</h1>
	<form action="<?php echo URL; ?>user/create" method="post">
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
      	 <div class="row">
        	<div class="input-field col s12">
          		<select name="role">
          			<option value="" disabled selected>Choose your option</option>
          			<option value="default">Default</option>
          			<option value="admin">Admin</option>
          		</select>
          		<label for="password"><?php echo $this->label_role;?></label>
       		 </div>
      	</div>
		<input class="btn btn-large" type="submit" />
	</form>
 <div class="container">
   	<div class="section">
		<table>
			<?php 
				foreach ($this -> userList as $key => $value){
					echo '<tr>';
					echo '<td>'.$value['userid'] .'</td>';
					echo '<td>'.$value['username'] .'</td>';
					echo '<td>'.$value['role'] .'</td>';
					echo '<td>
          				<a href="'.URL.'user/edit/'.$value['userid'].'">Edit</a> 
          				<a href="'.URL.'user/delete/'.$value['userid'].'">Delete</a></td>';
					echo '</tr>';
				}
				//print_r($this->userList);
			?>
		</table>
	</div>
</div>
  </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
    $('select').material_select();
});
</script>
