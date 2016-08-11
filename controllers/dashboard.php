<?php
/**
 * Controler
 *      My_COntroller extend controller
 *          Dashboaard extend my_controller
 */


class Dashboard extends Controller {
	
	function __construct(){
		parent::__construct();
		//echo 'we are in login dashboard';
                Auth::handleLogin();
		
		//print_r($_SESSION);
                
                $this->view-> ngApp ="data-ng-app='materialAdmin' data-ng-controller='materialadminCtrl as mactrl'";
 
         
                $this->view->jsVendorsConstruct = array(
                    'bower_components/jquery/dist/jquery.min.js',
                    'bower_components/angular/angular.min.js',
                    'bower_components/angular-animate/angular-animate.min.js',
                    'bower_components/angular-resource/angular-resource.min.js',
                 
                    'bower_components/angular-ui-router/release/angular-ui-router.min.js',
                    'bower_components/angular-loading-bar/src/loading-bar.js',
                    'bower_components/oclazyload/dist/ocLazyLoad.min.js',
                    'bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js',
                    'bower_components/angular-nouislider/src/nouislider.min.js',
                    'bootstrap-growl/bootstrap-growl.min.js',
                    'bower_components/lightgallery/light-gallery/js/lightGallery.min.js',
                    'bower_components/flot/jquery.flot.js',
                    'bower_components/flot.curvedlines/curvedLines.js',
                    'bower_components/flot/jquery.flot.resize.js',
                    'bower_components/moment/min/moment.min.js',
                    'bower_components/fullcalendar/dist/fullcalendar.min.js',
                    'bower_components/flot-orderBars/js/jquery.flot.orderBars.js',
                    'bower_components/flot/jquery.flot.pie.js',
                    'bower_components/flot.tooltip/js/jquery.flot.tooltip.min.js',
                    'bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js',
                    'bower_components/bootstrap-sweetalert/lib/sweet-alert.min.js',
                    'bower_components/Waves/dist/waves.min.js',
                    'bower_components/ng-table/dist/ng-table.min.js',
         'js/app.js',
  
    'js/controllers/main.js',
       'js/services.js',
        'js/templates.js',
       'js/controllers/ui-bootstrap.js',
        'js/controllers/table.js' 
                    
                   );
                
      
          
  
   
                
                $this->view->cssVendors = array(
                    
                    'bower_components/animate.css/animate.min.css',
                    'bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css',
                    'bower_components/bootstrap-sweetalert/lib/sweet-alert.css',
                    'bower_components/angular-loading-bar/src/loading-bar.css',
                    'bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css',
                    'bower_components/lightgallery/light-gallery/css/lightGallery.css'
                    ); 
                
                $this->view->jcs = array(
                    'dashboard/js/app.js',
                    'dashboard/js/services.js',
                    'dashboard/js/templates.js',
                    'dashboard/js/controllers/ui-bootstrap.js',
                    'dashboard/js/controllers/table.js',
                    'dashboard/js/modules/template.js',
                    'dashboard/js/modules/ui.js',
                    'dashboard/js/modules/charts/flot.js',
                    'dashboard/js/modules/charts/other-charts.js'
                   );
                
                $this->view->css = array(
                    'dashboard/css/app.min.1.css',
                    'dashboard/css/app.min.2.css',
                    'dashboard/css/demo.css',
                    'dashboard/css/fullcalendar.css');
                	
	}
 
	function index() {
	//	require 'models/login_model.php'; // have creted autoload in controller
	//	$model = new Login_Model();  // have creted autoload in controller
            
      
                // sample $this->view->css = array('bootstrap.css','jquery-ui.css');
		//$this->view->js = array('dashboard/js/default.js');
                //
               
                              
                   
                $this->view->jsVendors = array('sparklines/jquery.sparkline.min.js',
                    'bower_components/jquery.easy-pie-chart/dist/jquery.easypiechart.min.js',
                     'bower_components/simpleWeather/jquery.simpleWeather.min.js'
                    );
                //$this->view->css = array('dashboard/css/fullcalendar.css');
            
                // $this->view->sidebarLeft = 'dashboard/inc/sidebar-left.php';
                $this->view->title = 'Dashboard';
		$this->view->render('dashboard/index',false,false,FALSE,FALSE);
               // $this->view->render('dashboard/inc/sidebar-left',false,false,false,FALSE);
                $this->view->render(FALSE,false,false,false,false);
	}
        
        function profile() {
	//	require 'models/login_model.php'; // have creted autoload in controller
	//	$model = new Login_Model();  // have creted autoload in controller
            
                $this->view->jsVendors = array(
                'bower_components/lightgallery/light-gallery/js/lightGallery.min.js' );
                
                $this->view->cssVendors = array(
                'bower_components/lightgallery/light-gallery/css/lightGallery.css'
                    ); 
            
                // $this->view->sidebarLeft = 'dashboard/inc/sidebar-left.php';
                $this->view->title = 'Dashboard';
		$this->view->render('dashboard/profile',3);
                $this->view->render('dashboard/inc/sidebar-left',1);
	}
	
	function logout() {
		Session::destroy();
		header('location: '.URL.'login');
		exit;
	}

	function xhrInsert()
	{
		$this->model->xhrInsert();
	}
	
	function xhrGetListings()
	{
		$this->model->xhrGetListings();
	}
	
	function xhrDeleteListing()
	{
		$this->model->xhrDeleteListing();
	}
 
}