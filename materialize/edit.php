<?php
session_start();
include("db.php");
  $id = $_GET['edit'];
  
  $query = "SELECT * FROM posts WHERE id = $id";
  $result = mysqli_query($db,$query);
  //returning data
  $row = mysqli_fetch_array($result);
?>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css" media="screen,projection">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>  
  <title>Edit a Post</title>
  <style type="text/css">
  body {
    background-color: #d9d9d9;
    background-position: center center;
    background-repeat: no-repeat; 
    /* Safari 4-5, Chrome 1-9 */ 
    background: -webkit-gradient(radial, center center, 0, center center, 460, from(#d9d9d9), to(#ffffff)); 
    /* Safari 5.1+, Chrome 10+ */ 
    background: -webkit-radial-gradient(circle, #d9d9d9, #fff);
    /* Firefox 3.6+ */ 
    background: -moz-radial-gradient(circle, #d9d9d9, #fff); 
    /* IE 10 */ 
    background: -ms-radial-gradient(circle, #d9d9d9, #fff); 
    /* Opera */
    background: radial-gradient(circle at 50% 50%, #fff, #d9d9d9);
  }
  </style>
</head>
<body>
<!--Import jQuery before materialize.js-->
      <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script> 

  <nav>
    <div class="nav-wrapper">
      <a href="index.php" class="brand-logo">Logo</a>
      <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
      <ul class="right hide-on-med-and-down">
        <li><a href="post.php">Create a New Post</a></li>
        <li><a href="index.php">Back to Home</a></li>
      </ul>
      <ul class="side-nav" id="mobile-demo">
        <li><a href="post.php">Create a New Post</a></li>
        <li><a href="index.php">Back to Home</a></li>
      </ul>
    </div>
  </nav>


<!--
<div class="container">
<form method="post" action="update.php">
<input type="hidden" class="form-control" name="id" value="<?php #echo $row['id'];?>"><br />
<label class="lead">Enter new title:</label> <input type="text" class="form-control" name="title" value="<?php #echo $row['title'];?>"><br />
<label class="lead">Enter new content:</label> <textarea type="text" class="form-control" name="content" rows="10" value="<?php #echo $row['content'];?>"></textarea><hr />

<input type="submit" name="submit" value="Save" class="btn-lg btn btn-info"/>
</form>
</div>-->

<div class="container">
<p class="flow-text">Edit <?php echo $row['title'];?> Post!</p>
  <div class="row">
    <form class="col s12" action="update.php" method="POST" role="form">
  <div class="row">
        <div class="input-field col s12">
          <input type="hidden" class="form-control" name="id" value="<?php echo $row['id'];?>"><br />
          <input id="title" type="text" name="title" class="validate">
          <label for="title">Enter New Title</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <textarea id="textarea1" type="text" class="materialize-textarea validate" name="content" rows="10" ></textarea>
          <label for="textarea1">Enter New Content</label>
        </div>
     </div>
     <input name="submit" type="submit" value="Edit it!" class="waves-effect waves-light btn">
    </form>
  </div>
</div>

<script>
  $( document ).ready(function(){
    $('#textarea1').val('<?php echo $row['content'];?>');
    $('#textarea1').trigger('autoresize');
    $('#title').val('<?php echo $row['title'];?>');
    $('#title').trigger('autoresize');
    $(".button-collapse").sideNav();
  })
</script>
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
            &copy; 2016 Vinayak R K.
            <a class="grey-text text-lighten-4 right" href="#!">Gonna social icons here</a>
            </div>
          </div>
        </footer>
</body>
</html>
<?php
mysqli_close($db);
?>