<?php
session_start();
include_once("db.php");
?>
<!DOCTYPE html>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<!--<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">-->
 <!-- Compiled and minified CSS -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css" media="screen,projection">
  <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>  
	<title>My Blog</title>
</head>
<body>
      <!--Import jQuery before materialize.js-->
      <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>

  <nav>
    <div class="nav-wrapper">
      <a href="#" class="brand-logo">Logo</a>
      <ul id="nav-mobile" class="right hide-on-med-and-down">
        <li><a href="view.php">View Blog Posts</a></li>
        <li><a href="post.php">Create a New Blog Post</a></li>
      </ul>
    </div>
  </nav>      
<div class="container">
<?php
	$sql = "SELECT * FROM posts ORDER BY id DESC";
	$result = $db->query($sql);
if(mysqli_num_rows($result)>0){
	while($row = mysqli_fetch_assoc($result)){
		$id = $row['id'];
		$title = $row['title'];
		$content = $row['content'];
		$date = $row['date'];
		#$admin = "<div><a href='del_post.php?pid=$id>Delete&nbsp;</a><a href='edit.php?pid=$id>Edit&nbsp;</a>";
		echo "<div class=\"row\">
		        <div class=\"col s12 m6\">
		          <div class=\"card  darken-1\">
		            <div class=\"card-content white-text\">
		              <span class=\"card-title\" style='color:#000;'>".$title."</span>
		              <p style='color:#000;'>".$row["content"]."</p>
		              <p style='color:#000;'>".$row["date"]."</p>
		            </div>
				     <div class=\"card-action\">
		              <a href=\"delete.php?del=$id\" class=\"waves-effect waves-light btn\">Delete this Post</a>
		              <a href=\"edit.php?edit=$id\" class=\"waves-effect waves-light btn\">Edit this Post</a>
		            </div>
		          </div>
		        </div>
		      </div>";
      	//echo "<p class='lead'><strong>Title:</strong> " . $title. "</p><br />";
        //echo "Content: " . . "<br />";
        //echo "Date:".$row["date"]."<br /><br />";
        //echo "<a href='delete.php?del=$id' class='btn btn-danger'>Delete Post</a>&nbsp;<a href='edit.php?edit=$id' class='btn btn-success'>Edit Post</a><br /><br /><hr />";
        #$e = print('Title: ' . $title. '<br /> Content: ' . $content. '<br /> Date:'.$date.'<br /><br />');
        
        #$posts .= "<div><h2><a href='view_post.php?pid=$id'>$title</a></h2><h3>$date</h3><p>$e</p>";
	}
}
$db->close();
?>


<h3> Create a Post</h3>
<br />
<a href="post.php" target="_blank" class="waves-effect waves-light btn blue">Create a New Post</a>
</div>
<footer class="page-footer">
          <div class="container">
            <div class="row">
              <div class="col l6 s12">
                <h5 class="white-text">Footer Content</h5>
                <p class="grey-text text-lighten-4">You can use rows and columns here to organize your footer content.</p>
              </div>
              <div class="col l4 offset-l2 s12">
                <h5 class="white-text">Links</h5>
                <ul>
                  <li><a class="grey-text text-lighten-3" href="#!">Link 1</a></li>
                  <li><a class="grey-text text-lighten-3" href="#!">Link 2</a></li>
                  <li><a class="grey-text text-lighten-3" href="#!">Link 3</a></li>
                  <li><a class="grey-text text-lighten-3" href="#!">Link 4</a></li>
                </ul>
              </div>
            </div>
          </div>
          <div class="footer-copyright">
            <div class="container">
            &copy; 2016 Vinayak R K. <?php
print_r($_SESSION);
?>

            <a class="grey-text text-lighten-4 right" href="#!">Gonna social icons here</a>
            </div>
          </div>
        </footer>
</body>
</html>
