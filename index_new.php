<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
?>
<!DOCTYPE html>
    <!--[if IE 9 ]><html class="ie9" data-ng-app="materialAdmin" data-ng-controller="materialadminCtrl as mactrl"><![endif]-->
    <![if IE 9 ]><html data-ng-app="materialAdmin" data-ng-controller="materialadminCtrl as mactrl"><![endif]>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Material Admin</title>


        <!-- Vendor CSS -->
        <link href="public/vendors/bower_components/animate.css/animate.min.css" rel="stylesheet">
        <link href="public/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css" rel="stylesheet">
        <link href="public/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.css" rel="stylesheet">
        <link href="public/vendors/bower_components/angular-loading-bar/src/loading-bar.css" rel="stylesheet">
        <link href="public/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css" rel="stylesheet">

        <!-- CSS -->
        <link href="public/css/app.min.1.css" rel="stylesheet" id="app-level">
        <link href="public/css/app.min.2.css" rel="stylesheet">
        <link href="public/css/demo.css" rel="stylesheet">


    </head>


    <body data-ng-class="{ &#39;sw-toggled&#39;: mactrl.layoutType === &#39;1&#39;}" class="sw-toggled" style="">

        <!-- uiView:  --><data ui-view="" class="ng-scope">

        <!-- ngInclude: 'template/header.html' -->
        <header id="header" data-current-skin="blue" data-ng-include="&#39;template/header.html&#39;" data-ng-controller="headerCtrl as hctrl" class="ng-scope"><ul class="header-inner clearfix ng-scope">
        <li id="menu-trigger" data-target="mainmenu" data-toggle-sidebar="" data-model-left="mactrl.sidebarToggle.left" data-ng-class="{ &#39;open&#39;: mactrl.sidebarToggle.left === true }" class="ng-isolate-scope"><div class="line-wrap">
        <div class="line top"></div><div class="line center"></div>
        <div class="line bottom"></div></div></li><li class="logo hidden-xs">
        <a data-ui-sref="home" data-ng-click="mactrl.sidebarStat($event)" href="home">Material Admin</a></li>
        <li class="pull-right">
        <ul class="top-menu"><li id="toggle-width"><div class="toggle-switch">
                    <input id="tw-switch" type="checkbox" hidden="" data-change-layout="mactrl.layoutType" class="ng-isolate-scope"><label for="tw-switch" class="ts-helper"></label></div></li><li id="top-search">

        <a href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-click="hctrl.openSearch()">
            <i class="tm-icon zmdi zmdi-search"></i></a></li><li class="dropdown" uib-dropdown=""><a uib-dropdown-toggle="" href="http://byrushan.com/projects/ma/1-5-2/angular/" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false"><i class="tm-icon zmdi zmdi-email"></i> <i class="tmn-counts">6</i></a><div class="dropdown-menu dropdown-menu-lg stop-propagate pull-right"><div class="listview"><div class="lv-header">Messages</div><div class="lv-body"><!-- ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list" style=""><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/1.jpg" alt="" src="themes_files/1.jpg"></div><div class="media-body"><div class="lv-title ng-binding">David Belle</div><small class="lv-small ng-binding">Cum sociis natoque penatibus et magnis dis parturient montes</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list" style=""><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/2.jpg" alt="" src="themes_files/2.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Jonathan Morris</div><small class="lv-small ng-binding">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list"><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/3.jpg" alt="" src="themes_files/3.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Fredric Mitchell Jr</div><small class="lv-small ng-binding">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list"><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/4.jpg" alt="" src="themes_files/4.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Glenn Jecobs</div><small class="lv-small ng-binding">Ut vitae lacus sem ellentesque maximus, nunc sit amet varius dignissim, dui est consectetur neque</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list"><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/5.jpg" alt="" src="themes_files/5.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Bill Phillips</div><small class="lv-small ng-binding">Proin laoreet commodo eros id faucibus. Donec ligula quam, imperdiet vel ante placerat</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --></div><div class="clearfix"></div><a class="lv-footer" href="http://byrushan.com/projects/ma/1-5-2/angular/">View All</a></div></div></li><li class="dropdown" uib-dropdown=""><a uib-dropdown-toggle="" href="http://byrushan.com/projects/ma/1-5-2/angular/" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false"><i class="tm-icon zmdi zmdi-notifications"></i> <i class="tmn-counts">9</i></a><div class="dropdown-menu dropdown-menu-lg stop-propagate pull-right"><div class="listview" id="notifications"><div class="lv-header">Notification<ul class="actions"><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-click="hctrl.clearNotification($event)"><i class="zmdi zmdi-check-all"></i></a></li></ul></div><div class="lv-body"><!-- ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list" style=""><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/1.jpg" alt="" src="themes_files/1.jpg"></div><div class="media-body"><div class="lv-title ng-binding">David Belle</div><small class="lv-small ng-binding">Cum sociis natoque penatibus et magnis dis parturient montes</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list" style=""><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/2.jpg" alt="" src="themes_files/2.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Jonathan Morris</div><small class="lv-small ng-binding">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list"><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/3.jpg" alt="" src="themes_files/3.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Fredric Mitchell Jr</div><small class="lv-small ng-binding">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list"><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/4.jpg" alt="" src="themes_files/4.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Glenn Jecobs</div><small class="lv-small ng-binding">Ut vitae lacus sem ellentesque maximus, nunc sit amet varius dignissim, dui est consectetur neque</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --><a class="lv-item ng-scope" ng-href="" ng-repeat="w in hctrl.messageResult.list"><div class="media"><div class="pull-left"><img class="lv-img-sm" ng-src="img/profile-pics/5.jpg" alt="" src="themes_files/5.jpg"></div><div class="media-body"><div class="lv-title ng-binding">Bill Phillips</div><small class="lv-small ng-binding">Proin laoreet commodo eros id faucibus. Donec ligula quam, imperdiet vel ante placerat</small></div></div></a><!-- end ngRepeat: w in hctrl.messageResult.list --></div><div class="clearfix"></div><a class="lv-footer" href="http://byrushan.com/projects/ma/1-5-2/angular/">View Previous</a></div></div></li><li class="dropdown hidden-xs" uib-dropdown=""><a uib-dropdown-toggle="" href="http://byrushan.com/projects/ma/1-5-2/angular/" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false"><i class="tm-icon zmdi zmdi-view-list-alt"></i> <i class="tmn-counts">2</i></a><div class="dropdown-menu pull-right dropdown-menu-lg"><div class="listview"><div class="lv-header">Tasks</div><div class="lv-body"><div class="lv-item"><div class="lv-title m-b-5">HTML5 Validation Report</div><div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="95" aria-valuemin="0" aria-valuemax="100" style="width: 95%"><span class="sr-only">95% Complete (success)</span></div></div></div><div class="lv-item"><div class="lv-title m-b-5">Google Chrome Extension</div><div class="progress"><div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%"><span class="sr-only">80% Complete (success)</span></div></div></div><div class="lv-item"><div class="lv-title m-b-5">Social Intranet Projects</div><div class="progress"><div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%"><span class="sr-only">20% Complete</span></div></div></div><div class="lv-item"><div class="lv-title m-b-5">Bootstrap Admin Template</div><div class="progress"><div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%"><span class="sr-only">60% Complete (warning)</span></div></div></div><div class="lv-item"><div class="lv-title m-b-5">Youtube Client App</div><div class="progress"><div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%"><span class="sr-only">80% Complete (danger)</span></div></div></div></div><div class="clearfix"></div><a class="lv-footer" href="http://byrushan.com/projects/ma/1-5-2/angular/">View All</a></div></div></li><li class="dropdown" uib-dropdown=""><a uib-dropdown-toggle="" href="http://byrushan.com/projects/ma/1-5-2/angular/" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false"><i class="tm-icon zmdi zmdi-more-vert"></i></a><ul class="dropdown-menu dm-icon pull-right"><li class="skin-switch hidden-xs"><!-- ngRepeat: w in mactrl.skinList | limitTo : 6 --><span ng-repeat="w in mactrl.skinList | limitTo : 6" class="ss-skin bgm-lightblue" data-ng-click="mactrl.skinSwitch(w)"></span><!-- end ngRepeat: w in mactrl.skinList | limitTo : 6 --><span ng-repeat="w in mactrl.skinList | limitTo : 6" class="ss-skin bgm-bluegray" data-ng-click="mactrl.skinSwitch(w)"></span><!-- end ngRepeat: w in mactrl.skinList | limitTo : 6 --><span ng-repeat="w in mactrl.skinList | limitTo : 6" class="ss-skin bgm-cyan" data-ng-click="mactrl.skinSwitch(w)"></span><!-- end ngRepeat: w in mactrl.skinList | limitTo : 6 --><span ng-repeat="w in mactrl.skinList | limitTo : 6" class="ss-skin bgm-teal" data-ng-click="mactrl.skinSwitch(w)"></span><!-- end ngRepeat: w in mactrl.skinList | limitTo : 6 --><span ng-repeat="w in mactrl.skinList | limitTo : 6" class="ss-skin bgm-green" data-ng-click="mactrl.skinSwitch(w)"></span><!-- end ngRepeat: w in mactrl.skinList | limitTo : 6 --><span ng-repeat="w in mactrl.skinList | limitTo : 6" class="ss-skin bgm-orange" data-ng-click="mactrl.skinSwitch(w)"></span><!-- end ngRepeat: w in mactrl.skinList | limitTo : 6 --></li><li class="divider hidden-xs"></li><li class="hidden-xs"><a data-ng-click="hctrl.fullScreen()" href="http://byrushan.com/projects/ma/1-5-2/angular/"><i class="zmdi zmdi-fullscreen"></i> Toggle Fullscreen</a></li><li><a data-ng-click="hctrl.clearLocalStorage()" href="http://byrushan.com/projects/ma/1-5-2/angular/"><i class="zmdi zmdi-delete"></i> Clear Local Storage</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/"><i class="zmdi zmdi-face"></i> Privacy Settings</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/"><i class="zmdi zmdi-settings"></i> Other Settings</a></li></ul></li><li class="hidden-xs ng-isolate-scope" data-target="chat" data-toggle-sidebar="" data-model-right="mactrl.sidebarToggle.right" data-ng-class="{ &#39;open&#39;: mactrl.sidebarToggle.right === true }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/"><i class="tm-icon zmdi zmdi-comment-alt-text"></i></a></li></ul></li></ul><!-- Top Search Content --><div id="top-search-wrap" class="ng-scope"><div class="tsw-inner"><i id="top-search-close" class="zmdi zmdi-arrow-left" data-ng-click="hctrl.closeSearch()"></i> <input type="text"></div></div></header>

<section id="main" class="ng-scope">
    <!-- ngInclude: 'template/sidebar-left.html' -->
    <aside id="sidebar" data-ng-include="&#39;
    template/sidebar-left.html&#39;" data-ng-class="{ &#39;toggled&#39;: mactrl.sidebarToggle.left === true }" class="ng-scope"><div class="sidebar-inner c-overflow ng-scope mCustomScrollbar _mCS_2 mCS-autoHide" style="position: relative; overflow: visible;"><div id="mCSB_2" class="mCustomScrollBox mCS-minimal-dark mCSB_vertical_horizontal mCSB_outside" tabindex="0"><div id="mCSB_2_container" class="mCSB_container mCS_x_hidden mCS_no_scrollbar_x" style="position: relative; top: 0px; left: 0px; width: 100%;" dir="ltr"><div class="profile-menu"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><div class="profile-pic"><img src="themes_files/1.jpg" alt="" class="mCS_img_loaded"></div><div class="profile-info">Malinda Hollaway <i class="zmdi zmdi-caret-down"></i></div></a><ul class="main-menu"><li><a data-ui-sref="pages.profile.profile-about" data-ng-click="mactrl.sidebarStat($event)" href="pages/profile/profile-about"><i class="zmdi zmdi-account"></i> View Profile</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">
    <i class="zmdi zmdi-input-antenna"></i> Privacy Settings</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/"><i class="zmdi zmdi-settings"></i> Settings</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/"><i class="zmdi zmdi-time-restore"></i> Logout</a></li></ul></div><ul class="main-menu"><li data-ui-sref-active="active" class="active"><a data-ui-sref="home" data-ng-click="mactrl.sidebarStat($event)" href="home"><i class="zmdi zmdi-home"></i> Home</a></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;headers&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-view-compact"></i> Headers</a><ul><li><a data-ui-sref-active="active" data-ui-sref="headers.textual-menu" data-ng-click="mactrl.sidebarStat($event)" href="headers/textual-menu">Textual menu</a></li><li><a data-ui-sref-active="active" data-ui-sref="headers.image-logo" data-ng-click="mactrl.sidebarStat($event)" href="headers/image-logo">Image logo</a></li><li><a data-ui-sref-active="active" data-ui-sref="headers.mainmenu-on-top" data-ng-click="mactrl.sidebarStat($event)" href="headers/mainmenu-on-top">Mainmenu on top</a></li></ul></li><li data-ui-sref-active="active"><a data-ui-sref="typography" data-ng-click="mactrl.sidebarStat($event)" href="typography"><i class="zmdi zmdi-format-underlined"></i> Typography</a></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;widgets&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-widgets"></i> Widgets</a><ul><li><a data-ui-sref-active="active" data-ui-sref="widgets.widget-templates" data-ng-click="mactrl.sidebarStat($event)" href="widgets/widget-templates">Templates</a></li><li><a data-ui-sref-active="active" data-ui-sref="widgets.widgets" data-ng-click="mactrl.sidebarStat($event)" href="widgets/widgets">Widgets</a></li></ul></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;tables&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-view-list"></i> Tables</a><ul><li><a data-ui-sref-active="active" data-ui-sref="tables.tables" data-ng-click="mactrl.sidebarStat($event)" href="tables/tables">Tables</a></li><li><a data-ui-sref-active="active" data-ui-sref="tables.data-table" data-ng-click="mactrl.sidebarStat($event)" href="tables/data-table">Data Tables</a></li></ul></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;form&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-collection-text"></i> Forms</a><ul><li><a data-ui-sref-active="active" data-ui-sref="form.basic-form-elements" data-ng-click="mactrl.sidebarStat($event)" href="form/basic-form-elements">Basic Form Elements</a></li><li><a data-ui-sref-active="active" data-ui-sref="form.form-components" data-ng-click="mactrl.sidebarStat($event)" href="form/form-components">Form Components</a></li><li><a data-ui-sref-active="active" data-ui-sref="form.form-examples" data-ng-click="mactrl.sidebarStat($event)" href="form/form-examples">Form Examples</a></li><li><a data-ui-sref-active="active" data-ui-sref="form.form-validations" data-ng-click="mactrl.sidebarStat($event)" href="form/form-validations">Form Validation</a></li></ul></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;user-interface&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-swap-alt"></i>User Interface</a><ul><li><a data-ui-sref-active="active" data-ui-sref="user-interface.ui-bootstrap" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/ui-bootstrap">UI Bootstrap</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.colors" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/colors">Colors</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.animations" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/animations">Animations</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.box-shadow" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/box-shadow">Box Shadow</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.buttons" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/buttons">Buttons</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.icons" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/icons">Icons</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.alerts" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/alerts">Alerts</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.preloaders" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/preloaders">Preloaders</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.notifications-dialogs" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/notifications-dialogs">Notifications &amp; Dialogs</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.media" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/media">Media</a></li><li><a data-ui-sref-active="active" data-ui-sref="user-interface.other-components" data-ng-click="mactrl.sidebarStat($event)" href="user-interface/other-components">Others</a></li></ul></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;charts&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-trending-up"></i>Charts</a><ul><li><a data-ui-sref-active="active" data-ui-sref="charts.flot-charts" data-ng-click="mactrl.sidebarStat($event)" href="js/modules/charts/flot-charts">Flot Charts</a></li><li><a data-ui-sref-active="active" data-ui-sref="charts.other-charts" data-ng-click="mactrl.sidebarStat($event)" href="js/modules/charts/other-charts.js">Other Charts</a></li></ul></li><li data-ui-sref-active="active"><a data-ui-sref="calendar" data-ng-click="mactrl.sidebarStat($event)" href="calendar"><i class="zmdi zmdi-calendar"></i> Calendar</a></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;photo-gallery&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-image"></i>Photo Gallery</a><ul><li><a data-ui-sref-active="active" data-ui-sref="photo-gallery.photos" data-ng-click="mactrl.sidebarStat($event)" href="photo-gallery/photos">Default</a></li><li><a data-ui-sref-active="active" data-ui-sref="photo-gallery.timeline" data-ng-click="mactrl.sidebarStat($event)" href="photo-gallery/timeline">Timeline</a></li></ul></li><li data-ui-sref-active="active"><a data-ui-sref="generic-classes" data-ng-click="mactrl.sidebarStat($event)" href="generic-classes"><i class="zmdi zmdi-layers"></i> Generic Classes</a></li><li class="sub-menu" data-ng-class="{ &#39;active toggled&#39;: mactrl.$state.includes(&#39;pages&#39;) }"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-collection-item"></i> Sample Pages</a><ul><li><a data-ui-sref-active="active" data-ui-sref="pages.profile.profile-about" data-ng-click="mactrl.sidebarStat($event)" href="pages/profile/profile-about">Profile</a></li><li><a data-ui-sref-active="active" data-ui-sref="pages.listview" data-ng-click="mactrl.sidebarStat($event)" href="pages/listview">List View</a></li><li><a data-ui-sref-active="active" data-ui-sref="pages.messages" data-ng-click="mactrl.sidebarStat($event)" href="pages/messages">Messages</a></li><li><a data-ui-sref-active="active" data-ui-sref="pages.pricing-table" data-ng-click="mactrl.sidebarStat($event)" href="pages/pricing-table">Pricing Table</a></li><li><a data-ui-sref-active="active" data-ui-sref="pages.contacts" data-ng-click="mactrl.sidebarStat($event)" href="pages/contacts">Contacts</a></li><li><a data-ui-sref-active="active" data-ui-sref="pages.invoice" data-ng-click="mactrl.sidebarStat($event)" href="pages/invoice">Invoice</a></li><li><a data-ui-sref-active="active" data-ui-sref="pages.wall" data-ng-click="mactrl.sidebarStat($event)" href="pages/wall">Wall</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/login.html">Login and Sign Up</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/lockscreen.html">Lockscreen</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/404.html">Error 404</a></li></ul></li><li class="sub-menu"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu=""><i class="zmdi zmdi-menu"></i> 3 Level Menu</a><ul><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Level 2 link</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Another level 2 Link</a></li><li class="sub-menu"><a href="http://byrushan.com/projects/ma/1-5-2/angular/" toggle-submenu="">I have children too</a><ul><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Level 3 link</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Another Level 3 link</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Third one</a></li></ul></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">One more 2</a></li></ul></li><li><a href="https://wrapbootstrap.com/theme/material-admin-responsive-angularjs-WB011H985"><i class="zmdi zmdi-money"></i> Buy this template</a></li></ul></div></div><div id="mCSB_2_scrollbar_vertical" class="mCSB_scrollTools mCSB_2_scrollbar mCS-minimal-dark mCSB_scrollTools_vertical" style="display: block;"><div class="mCSB_draggerContainer"><div id="mCSB_2_dragger_vertical" class="mCSB_dragger" style="position: absolute; min-height: 50px; display: block; height: 302px; max-height: 481px; top: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar" style="line-height: 50px;"></div></div><div class="mCSB_draggerRail"></div></div></div><div id="mCSB_2_scrollbar_horizontal" class="mCSB_scrollTools mCSB_2_scrollbar mCS-minimal-dark mCSB_scrollTools_horizontal" style="display: none;"><div class="mCSB_draggerContainer"><div id="mCSB_2_dragger_horizontal" class="mCSB_dragger" style="position: absolute; min-width: 50px; width: 0px; left: 0px;" oncontextmenu="return false;"><div class="mCSB_dragger_bar"></div></div><div class="mCSB_draggerRail"></div></div></div></div></aside>

    <!-- ngInclude: 'template/chat.html' --><aside id="chat" data-ng-include="&#39;template/chat.html&#39;" data-ng-class="{ &#39;toggled&#39;: mactrl.sidebarToggle.right === true }" class="ng-scope"><div class="chat-search ng-scope"><div class="fg-line"><input type="text" class="form-control" placeholder="Search People"></div></div><div class="listview ng-scope"><a class="lv-item" href="http://byrushan.com/projects/ma/1-5-2/angular/"><div class="media"><div class="pull-left p-relative"><img class="lv-img-sm" src="themes_files/2.jpg" alt=""> <i class="chat-status-busy"></i></div><div class="media-body"><div class="lv-title">Jonathan Morris</div><small class="lv-small">Available</small></div></div></a> <a class="lv-item" href="http://byrushan.com/projects/ma/1-5-2/angular/"><div class="media"><div class="pull-left"><img class="lv-img-sm" src="themes_files/1.jpg" alt=""></div><div class="media-body"><div class="lv-title">David Belle</div><small class="lv-small">Last seen 3 hours ago</small></div></div></a> <a class="lv-item" href="http://byrushan.com/projects/ma/1-5-2/angular/"><div class="media"><div class="pull-left p-relative"><img class="lv-img-sm" src="themes_files/3.jpg" alt=""> <i class="chat-status-online"></i></div><div class="media-body"><div class="lv-title">Fredric Mitchell Jr.</div><small class="lv-small">Availble</small></div></div></a> <a class="lv-item" href="http://byrushan.com/projects/ma/1-5-2/angular/"><div class="media"><div class="pull-left p-relative"><img class="lv-img-sm" src="themes_files/4.jpg" alt=""> <i class="chat-status-online"></i></div><div class="media-body"><div class="lv-title">Glenn Jecobs</div><small class="lv-small">Availble</small></div></div></a> <a class="lv-item" href="http://byrushan.com/projects/ma/1-5-2/angular/"><div class="media"><div class="pull-left"><img class="lv-img-sm" src="themes_files/5.jpg" alt=""></div><div class="media-body"><div class="lv-title">Bill Phillips</div><small class="lv-small">Last seen 3 days ago</small></div></div></a> <a class="lv-item" href="http://byrushan.com/projects/ma/1-5-2/angular/"><div class="media"><div class="pull-left"><img class="lv-img-sm" src="themes_files/6.jpg" alt=""></div><div class="media-body"><div class="lv-title">Wendy Mitchell</div><small class="lv-small">Last seen 2 minutes ago</small></div></div></a> <a class="lv-item" href="http://byrushan.com/projects/ma/1-5-2/angular/"><div class="media"><div class="pull-left p-relative"><img class="lv-img-sm" src="themes_files/7.jpg" alt=""> <i class="chat-status-busy"></i></div><div class="media-body"><div class="lv-title">Teena Bell Ann</div><small class="lv-small">Busy</small></div></div></a></div></aside>

    <section id="content">

        <div class="container">
            <div class="block-header">
                <h2>Dashboard</h2>

                <ul class="actions">
                    <li>
                        <a href="http://byrushan.com/projects/ma/1-5-2/angular/">
                            <i class="zmdi zmdi-trending-up"></i>
                        </a>
                    </li>
                    <li>
                        <a href="http://byrushan.com/projects/ma/1-5-2/angular/">
                            <i class="zmdi zmdi-check-all"></i>
                        </a>
                    </li>
                    <li class="dropdown" uib-dropdown="">
                        <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                            <i class="zmdi zmdi-more-vert"></i>
                        </a>

                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Refresh</a>
                            </li>
                            <li>
                                <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Manage Widgets</a>
                            </li>
                            <li>
                                <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Widgets Settings</a>
                            </li>
                        </ul>
                    </li>
                </ul>

            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Sales Statistics <small>Vestibulum purus quam scelerisque, mollis nonummy metus</small></h2>

                    <ul class="actions">
                        <li>
                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">
                                <i class="zmdi zmdi-refresh-alt"></i>
                            </a>
                        </li>
                        <li>
                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">
                                <i class="zmdi zmdi-download"></i>
                            </a>
                        </li>
                        <li class="dropdown" uib-dropdown="">
                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                <i class="zmdi zmdi-more-vert"></i>
                            </a>

                            <ul class="dropdown-menu dropdown-menu-right">
                                <li>
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Change Date Range</a>
                                </li>
                                <li>
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Change Graph Type</a>
                                </li>
                                <li>
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Other Settings</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>

                <div class="card-body">
                    <div class="chart-edge">
                        <div class="flot-chart" data-curvedline-chart="" style="padding: 0px; position: relative;"><canvas class="flot-base" width="986" height="200" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 986px; height: 200px;"></canvas><canvas class="flot-overlay" width="986" height="200" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 986px; height: 200px;"></canvas></div>
                    </div>
                </div>
            </div>

            <div class="mini-charts">
                <div class="row">
                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-cyan">
                            <div class="clearfix">
                                <div class="chart stats-bar" data-sparkline-bar=""><canvas width="83" height="45" style="display: inline-block; width: 83px; height: 45px; vertical-align: top;"></canvas></div>
                                <div class="count">
                                    <small>Website Traffics</small>
                                    <h2>987,459</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-lightgreen">
                            <div class="clearfix">
                                <div class="chart stats-bar-2" data-sparkline-bar=""><canvas width="83" height="45" style="display: inline-block; width: 83px; height: 45px; vertical-align: top;"></canvas></div>
                                <div class="count">
                                    <small>Website Impressions</small>
                                    <h2>356,785K</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-orange">
                            <div class="clearfix">
                                <div class="chart stats-line" data-sparkline-line=""><canvas width="85" height="45" style="display: inline-block; width: 85px; height: 45px; vertical-align: top;"></canvas></div>
                                <div class="count">
                                    <small>Total Sales</small>
                                    <h2>$ 458,778</h2>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-bluegray">
                            <div class="clearfix">
                                <div class="chart stats-line-2" data-sparkline-line=""><canvas width="85" height="45" style="display: inline-block; width: 85px; height: 45px; vertical-align: top;"></canvas></div>
                                <div class="count">
                                    <small>Support Tickets</small>
                                    <h2>23,856</h2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div class="dash-widgets">
                <div class="row">
                    <div class="col-md-3 col-sm-6">
                        <div id="site-visits" class="dash-widget-item bgm-teal">
                            <div class="dash-widget-header">
                                <div class="p-20">
                                    <div class="dash-widget-visits" data-sparkline-line=""><canvas width="180" height="95" style="display: inline-block; width: 180px; height: 95px; vertical-align: top;"></canvas></div>
                                </div>

                                <div class="dash-widget-title">For the past 30 days</div>

                                <ul class="actions actions-alt">
                                    <li class="dropdown" uib-dropdown="">
                                        <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                            <i class="zmdi zmdi-more-vert"></i>
                                        </a>

                                        <ul class="dropdown-menu dropdown-menu-right">
                                            <li>
                                                <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Refresh</a>
                                            </li>
                                            <li>
                                                <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Manage Widgets</a>
                                            </li>
                                            <li>
                                                <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Widgets Settings</a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>

                            <div class="p-20">

                                <small>Page Views</small>
                                <h3 class="m-0 f-400">47,896,536</h3>

                                <br>

                                <small>Site Visitors</small>
                                <h3 class="m-0 f-400">24,456,799</h3>

                                <br>

                                <small>Total Clicks</small>
                                <h3 class="m-0 f-400">13,965</h3>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3 col-sm-6">
                        <div id="pie-charts" class="dash-widget-item">
                            <div class="bgm-pink">
                                <div class="dash-widget-header">
                                    <div class="dash-widget-title">Email Statistics</div>
                                </div>

                                <div class="clearfix"></div>

                                <div class="text-center p-20 m-t-25">
                                    <div class="easy-pie main-pie" data-percent="75" data-easypie-chart="">
                                        <div class="percent">45</div>
                                        <div class="pie-title">Total Emails Sent</div>
                                    <canvas height="148" width="148"></canvas></div>
                                </div>
                            </div>

                            <div class="p-t-20 p-b-20 text-center">
                                <div class="easy-pie sub-pie-1" data-percent="56" data-easypie-chart="">
                                    <div class="percent">56</div>
                                    <div class="pie-title">Bounce Rate</div>
                                <canvas height="95" width="95"></canvas></div>
                                <div class="easy-pie sub-pie-2" data-percent="84" data-easypie-chart="">
                                    <div class="percent">84</div>
                                    <div class="pie-title">Total Opened</div>
                                <canvas height="95" width="95"></canvas></div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3 col-sm-6">
                        <div class="dash-widget-item bgm-lime">
                            <div id="weather-widget" data-weather-widget=""><div class="weather-status">87°F</div><ul class="weather-info"><li>Austin,  TX</li><li class="currently">Clear</li></ul><div class="weather-icon wi-31"></div><div class="dash-widget-footer"><div class="weather-list tomorrow"><span class="weather-list-icon wi-34"></span><span>98/77</span><span>Mostly Sunny</span></div><div class="weather-list after-tomorrow"><span class="weather-list-icon wi-34"></span><span>99/78</span><span>Mostly Sunny</span></div></div></div>
                        </div>
                    </div>

                    <div class="col-md-3 col-sm-6">
                        <div id="best-selling" class="dash-widget-item ng-scope" data-ng-controller="bestsellingCtrl as wctrl">
                            <div class="dash-widget-header">
                                <div class="dash-widget-title">Best Sellings</div>
                                <img src="themes_files/alpha.jpg" alt="">
                                <div class="main-item">
                                    <small>Samsung Galaxy Alpha</small>
                                    <h2>$799.99</h2>
                                </div>
                            </div>

                            <div class="listview p-t-5">
                                <!-- ngRepeat: w in wctrl.bsResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in wctrl.bsResult.list" style="">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="img/widgets/note4.jpg" alt="" src="themes_files/note4.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">Samsung Galaxy Note 4</div>
                                            <small class="lv-small ng-binding">$850.00 - $1199.99</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in wctrl.bsResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in wctrl.bsResult.list" style="">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="img/widgets/mate7.jpg" alt="" src="themes_files/mate7.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">Huawei Ascend Mate</div>
                                            <small class="lv-small ng-binding">$649.59 - $749.99</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in wctrl.bsResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in wctrl.bsResult.list">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="img/widgets/535.jpg" alt="" src="themes_files/535.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">Nokia Lumia 535</div>
                                            <small class="lv-small ng-binding">$189.99 - $250.00</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in wctrl.bsResult.list -->

                                <div class="clearfix"></div>

                                <a class="lv-footer" href="http://byrushan.com/projects/ma/1-5-2/angular/">
                                    View All
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-6">
                    <!-- Recent Items -->
                    <div class="card ng-scope" data-ng-controller="recentitemCtrl as rictrl">
                        <div class="card-header">
                            <h2>Recent Items <small>Phasellus condimentum ipsum id auctor imperdie</small></h2>
                            <ul class="actions">
                                <li class="dropdown" uib-dropdown="">
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                        <i class="zmdi zmdi-more-vert"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Refresh</a>
                                        </li>
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Settings</a>
                                        </li>
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Other Settings</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                        <div class="card-body m-t-0">
                            <table class="table table-inner table-vmiddle">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th style="width: 60px">Price</th>
                                </tr>
                                </thead>
                                <tbody>
                                <!-- ngRepeat: w in rictrl.riResult.list --><tr data-ng-repeat="w in rictrl.riResult.list" class="ng-scope">
                                    <td class="f-500 c-cyan ng-binding">2569</td>
                                    <td class="ng-binding">Samsung Galaxy Mega</td>
                                    <td class="f-500 c-cyan ng-binding">521</td>
                                </tr><!-- end ngRepeat: w in rictrl.riResult.list --><tr data-ng-repeat="w in rictrl.riResult.list" class="ng-scope">
                                    <td class="f-500 c-cyan ng-binding">9658</td>
                                    <td class="ng-binding">Huawei Ascend P6</td>
                                    <td class="f-500 c-cyan ng-binding">440</td>
                                </tr><!-- end ngRepeat: w in rictrl.riResult.list --><tr data-ng-repeat="w in rictrl.riResult.list" class="ng-scope">
                                    <td class="f-500 c-cyan ng-binding">1101</td>
                                    <td class="ng-binding">HTC One M8</td>
                                    <td class="f-500 c-cyan ng-binding">680</td>
                                </tr><!-- end ngRepeat: w in rictrl.riResult.list --><tr data-ng-repeat="w in rictrl.riResult.list" class="ng-scope">
                                    <td class="f-500 c-cyan ng-binding">2569</td>
                                    <td class="ng-binding">Samsung Galaxy Alpha</td>
                                    <td class="f-500 c-cyan ng-binding">870</td>
                                </tr><!-- end ngRepeat: w in rictrl.riResult.list --><tr data-ng-repeat="w in rictrl.riResult.list" class="ng-scope">
                                    <td class="f-500 c-cyan ng-binding">6598</td>
                                    <td class="ng-binding">LG G3</td>
                                    <td class="f-500 c-cyan ng-binding">690</td>
                                </tr><!-- end ngRepeat: w in rictrl.riResult.list -->
                                </tbody>
                            </table>
                        </div>
                        <div id="recent-items-chart" class="flot-chart" data-line-chart="" style="padding: 0px; position: relative;"><canvas class="flot-base" width="488" height="150" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 488px; height: 150px;"></canvas><canvas class="flot-overlay" width="488" height="150" style="direction: ltr; position: absolute; left: 0px; top: 0px; width: 488px; height: 150px;"></canvas></div>
                    </div>

                    <!-- Todo Lists -->
                    <div id="todo-lists" data-ng-controller="todoCtrl as tctrl" class="ng-scope">
                        <div class="tl-header">
                            <h2>Todo Lists</h2>
                            <small>Add, edit and manage your Todo Lists</small>

                            <ul class="actions actions-alt">
                                <li class="dropdown" uib-dropdown="">
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                        <i class="zmdi zmdi-more-vert"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Refresh</a>
                                        </li>
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Manage Widgets</a>
                                        </li>
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Widgets Settings</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                        <div class="clearfix"></div>

                        <div class="tl-body">
                            <div id="add-tl-item" data-ng-class="{ &#39;toggled&#39;: tctrl.addTodoStat }" data-ng-click="tctrl.addTodoStat = true">
                                <i class="add-new-item zmdi zmdi-plus" data-ng-click="tctrl.addTodo($event)"></i>

                                <div class="add-tl-body">
                                    <textarea placeholder="What you want to do..." data-ng-model="tctrl.todo" class="ng-pristine ng-untouched ng-valid"></textarea>

                                    <div class="add-tl-actions">
                                        <a class="zmdi zmdi-close" data-tl-action="dismiss" data-ng-click="tctrl.addTodoStat = false; $event.stopPropagation()"></a>
                                        <a class="zmdi zmdi-check" data-tl-action="save" data-ng-click="tctrl.addTodoStat = false; $event.stopPropagation()"></a>
                                    </div>
                                </div>
                            </div>


                            <!-- ngRepeat: w in tctrl.tdResult.list --><div class="checkbox media ng-scope" data-ng-repeat="w in tctrl.tdResult.list">
                                <div class="pull-right">
                                    <ul class="actions actions-alt">
                                        <li class="dropdown" uib-dropdown="">
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                                <i class="zmdi zmdi-more-vert"></i>
                                            </a>

                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Delete</a></li>
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Archive</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                <div class="media-body">
                                    <label>
                                        <input type="checkbox">
                                        <i class="input-helper"></i>
                                        <span class="ng-binding">Duis vitae nibh molestie pharetra augue vitae</span>
                                    </label>
                                </div>
                            </div><!-- end ngRepeat: w in tctrl.tdResult.list --><div class="checkbox media ng-scope" data-ng-repeat="w in tctrl.tdResult.list">
                                <div class="pull-right">
                                    <ul class="actions actions-alt">
                                        <li class="dropdown" uib-dropdown="">
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                                <i class="zmdi zmdi-more-vert"></i>
                                            </a>

                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Delete</a></li>
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Archive</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                <div class="media-body">
                                    <label>
                                        <input type="checkbox">
                                        <i class="input-helper"></i>
                                        <span class="ng-binding">In vel imperdiet leoorbi mollis leo sit amet quam fringilla varius mauris orci turpis</span>
                                    </label>
                                </div>
                            </div><!-- end ngRepeat: w in tctrl.tdResult.list --><div class="checkbox media ng-scope" data-ng-repeat="w in tctrl.tdResult.list">
                                <div class="pull-right">
                                    <ul class="actions actions-alt">
                                        <li class="dropdown" uib-dropdown="">
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                                <i class="zmdi zmdi-more-vert"></i>
                                            </a>

                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Delete</a></li>
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Archive</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                <div class="media-body">
                                    <label>
                                        <input type="checkbox">
                                        <i class="input-helper"></i>
                                        <span class="ng-binding">Suspendisse quis sollicitudin erosvel dictum nunc</span>
                                    </label>
                                </div>
                            </div><!-- end ngRepeat: w in tctrl.tdResult.list --><div class="checkbox media ng-scope" data-ng-repeat="w in tctrl.tdResult.list">
                                <div class="pull-right">
                                    <ul class="actions actions-alt">
                                        <li class="dropdown" uib-dropdown="">
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                                <i class="zmdi zmdi-more-vert"></i>
                                            </a>

                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Delete</a></li>
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Archive</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                <div class="media-body">
                                    <label>
                                        <input type="checkbox">
                                        <i class="input-helper"></i>
                                        <span class="ng-binding">Curabitur egestas finibus sapien quis faucibusras bibendum ut justo at sagittis. In hac habitasse platea dictumst</span>
                                    </label>
                                </div>
                            </div><!-- end ngRepeat: w in tctrl.tdResult.list --><div class="checkbox media ng-scope" data-ng-repeat="w in tctrl.tdResult.list">
                                <div class="pull-right">
                                    <ul class="actions actions-alt">
                                        <li class="dropdown" uib-dropdown="">
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                                <i class="zmdi zmdi-more-vert"></i>
                                            </a>

                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Delete</a></li>
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Archive</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                <div class="media-body">
                                    <label>
                                        <input type="checkbox">
                                        <i class="input-helper"></i>
                                        <span class="ng-binding">Suspendisse potenti. Cras dolor augue, tincidunt sit amet lorem id, blandit rutrum libero</span>
                                    </label>
                                </div>
                            </div><!-- end ngRepeat: w in tctrl.tdResult.list --><div class="checkbox media ng-scope" data-ng-repeat="w in tctrl.tdResult.list">
                                <div class="pull-right">
                                    <ul class="actions actions-alt">
                                        <li class="dropdown" uib-dropdown="">
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                                <i class="zmdi zmdi-more-vert"></i>
                                            </a>

                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Delete</a></li>
                                                <li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Archive</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                <div class="media-body">
                                    <label>
                                        <input type="checkbox">
                                        <i class="input-helper"></i>
                                        <span class="ng-binding">Proin luctus dictum nisl id auctor. Nullam lobortis condimentum arcu sit amet gravida</span>
                                    </label>
                                </div>
                            </div><!-- end ngRepeat: w in tctrl.tdResult.list -->

                        </div>
                    </div>
                </div>

                <div class="col-sm-6">
                    <!-- Calendar -->
                    <div id="calendar-widget" data-full-calendar="" class="fc fc-ltr ui-widget"><div class="fc-toolbar"><div class="fc-left"></div><div class="fc-right"></div><div class="fc-center"><button type="button" class="fc-prev-button ui-button ui-state-default ui-corner-left ui-corner-right"><span class="ui-icon ui-icon-circle-triangle-w"></span></button><h2>June 2014</h2><button type="button" class="fc-next-button ui-button ui-state-default ui-corner-left ui-corner-right"><span class="ui-icon ui-icon-circle-triangle-e"></span></button></div><div class="fc-clear"></div></div><div class="fc-view-container" style=""><div class="fc-view fc-month-view fc-basic-view"><table><thead class="fc-head"><tr><td class="ui-widget-header"><div class="fc-row ui-widget-header"><table><thead><tr><th class="fc-day-header ui-widget-header fc-sun">Sun</th><th class="fc-day-header ui-widget-header fc-mon">Mon</th><th class="fc-day-header ui-widget-header fc-tue">Tue</th><th class="fc-day-header ui-widget-header fc-wed">Wed</th><th class="fc-day-header ui-widget-header fc-thu">Thu</th><th class="fc-day-header ui-widget-header fc-fri">Fri</th><th class="fc-day-header ui-widget-header fc-sat">Sat</th></tr></thead></table></div></td></tr></thead><tbody class="fc-body"><tr><td class="ui-widget-content"><div class="fc-day-grid-container"><div class="fc-day-grid"><div class="fc-row fc-week ui-widget-content"><div class="fc-bg"><table><tbody><tr><td class="fc-day ui-widget-content fc-sun fc-past" data-date="2014-06-01"></td><td class="fc-day ui-widget-content fc-mon fc-past" data-date="2014-06-02"></td><td class="fc-day ui-widget-content fc-tue fc-past" data-date="2014-06-03"></td><td class="fc-day ui-widget-content fc-wed fc-past" data-date="2014-06-04"></td><td class="fc-day ui-widget-content fc-thu fc-past" data-date="2014-06-05"></td><td class="fc-day ui-widget-content fc-fri fc-past" data-date="2014-06-06"></td><td class="fc-day ui-widget-content fc-sat fc-past" data-date="2014-06-07"></td></tr></tbody></table></div><div class="fc-content-skeleton"><table><thead><tr><td class="fc-day-number fc-sun fc-past" data-date="2014-06-01">1</td><td class="fc-day-number fc-mon fc-past" data-date="2014-06-02">2</td><td class="fc-day-number fc-tue fc-past" data-date="2014-06-03">3</td><td class="fc-day-number fc-wed fc-past" data-date="2014-06-04">4</td><td class="fc-day-number fc-thu fc-past" data-date="2014-06-05">5</td><td class="fc-day-number fc-fri fc-past" data-date="2014-06-06">6</td><td class="fc-day-number fc-sat fc-past" data-date="2014-06-07">7</td></tr></thead><tbody><tr><td class="fc-event-container"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bgm-cyan fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">All Day</span></div><div class="fc-resizer fc-end-resizer"></div></a></td><td></td><td></td><td></td><td></td><td></td><td class="fc-event-container"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-not-end bgm-orange fc-draggable"><div class="fc-content"> <span class="fc-title">Long Event</span></div></a></td></tr></tbody></table></div></div><div class="fc-row fc-week ui-widget-content"><div class="fc-bg"><table><tbody><tr><td class="fc-day ui-widget-content fc-sun fc-past" data-date="2014-06-08"></td><td class="fc-day ui-widget-content fc-mon fc-past" data-date="2014-06-09"></td><td class="fc-day ui-widget-content fc-tue fc-past" data-date="2014-06-10"></td><td class="fc-day ui-widget-content fc-wed fc-past" data-date="2014-06-11"></td><td class="fc-day ui-widget-content fc-thu fc-past" data-date="2014-06-12"></td><td class="fc-day ui-widget-content fc-fri fc-past" data-date="2014-06-13"></td><td class="fc-day ui-widget-content fc-sat fc-past" data-date="2014-06-14"></td></tr></tbody></table></div><div class="fc-content-skeleton"><table><thead><tr><td class="fc-day-number fc-sun fc-past" data-date="2014-06-08">8</td><td class="fc-day-number fc-mon fc-past" data-date="2014-06-09">9</td><td class="fc-day-number fc-tue fc-past" data-date="2014-06-10">10</td><td class="fc-day-number fc-wed fc-past" data-date="2014-06-11">11</td><td class="fc-day-number fc-thu fc-past" data-date="2014-06-12">12</td><td class="fc-day-number fc-fri fc-past" data-date="2014-06-13">13</td><td class="fc-day-number fc-sat fc-past" data-date="2014-06-14">14</td></tr></thead><tbody><tr><td class="fc-event-container" colspan="2"><a class="fc-day-grid-event fc-h-event fc-event fc-not-start fc-end bgm-orange fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">Long Event</span></div><div class="fc-resizer fc-end-resizer"></div></a></td><td rowspan="2"></td><td rowspan="2"></td><td class="fc-event-container"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bgm-gray fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">Lunch</span></div><div class="fc-resizer fc-end-resizer"></div></a></td><td class="fc-event-container" rowspan="2"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bgm-pink fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">Birthday</span></div><div class="fc-resizer fc-end-resizer"></div></a></td><td rowspan="2"></td></tr><tr><td></td><td class="fc-event-container"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bgm-lightgreen fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">Repeat</span></div><div class="fc-resizer fc-end-resizer"></div></a></td><td class="fc-event-container"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bgm-teal fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">Meet</span></div><div class="fc-resizer fc-end-resizer"></div></a></td></tr></tbody></table></div></div><div class="fc-row fc-week ui-widget-content"><div class="fc-bg"><table><tbody><tr><td class="fc-day ui-widget-content fc-sun fc-past" data-date="2014-06-15"></td><td class="fc-day ui-widget-content fc-mon fc-past" data-date="2014-06-16"></td><td class="fc-day ui-widget-content fc-tue fc-past" data-date="2014-06-17"></td><td class="fc-day ui-widget-content fc-wed fc-past" data-date="2014-06-18"></td><td class="fc-day ui-widget-content fc-thu fc-past" data-date="2014-06-19"></td><td class="fc-day ui-widget-content fc-fri fc-past" data-date="2014-06-20"></td><td class="fc-day ui-widget-content fc-sat fc-past" data-date="2014-06-21"></td></tr></tbody></table></div><div class="fc-content-skeleton"><table><thead><tr><td class="fc-day-number fc-sun fc-past" data-date="2014-06-15">15</td><td class="fc-day-number fc-mon fc-past" data-date="2014-06-16">16</td><td class="fc-day-number fc-tue fc-past" data-date="2014-06-17">17</td><td class="fc-day-number fc-wed fc-past" data-date="2014-06-18">18</td><td class="fc-day-number fc-thu fc-past" data-date="2014-06-19">19</td><td class="fc-day-number fc-fri fc-past" data-date="2014-06-20">20</td><td class="fc-day-number fc-sat fc-past" data-date="2014-06-21">21</td></tr></thead><tbody><tr><td></td><td class="fc-event-container"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bgm-blue fc-draggable fc-resizable"><div class="fc-content"> <span class="fc-title">Repeat</span></div><div class="fc-resizer fc-end-resizer"></div></a></td><td></td><td></td><td></td><td></td><td></td></tr></tbody></table></div></div><div class="fc-row fc-week ui-widget-content"><div class="fc-bg"><table><tbody><tr><td class="fc-day ui-widget-content fc-sun fc-past" data-date="2014-06-22"></td><td class="fc-day ui-widget-content fc-mon fc-past" data-date="2014-06-23"></td><td class="fc-day ui-widget-content fc-tue fc-past" data-date="2014-06-24"></td><td class="fc-day ui-widget-content fc-wed fc-past" data-date="2014-06-25"></td><td class="fc-day ui-widget-content fc-thu fc-past" data-date="2014-06-26"></td><td class="fc-day ui-widget-content fc-fri fc-past" data-date="2014-06-27"></td><td class="fc-day ui-widget-content fc-sat fc-past" data-date="2014-06-28"></td></tr></tbody></table></div><div class="fc-content-skeleton"><table><thead><tr><td class="fc-day-number fc-sun fc-past" data-date="2014-06-22">22</td><td class="fc-day-number fc-mon fc-past" data-date="2014-06-23">23</td><td class="fc-day-number fc-tue fc-past" data-date="2014-06-24">24</td><td class="fc-day-number fc-wed fc-past" data-date="2014-06-25">25</td><td class="fc-day-number fc-thu fc-past" data-date="2014-06-26">26</td><td class="fc-day-number fc-fri fc-past" data-date="2014-06-27">27</td><td class="fc-day-number fc-sat fc-past" data-date="2014-06-28">28</td></tr></thead><tbody><tr><td></td><td></td><td></td><td></td><td></td><td></td><td class="fc-event-container"><a class="fc-day-grid-event fc-h-event fc-event fc-start fc-end bgm-bluegray fc-draggable fc-resizable" href="http://google.com/"><div class="fc-content"> <span class="fc-title">Google</span></div><div class="fc-resizer fc-end-resizer"></div></a></td></tr></tbody></table></div></div><div class="fc-row fc-week ui-widget-content"><div class="fc-bg"><table><tbody><tr><td class="fc-day ui-widget-content fc-sun fc-past" data-date="2014-06-29"></td><td class="fc-day ui-widget-content fc-mon fc-past" data-date="2014-06-30"></td><td class="fc-day ui-widget-content fc-tue fc-other-month fc-past" data-date="2014-07-01"></td><td class="fc-day ui-widget-content fc-wed fc-other-month fc-past" data-date="2014-07-02"></td><td class="fc-day ui-widget-content fc-thu fc-other-month fc-past" data-date="2014-07-03"></td><td class="fc-day ui-widget-content fc-fri fc-other-month fc-past" data-date="2014-07-04"></td><td class="fc-day ui-widget-content fc-sat fc-other-month fc-past" data-date="2014-07-05"></td></tr></tbody></table></div><div class="fc-content-skeleton"><table><thead><tr><td class="fc-day-number fc-sun fc-past" data-date="2014-06-29">29</td><td class="fc-day-number fc-mon fc-past" data-date="2014-06-30">30</td><td class="fc-day-number fc-tue fc-other-month fc-past" data-date="2014-07-01">1</td><td class="fc-day-number fc-wed fc-other-month fc-past" data-date="2014-07-02">2</td><td class="fc-day-number fc-thu fc-other-month fc-past" data-date="2014-07-03">3</td><td class="fc-day-number fc-fri fc-other-month fc-past" data-date="2014-07-04">4</td><td class="fc-day-number fc-sat fc-other-month fc-past" data-date="2014-07-05">5</td></tr></thead><tbody><tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr></tbody></table></div></div><div class="fc-row fc-week ui-widget-content"><div class="fc-bg"><table><tbody><tr><td class="fc-day ui-widget-content fc-sun fc-other-month fc-past" data-date="2014-07-06"></td><td class="fc-day ui-widget-content fc-mon fc-other-month fc-past" data-date="2014-07-07"></td><td class="fc-day ui-widget-content fc-tue fc-other-month fc-past" data-date="2014-07-08"></td><td class="fc-day ui-widget-content fc-wed fc-other-month fc-past" data-date="2014-07-09"></td><td class="fc-day ui-widget-content fc-thu fc-other-month fc-past" data-date="2014-07-10"></td><td class="fc-day ui-widget-content fc-fri fc-other-month fc-past" data-date="2014-07-11"></td><td class="fc-day ui-widget-content fc-sat fc-other-month fc-past" data-date="2014-07-12"></td></tr></tbody></table></div><div class="fc-content-skeleton"><table><thead><tr><td class="fc-day-number fc-sun fc-other-month fc-past" data-date="2014-07-06">6</td><td class="fc-day-number fc-mon fc-other-month fc-past" data-date="2014-07-07">7</td><td class="fc-day-number fc-tue fc-other-month fc-past" data-date="2014-07-08">8</td><td class="fc-day-number fc-wed fc-other-month fc-past" data-date="2014-07-09">9</td><td class="fc-day-number fc-thu fc-other-month fc-past" data-date="2014-07-10">10</td><td class="fc-day-number fc-fri fc-other-month fc-past" data-date="2014-07-11">11</td><td class="fc-day-number fc-sat fc-other-month fc-past" data-date="2014-07-12">12</td></tr></thead><tbody><tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr></tbody></table></div></div></div></div></td></tr></tbody></table></div></div></div>

                    <!-- Recent Posts -->
                    <div class="card ng-scope" data-ng-controller="recentpostCtrl as rpctrl">
                        <div class="card-header ch-alt m-b-20">
                            <h2>Recent Posts <small>Phasellus condimentum ipsum id auctor imperdie</small></h2>
                            <ul class="actions">
                                <li>
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/">
                                        <i class="zmdi zmdi-refresh-alt"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/">
                                        <i class="zmdi zmdi-download"></i>
                                    </a>
                                </li>
                                <li class="dropdown" uib-dropdown="">
                                    <a href="http://byrushan.com/projects/ma/1-5-2/angular/" uib-dropdown-toggle="" class="dropdown-toggle" aria-haspopup="true" aria-expanded="false">
                                        <i class="zmdi zmdi-more-vert"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Change Date Range</a>
                                        </li>
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Change Graph Type</a>
                                        </li>
                                        <li>
                                            <a href="http://byrushan.com/projects/ma/1-5-2/angular/">Other Settings</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>

                            <button class="btn bgm-cyan btn-float waves-effect waves-circle"><i class="zmdi zmdi-plus"></i></button>
                        </div>

                        <div class="card-body">
                            <div class="listview">

                                <!-- ngRepeat: w in rpctrl.rpResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in rpctrl.rpResult.list" style="">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="public/img/profile-pics/1.jpg" alt="" src="themes_files/1.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">David Belle</div>
                                            <small class="lv-small ng-binding">Cum sociis natoque penatibus et magnis dis parturient montes</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in rpctrl.rpResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in rpctrl.rpResult.list" style="">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="public/img/profile-pics/2.jpg" alt="" src="themes_files/2.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">Jonathan Morris</div>
                                            <small class="lv-small ng-binding">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in rpctrl.rpResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in rpctrl.rpResult.list">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="img/profile-pics/3.jpg" alt="" src="themes_files/3.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">Fredric Mitchell Jr</div>
                                            <small class="lv-small ng-binding">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in rpctrl.rpResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in rpctrl.rpResult.list">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="img/profile-pics/4.jpg" alt="" src="themes_files/4.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">Glenn Jecobs</div>
                                            <small class="lv-small ng-binding">Ut vitae lacus sem ellentesque maximus, nunc sit amet varius dignissim, dui est consectetur neque</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in rpctrl.rpResult.list --><a class="lv-item ng-scope" href="http://byrushan.com/projects/ma/1-5-2/angular/" data-ng-repeat="w in rpctrl.rpResult.list">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="img/profile-pics/5.jpg" alt="" src="themes_files/5.jpg">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title ng-binding">Bill Phillips</div>
                                            <small class="lv-small ng-binding">Proin laoreet commodo eros id faucibus. Donec ligula quam, imperdiet vel ante placerat</small>
                                        </div>
                                    </div>
                                </a><!-- end ngRepeat: w in rpctrl.rpResult.list -->

                                <div class="clearfix"></div>

                                <a class="lv-footer" href="http://byrushan.com/projects/ma/1-5-2/angular/">View All</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>

<!-- ngInclude: 'template/footer.html' --><footer id="footer" data-ng-include="&#39;template/footer.html&#39;" class="ng-scope"><span class="ng-scope">Copyright © 2015 Material Admin</span><ul class="f-menu ng-scope"><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Home</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Dashboard</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Reports</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Support</a></li><li><a href="http://byrushan.com/projects/ma/1-5-2/angular/">Contact</a></li></ul></footer>
</data>

        <!-- Older IE warning message -->
        <!--[if lt IE 9]>
            <div class="ie-warning">
                <h1 class="c-white">Warning!!</h1>
                <p>You are using an outdated version of Internet Explorer, please upgrade <br/>to any of the following web browsers to access this website.</p>
                <div class="iew-container">
                    <ul class="iew-download">
                        <li>
                            <a href="http://www.google.com/chrome/">
                                <img src="img/browsers/chrome.png" alt="">
                                <div>Chrome</div>
                            </a>
                        </li>
                        <li>
                            <a href="https://www.mozilla.org/en-US/firefox/new/">
                                <img src="img/browsers/firefox.png" alt="">
                                <div>Firefox</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://www.opera.com">
                                <img src="img/browsers/opera.png" alt="">
                                <div>Opera</div>
                            </a>
                        </li>
                        <li>
                            <a href="https://www.apple.com/safari/">
                                <img src="img/browsers/safari.png" alt="">
                                <div>Safari</div>
                            </a>
                        </li>
                        <li>
                            <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">
                                <img src="img/browsers/ie.png" alt="">
                                <div>IE (New)</div>
                            </a>
                        </li>
                    </ul>
                </div>
                <p>Sorry for the inconvenience!</p>
            </div>
        <![endif]-->


        <!-- Core -->
        <script src="public/vendors/bower_components/jquery/dist/jquery.min.js"></script>

        <!-- Angular -->
        <script src="public/vendors/bower_components/angular/angular.min.js"></script>
        <script src="public/vendors/bower_components/angular-animate/angular-animate.min.js"></script>
        <script src="public/vendors/bower_components/angular-resource/angular-resource.min.js"></script>
        
        <!-- Angular Modules -->
        <script src="public/vendors/bower_components/angular-ui-router/release/angular-ui-router.min.js"></script>
        <script src="public/vendors/bower_components/angular-loading-bar/src/loading-bar.js"></script>
        <script src="public/vendors/bower_components/oclazyload/dist/ocLazyLoad.min.js"></script>
        <script src="public/vendors/bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>

        <!-- Common Vendors -->
        <script src="public/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
        <script src="public/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.min.js"></script>
        <script src="public/vendors/bower_components/Waves/dist/waves.min.js"></script>
        <script src="public/vendors/bootstrap-growl/bootstrap-growl.min.js"></script>
        <script src="public/vendors/bower_components/ng-table/dist/ng-table.min.js"></script>
       

        <!-- Placeholder for IE9 -->
        <!--[if IE 9 ]>
            <script src="vendors/bower_components/jquery-placeholder/jquery.placeholder.min.js"></script>
        <![endif]-->


        <!-- Using below vendors in order to avoid misloading on resolve -->
        <script src="public/vendors/bower_components/flot/jquery.flot.js"></script>
        <script src="public/vendors/bower_components/flot.curvedlines/curvedLines.js"></script>
        <script src="public/vendors/bower_components/flot/jquery.flot.resize.js"></script>
        <script src="public/vendors/bower_components/moment/min/moment.min.js"></script>
        <script src="public/vendors/bower_components/fullcalendar/dist/fullcalendar.min.js"></script>
        <script src="public/vendors/bower_components/flot-orderBars/js/jquery.flot.orderBars.js"></script>
        <script src="public/vendors/bower_components/flot/jquery.flot.pie.js"></script>
        <script src="public/vendors/bower_components/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
        <script src="public/vendors/bower_components/angular-nouislider/src/nouislider.min.js"></script>
        
        
        <!-- App level -->
        <script src="public/js/app.js"></script>
        <script src="public/js/config.js"></script>
        <script src="public/js/controllers/main.js"></script>
        <script src="public/js/services.js"></script>
        <script src="public/js/templates.js"></script>
        <script src="public/js/controllers/ui-bootstrap.js"></script>
        <script src="public/js/controllers/table.js"></script>


        <!-- Template Modules -->
        <script src="public/js/modules/template.js"></script>
        <script src="public/js/modules/ui.js"></script>
        <script src="public/js/modules/charts/flot.js"></script>
        <script src="public/js/modules/charts/other-charts.js"></script>
        <script src="public/js/modules/form.js"></script>
        <script src="public/js/modules/media.js"></script>
        <script src="public/js/modules/components.js"></script>
        <script src="public/js/modules/calendar.js"></script>
        <script src="public/js/modules/demo.js"></script>



<div>
<div class="sweet-overlay" tabindex="-1"></div>
<div class="sweet-alert" tabindex="-1">
<div class="icon error">
    <span class="x-mark">
        <span class="line left">
    
            </span>
                <span class="line right"></span>
            </span>
        </div>
<div class="icon warning"> 
<span class="body"></span> <span class="dot"></span> 
</div> <div class="icon info"></div> <div class="icon success"> 
    <span class="line tip"></span> <span class="line long"></span> 
    <div class="placeholder"></div> <div class="fix"></div> </div> 
    <div class="icon custom"></div> <h2>Title</h2><p class="lead text-muted">Text</p><p>
    <button class="cancel btn btn-lg" tabindex="2">Cancel</button> 
    <button class="confirm btn btn-lg" tabindex="1">OK</button></p></div>
</div>
    <div class="flot-tooltip" style="top: 335px; left: 365px; display: none;">Product 1 of 0.65 = 19.76</div>
    </body>
</html>