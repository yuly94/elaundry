<!DOCTYPE html>
    <!--[if IE 9 ]><html class="ie9" data-ng-app="materialAdmin" data-ng-controller="materialadminCtrl as mactrl"><![endif]-->
    <![if IE 9 ]><html data-ng-app="materialAdmin" data-ng-controller="materialadminCtrl as mactrl">
        <![endif]>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title><?php echo SITE_NAME;?> - <?php echo $this->title; ?></title>

        <!-- Vendor CSS -->
        <link href="<?php echo URL; ?>public/vendors/bower_components/animate.css/animate.min.css" rel="stylesheet">
        <link href="<?php echo URL; ?>public/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css" rel="stylesheet">
        <link href="<?php echo URL; ?>public/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.css" rel="stylesheet">
        <link href="<?php echo URL; ?>public/vendors/bower_components/angular-loading-bar/src/loading-bar.css" rel="stylesheet">
        <link href="<?php echo URL; ?>public/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css" rel="stylesheet">

        <!-- CSS -->
        
        <link href="<?php echo URL; ?>public/css/app.min.1.css" rel="stylesheet" id="app-level">
        <link href="<?php echo URL; ?>public/css/app.min.2.css" rel="stylesheet">
        <link href="<?php echo URL; ?>public/css/demo.css" rel="stylesheet">

        <!-- Core -->
        <script src="<?php echo URL; ?>public/vendors/bower_components/jquery/dist/jquery.min.js"></script>


    <?php 
    if (isset($this->js)) 
        {
            foreach ($this->js as $js)
                {
                    echo '<script type="text/javascript" src="'.URL.'views/'.$js.'"></script>';
                }
        }
    ?>
</head>
<?php Session::init(); ?>
    