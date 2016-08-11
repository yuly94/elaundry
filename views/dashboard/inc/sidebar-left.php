<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

?>
<aside id="sidebar" 
           
        data-ng-class="{ 'toggled': mactrl.sidebarToggle.left === true }">
     
<div class="sidebar-inner c-overflow">    
    <div class="profile-menu">
        <a href='' toggle-submenu>
            <div class="profile-pic">
                <img src="<?php echo URL; ?>public/img/profile-pics/1.jpg" alt="">
            </div>

            <div class="profile-info">
                 <?php echo Session::get('username'); ?> 

                <i class="zmdi zmdi-caret-down"></i>
            </div>
        </a>

        <ul class="main-menu">
            <li>
                <a href="<?php echo URL; ?>dashboard/profile" data-ng-click="mactrl.sidebarStat($event)">
                    <i class="zmdi zmdi-account"></i> View Profile</a>
            </li>
            <li>
                <a href=""><i class="zmdi zmdi-input-antenna"></i> Privacy Settings</a>
            </li>
            <li>
                <a href=""><i class="zmdi zmdi-settings"></i> Settings</a>
            </li>
            <li>
                <a href="<?php echo URL; ?>dashboard/logout"><i class="zmdi zmdi-time-restore"></i> Logout</a>
            </li>
        </ul>
    </div>

    <ul class="main-menu">
        
        <li data-ui-sref-active="active">
            <a data-ui-sref="<?php echo URL; ?>dashboard" data-ng-click="mactrl.sidebarStat($event)"><i class="zmdi zmdi-home"></i> Home</a>
        </li>
        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('headers') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-view-compact"></i> Headers</a>

            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="headers.textual-menu" data-ng-click="mactrl.sidebarStat($event)">Textual menu</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="headers.image-logo" data-ng-click="mactrl.sidebarStat($event)">Image logo</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="headers.mainmenu-on-top" data-ng-click="mactrl.sidebarStat($event)">Mainmenu on top</a></li>
            </ul>
        </li>
        <li data-ui-sref-active="active">
            <a data-ui-sref="typography" data-ng-click="mactrl.sidebarStat($event)"><i class="zmdi zmdi-format-underlined"></i> Typography</a>
        </li>
        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('widgets') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-widgets"></i> Widgets</a>

            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="widgets.widget-templates" data-ng-click="mactrl.sidebarStat($event)">Templates</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="widgets.widgets" data-ng-click="mactrl.sidebarStat($event)">Widgets</a></li>
            </ul>
        </li>
        
        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('tables') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-view-list"></i> Tables</a>

            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="tables.tables" data-ng-click="mactrl.sidebarStat($event)">Tables</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="tables.data-table" data-ng-click="mactrl.sidebarStat($event)">Data Tables</a></li>
            </ul>
        </li>
        
        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('form') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-collection-text"></i> Forms</a>

            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="form.basic-form-elements" data-ng-click="mactrl.sidebarStat($event)">Basic Form Elements</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="form.form-components" data-ng-click="mactrl.sidebarStat($event)">Form Components</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="form.form-examples" data-ng-click="mactrl.sidebarStat($event)">Form Examples</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="form.form-validations" data-ng-click="mactrl.sidebarStat($event)">Form Validation</a></li>
            </ul>
        </li>

        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('user-interface') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-swap-alt"></i>User Interface</a>
            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.ui-bootstrap" data-ng-click="mactrl.sidebarStat($event)">UI Bootstrap</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.colors" data-ng-click="mactrl.sidebarStat($event)">Colors</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.animations" data-ng-click="mactrl.sidebarStat($event)">Animations</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.box-shadow" data-ng-click="mactrl.sidebarStat($event)">Box Shadow</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.buttons" data-ng-click="mactrl.sidebarStat($event)">Buttons</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.icons" data-ng-click="mactrl.sidebarStat($event)">Icons</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.alerts" data-ng-click="mactrl.sidebarStat($event)">Alerts</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.preloaders" data-ng-click="mactrl.sidebarStat($event)">Preloaders</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.notifications-dialogs" data-ng-click="mactrl.sidebarStat($event)">Notifications & Dialogs</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.media" data-ng-click="mactrl.sidebarStat($event)">Media</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="user-interface.other-components" data-ng-click="mactrl.sidebarStat($event)">Others</a></li>
            </ul>
        </li>
        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('charts') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-trending-up"></i>Charts</a>
            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="charts.flot-charts" data-ng-click="mactrl.sidebarStat($event)">Flot Charts</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="charts.other-charts" data-ng-click="mactrl.sidebarStat($event)">Other Charts</a></li>
            </ul>
        </li>

        <li data-ui-sref-active="active">
            <a data-ui-sref="calendar" data-ng-click="mactrl.sidebarStat($event)"><i class="zmdi zmdi-calendar"></i> Calendar</a>
        </li>
        
        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('photo-gallery') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-image"></i>Photo Gallery</a>
            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="photo-gallery.photos" data-ng-click="mactrl.sidebarStat($event)">Default</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="photo-gallery.timeline" data-ng-click="mactrl.sidebarStat($event)">Timeline</a></li>
            </ul>
        </li>

        
        <li data-ui-sref-active="active">
            <a data-ui-sref="generic-classes" data-ng-click="mactrl.sidebarStat($event)"><i class="zmdi zmdi-layers"></i> Generic Classes</a>
        </li>

        <li class="sub-menu" data-ng-class="{ 'active toggled': mactrl.$state.includes('pages') }">
            <a href="" toggle-submenu><i class="zmdi zmdi-collection-item"></i> Sample Pages</a>
            <ul>
                <li><a data-ui-sref-active="active" data-ui-sref="pages.profile.profile-about" data-ng-click="mactrl.sidebarStat($event)">Profile</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="pages.listview" data-ng-click="mactrl.sidebarStat($event)">List View</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="pages.messages" data-ng-click="mactrl.sidebarStat($event)">Messages</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="pages.pricing-table" data-ng-click="mactrl.sidebarStat($event)">Pricing Table</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="pages.contacts" data-ng-click="mactrl.sidebarStat($event)">Contacts</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="pages.invoice" data-ng-click="mactrl.sidebarStat($event)">Invoice</a></li>
                <li><a data-ui-sref-active="active" data-ui-sref="pages.wall" data-ng-click="mactrl.sidebarStat($event)">Wall</a></li>
                <li><a href="login.html">Login and Sign Up</a></li>
                <li><a href="lockscreen.html">Lockscreen</a></li>
                <li><a href="404.html">Error 404</a></li>
            </ul>
        </li>
        <li class="sub-menu">
            <a href="" toggle-submenu><i class="zmdi zmdi-menu"></i> 3 Level Menu</a>

            <ul>
                <li><a href="">Level 2 link</a></li>
                <li><a href="">Another level 2 Link</a></li>
                <li class="sub-menu">
                    <a href="" toggle-submenu>I have children too</a>

                    <ul>
                        <li><a href="">Level 3 link</a></li>
                        <li><a href="">Another Level 3 link</a></li>
                        <li><a href="">Third one</a></li>
                    </ul>
                </li>
                <li><a href="">One more 2</a></li>
            </ul>
        </li>
        <li>
            <a href="https://wrapbootstrap.com/theme/material-admin-responsive-angularjs-WB011H985"><i class="zmdi zmdi-money"></i> Buy this template</a>
        </li>
    </ul>
</div>
  
</aside>
