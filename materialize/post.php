<?php
include_once('db.php');
include_once('functions.php');
#sec_session_start();

session_start(); 
      if(isset($_POST['post'])){
    $title = strip_tags($_POST['title']);
    $content = strip_tags($_POST['content']);

    $title = mysqli_real_escape_string($db,$title);
    $content = mysqli_real_escape_string($db,$content);

    $date = date('jS \of F Y h:i:s A');
    $sql = "INSERT into posts (title,content,date) VALUES ('$title', '$content', '$date')";
    if($title == "" || $content == ""){
      echo "Please complete your posts!";
    }
    mysqli_query($db,$sql);
    header("Location: index.php");
  }
?>
<!DOCTYPE html>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css" media="screen,projection">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>  
  <title>Create a new Blog Post</title>
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

  <?php if (login_check($db) == true) :  ?>



            <p>Welcome <?php echo htmlentities($_SESSION['username']); ?>!</p>

<?php
print_r($_SESSION);
?>

<div class="container">
<p class="flow-text">Create a new Blog Post!</p>
  <div class="row">
    <form class="col s12" action="post.php" method="POST" role="form">
  <div class="row">
        <div class="input-field col s12">
          <input id="title" type="text" name="title" class="validate">
          <label for="title">Enter Title</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <textarea id="textarea1" type="email" class="materialize-textarea validate" name="content" rows="10"></textarea>
          <label for="textarea1">Enter Content</label>
        </div>
     </div>
     <input name="post" type="submit" value="Post it!" class="waves-effect waves-light btn">
    </form>
         <?php else : ?>
            <p>
                <span class="error flow-text">You are not authorized to access this page. Please <a href="login.php">login</a>.</span>
            </p>
        <?php endif; ?>
  </div>
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
            &copy; 2016 Vinayak R K.
            <a class="grey-text text-lighten-4 right" href="#!">Gonna social icons here</a>
            </div>
          </div>
        </footer>


<script>
  $( document ).ready(function(){
    $('#textarea1').val('');
    $('#textarea1').trigger('autoresize');
    $('#title').val('');
    $('#title').trigger('autoresize');
    $(".button-collapse").sideNav();
  })
</script>
</body>
</html>
