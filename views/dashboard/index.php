

<header id="header" data-current-skin={{mactrl.currentSkin}} 
            data-ng-app=""data-ng-controller="headerCtrl as hctrl">
        
        <ul class="header-inner clearfix">
                    <li id="menu-trigger" data-target="mainmenu" data-toggle-sidebar data-model-left="mactrl.sidebarToggle.left" data-ng-class="{ 'open': mactrl.sidebarToggle.left === true }">
                        <div class="line-wrap">
                            <div class="line top">            
                            </div>
                                <div class="line center"></div>
                                <div class="line bottom"></div>
                        </div>
                    </li>
                    <li class="hidden-xs">
                        <a href="<?php echo URL; ?>/index.php" class="m-l-10" data-ng-click="mactrl.sidebarStat($event)">
                            <img src="public/img/test/logo.png" alt="">
                        </a>
                    </li>
                    <li class="pull-right">
                    <ul class="top-menu">
                                <li id="toggle-width">
                                    <div class="toggle-switch">
                                        <input id="tw-switch" type="checkbox" hidden data-change-layout="mactrl.layoutType">
                                        <label for="tw-switch" class="ts-helper"></label>
                                    </div>
                                </li>
                                <li id="top-search">
                                    <a href="" data-ng-click="hctrl.openSearch()">
                                        <i class="tm-icon zmdi zmdi-search"></i>
                                    </a>
                                </li>
                                <li class="dropdown" uib-dropdown>
                                    <a uib-dropdown-toggle href="">
                                        <i class="tm-icon zmdi zmdi-email"></i> 
                                        <i class="tmn-counts">6</i>
                                    </a>
                                        <div class="dropdown-menu dropdown-menu-lg stop-propagate pull-right">
                                            <div class="listview">
                                                <div class="lv-header">Messages</div>
                                                <div class="lv-body">
                                                    <a class="lv-item" ng-href="" ng-repeat="w in hctrl.messageResult.list">
                                                        <div class="media">
                                                            <div class="pull-left">
                                                                <img class="lv-img-sm" ng-src="public/img/profile-pics/{{ w.img }}" alt="">
                                                            </div>
                                                                <div class="media-body">
                                                                    <div class="lv-title">{{ w.user }}
                                                                    </div>
                                                                    <small class="lv-small">{{ w.text }}
                                                                    </small>
                                                                </div>
                                                        </div>
                                                    </a>
                                                </div>
                                                <div class="clearfix">
                                                </div>
                                                <a class="lv-footer" href="">View All</a>
                                            </div>
                                        </div>                           
                                </li>
                                <li class="dropdown" uib-dropdown>
                                    <a uib-dropdown-toggle href="">
                                        <i class="tm-icon zmdi zmdi-notifications">\ 
                                        </i>
                                        <i class="tmn-counts">9</i>
                                    </a><div class="dropdown-menu dropdown-menu-lg stop-propagate pull-right">
                                        <div class="listview" id="notifications">
                                            <div class="lv-header">Notification
                                                <ul class="actions">
                                                    <li>
                                                        <a href="" data-ng-click="hctrl.clearNotification($event)">
                                                            <i class="zmdi zmdi-check-all"></i></a>
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="lv-body">
                                                        <a class="lv-item" ng-href="" ng-repeat="w in hctrl.messageResult.list">
                                                            <div class="media">
                                                                <div class="pull-left">
                                                                    <img class="lv-img-sm" ng-src="public/img/profile-pics/{{ w.img }}" alt="">
                                                                </div>
                                                                <div class="media-body">
                                                                    <div class="lv-title">{{ w.user }}</div>
                                                                    <small class="lv-small">{{ w.text }}</small>
                                                                </div>
                                                            </div> 
                                                        </a>
                                            </div>
                                            <div class="clearfix">
                                            </div>
                                                        <a class="lv-footer" href="">View Previous</a>
                                        </div>
                                    </div></li>
                                    <li class="dropdown hidden-xs" uib-dropdown>
                                        <a uib-dropdown-toggle href="">
                                            <i class="tm-icon zmdi zmdi-view-list-alt"></i>
                                            <i class="tmn-counts">2</i>
                                        </a><div class="dropdown-menu pull-right dropdown-menu-lg">
                                            <div class="listview"><div class="lv-header">Tasks</div>
                                                <div class="lv-body"><div class="lv-item">
                                                        <div class="lv-title m-b-5">HTML5 Validation Report</div>
                                                        <div class="progress">
                                                            <div class="progress-bar" role="progressbar" aria-valuenow="95" aria-valuemin="0" aria-valuemax="100" style="width: 95%">
                                                                <span class="sr-only">95% Complete (success)</span>
                                                            </div>
                                                        </div>
                                                    </div><div class="lv-item">
                                                        <div class="lv-title m-b-5">Google Chrome Extension</div>
                                                                <div class="progress">
                                                                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                                                        <span class="sr-only">80% Complete (success)</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                    <div class="lv-item">
                                                                        <div class="lv-title m-b-5">Social Intranet Projects</div>
                                                                            <div class="progress">
                                                                                <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%"><span class="sr-only">20% Complete</span></div></div></div><div class="lv-item"><div class="lv-title m-b-5">Bootstrap Admin Template</div>
                                                                             <div class="progress">
                                                                        <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%"><span class="sr-only">60% Complete (warning)</span></div></div></div><div class="lv-item"><div class="lv-title m-b-5">Youtube Client App</div><div class="progress"><div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%"><span class="sr-only">80% Complete (danger)</span></div></div></div></div><div class="clearfix"></div><a class="lv-footer" href="">View All</a></div></div></li><li class="dropdown" uib-dropdown><a uib-dropdown-toggle href=""><i class="tm-icon zmdi zmdi-more-vert"></i>
                                                    </a>
                                                <ul class="dropdown-menu dm-icon pull-right">
                                                        <li class="skin-switch hidden-xs">
                                                            <span ng-repeat="w in mactrl.skinList | limitTo : 6" class="ss-skin bgm-{{ w }}" data-ng-click="mactrl.skinSwitch(w)"></span>
                                                        </li>
                                                        
                                                            <li class="divider hidden-xs">
                                                            </li>
                                                        
                                                                    <li class="hidden-xs">
                                                                        <a data-ng-click="hctrl.fullScreen()" href="">
                                                                            <i class="zmdi zmdi-fullscreen">  
                                                                            </i> Toggle Fullscreen</a>
                                                                    </li>
                                                                <li>
                                                                    <a data-ng-click="hctrl.clearLocalStorage()" href="">
                                                                    <i class="zmdi zmdi-delete"></i> Clear Local Storage</a>
                                                                </li>
                                                            <li>
                                                                <a href=""><i class="zmdi zmdi-face"></i> Privacy Settings</a>
                                                            </li>
                                                        <li>
                                                            <a href=""><i class="zmdi zmdi-settings"></i> Other Settings</a>
                                                        </li>
                                                </ul>
                                    <li class="hidden-xs" data-target="chat" data-toggle-sidebar data-model-right="mactrl.sidebarToggle.right" data-ng-class="{ 'open': mactrl.sidebarToggle.right === true }">
                                            <a href=""><i class="tm-icon zmdi zmdi-comment-alt-text"></i></a>
                                    </li>
                        </ul>
                    </li>
        </ul>
    
    <!-- Top Search Content -->
    <div id="top-search-wrap">
                <div class="tsw-inner">
                    <i id="top-search-close" class="zmdi zmdi-arrow-left" data-ng-click="hctrl.closeSearch()"></i> 
                    <input type="text">
                </div>
    </div>
</header>
 
    <body data-ng-class="{ 'sw-toggled': mactrl.layoutType === '1'}" class="sw-toggled" style="">

        <!-- uiView:  -->
     <data ui-view="" class="ng-scope">

     </data>

        
        <?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

?>

<section id="main">
    

    <aside id="chat" data-ng-include="'public/template/chat.html'" 
           data-ng-class="{ 'toggled': mactrl.sidebarToggle.right === true }"></aside>

    <section id="content">

        <div class="container">
            <div class="block-header">
                <h2>Dashboard</h2>

                <ul class="actions">
                    <li>
                        <a href="">
                            <i class="zmdi zmdi-trending-up"></i>
                        </a>
                    </li>
                    <li>
                        <a href="">
                            <i class="zmdi zmdi-check-all"></i>
                        </a>
                    </li>
                    <li class="dropdown" uib-dropdown>
                        <a href="" uib-dropdown-toggle>
                            <i class="zmdi zmdi-more-vert"></i>
                        </a>

                        <ul class="dropdown-menu dropdown-menu-right">
                            <li>
                                <a href="">Refresh</a>
                            </li>
                            <li>
                                <a href="">Manage Widgets</a>
                            </li>
                            <li>
                                <a href="">Widgets Settings</a>
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
                            <a href="">
                                <i class="zmdi zmdi-refresh-alt"></i>
                            </a>
                        </li>
                        <li>
                            <a href="">
                                <i class="zmdi zmdi-download"></i>
                            </a>
                        </li>
                        <li class="dropdown" uib-dropdown>
                            <a href="" uib-dropdown-toggle>
                                <i class="zmdi zmdi-more-vert"></i>
                            </a>

                            <ul class="dropdown-menu dropdown-menu-right">
                                <li>
                                    <a href="">Change Date Range</a>
                                </li>
                                <li>
                                    <a href="">Change Graph Type</a>
                                </li>
                                <li>
                                    <a href="">Other Settings</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>

                <div class="card-body">
                    <div class="chart-edge">
                        <div class="flot-chart" data-curvedline-chart></div>
                    </div>
                </div>
            </div>

            <div class="mini-charts">
                <div class="row">
                    <div class="col-sm-6 col-md-3">
                        <div class="mini-charts-item bgm-cyan">
                            <div class="clearfix">
                                <div class="chart stats-bar" data-sparkline-bar></div>
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
                                <div class="chart stats-bar-2" data-sparkline-bar></div>
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
                                <div class="chart stats-line" data-sparkline-line></div>
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
                                <div class="chart stats-line-2" data-sparkline-line></div>
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
                                    <div class="dash-widget-visits" data-sparkline-line></div>
                                </div>

                                <div class="dash-widget-title">For the past 30 days</div>

                                <ul class="actions actions-alt">
                                    <li class="dropdown" uib-dropdown>
                                        <a href="" uib-dropdown-toggle>
                                            <i class="zmdi zmdi-more-vert"></i>
                                        </a>

                                        <ul class="dropdown-menu dropdown-menu-right">
                                            <li>
                                                <a href="">Refresh</a>
                                            </li>
                                            <li>
                                                <a href="">Manage Widgets</a>
                                            </li>
                                            <li>
                                                <a href="">Widgets Settings</a>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>

                            <div class="p-20">

                                <small>Page Views</small>
                                <h3 class="m-0 f-400">47,896,536</h3>

                                <br/>

                                <small>Site Visitors</small>
                                <h3 class="m-0 f-400">24,456,799</h3>

                                <br/>

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
                                    <div class="easy-pie main-pie" data-percent="75" data-easypie-chart>
                                        <div class="percent">45</div>
                                        <div class="pie-title">Total Emails Sent</div>
                                    </div>
                                </div>
                            </div>

                            <div class="p-t-20 p-b-20 text-center">
                                <div class="easy-pie sub-pie-1" data-percent="56" data-easypie-chart>
                                    <div class="percent">56</div>
                                    <div class="pie-title">Bounce Rate</div>
                                </div>
                                <div class="easy-pie sub-pie-2" data-percent="84" data-easypie-chart>
                                    <div class="percent">84</div>
                                    <div class="pie-title">Total Opened</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3 col-sm-6">
                        <div class="dash-widget-item bgm-lime">
                            <div id="weather-widget" data-weather-widget></div>
                        </div>
                    </div>

                    <div class="col-md-3 col-sm-6">
                        <div id="best-selling" class="dash-widget-item" data-ng-controller="bestsellingCtrl as wctrl">
                            <div class="dash-widget-header">
                                <div class="dash-widget-title">Best Sellings</div>
                                <img src="public/img/widgets/alpha.jpg" alt="">
                                <div class="main-item">
                                    <small>Samsung Galaxy Alpha</small>
                                    <h2>$799.99</h2>
                                </div>
                            </div>

                            <div class="listview p-t-5">
                                <a class="lv-item" href="" data-ng-repeat="w in wctrl.bsResult.list">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="public/img/widgets/{{ w.img }}" alt="">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title">{{ w.name }}</div>
                                            <small class="lv-small">{{ w.range }}</small>
                                        </div>
                                    </div>
                                </a>

                                <div class="clearfix"></div>

                                <a class="lv-footer" href="">
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
                    <div class="card" data-ng-controller="recentitemCtrl as rictrl">
                        <div class="card-header">
                            <h2>Recent Items <small>Phasellus condimentum ipsum id auctor imperdie</small></h2>
                            <ul class="actions">
                                <li class="dropdown" uib-dropdown>
                                    <a href="" uib-dropdown-toggle>
                                        <i class="zmdi zmdi-more-vert"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li>
                                            <a href="">Refresh</a>
                                        </li>
                                        <li>
                                            <a href="">Settings</a>
                                        </li>
                                        <li>
                                            <a href="">Other Settings</a>
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
                                <tr data-ng-repeat="w in rictrl.riResult.list">
                                    <td class="f-500 c-cyan">{{ w.id }}</td>
                                    <td>{{ w.name }}</td>
                                    <td class="f-500 c-cyan">{{ w.price }}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="recent-items-chart" class="flot-chart" data-line-chart></div>
                    </div>

                    <!-- Todo Lists -->
                    <div id="todo-lists" data-ng-controller="todoCtrl as tctrl">
                        <div class="tl-header">
                            <h2>Todo Lists</h2>
                            <small>Add, edit and manage your Todo Lists</small>

                            <ul class="actions actions-alt">
                                <li class="dropdown" uib-dropdown>
                                    <a href="" uib-dropdown-toggle>
                                        <i class="zmdi zmdi-more-vert"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li>
                                            <a href="">Refresh</a>
                                        </li>
                                        <li>
                                            <a href="">Manage Widgets</a>
                                        </li>
                                        <li>
                                            <a href="">Widgets Settings</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                        <div class="clearfix"></div>

                        <div class="tl-body">
                            <div id="add-tl-item" data-ng-class="{ 'toggled': tctrl.addTodoStat }" data-ng-click="tctrl.addTodoStat = true">
                                <i class="add-new-item zmdi zmdi-plus" data-ng-click="tctrl.addTodo($event)"></i>

                                <div class="add-tl-body">
                                    <textarea placeholder="What you want to do..." data-ng-model="tctrl.todo"></textarea>

                                    <div class="add-tl-actions">
                                        <a class="zmdi zmdi-close" data-tl-action="dismiss" data-ng-click="tctrl.addTodoStat = false; $event.stopPropagation()"></a>
                                        <a class="zmdi zmdi-check" data-tl-action="save" data-ng-click="tctrl.addTodoStat = false; $event.stopPropagation()"></a>
                                    </div>
                                </div>
                            </div>


                            <div class="checkbox media" data-ng-repeat="w in tctrl.tdResult.list">
                                <div class="pull-right">
                                    <ul class="actions actions-alt">
                                        <li class="dropdown" uib-dropdown>
                                            <a href="" uib-dropdown-toggle>
                                                <i class="zmdi zmdi-more-vert"></i>
                                            </a>

                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="">Delete</a></li>
                                                <li><a href="">Archive</a></li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                                <div class="media-body">
                                    <label>
                                        <input type="checkbox">
                                        <i class="input-helper"></i>
                                        <span>{{ w.todo }}</span>
                                    </label>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="col-sm-6">
                    <!-- Calendar -->
                    <div id="calendar-widget" data-full-calendar></div>

                    <!-- Recent Posts -->
                    <div class="card" data-ng-controller="recentpostCtrl as rpctrl">
                        <div class="card-header ch-alt m-b-20">
                            <h2>Recent Posts <small>Phasellus condimentum ipsum id auctor imperdie</small></h2>
                            <ul class="actions">
                                <li>
                                    <a href="">
                                        <i class="zmdi zmdi-refresh-alt"></i>
                                    </a>
                                </li>
                                <li>
                                    <a href="">
                                        <i class="zmdi zmdi-download"></i>
                                    </a>
                                </li>
                                <li class="dropdown" uib-dropdown>
                                    <a href="" uib-dropdown-toggle>
                                        <i class="zmdi zmdi-more-vert"></i>
                                    </a>

                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li>
                                            <a href="">Change Date Range</a>
                                        </li>
                                        <li>
                                            <a href="">Change Graph Type</a>
                                        </li>
                                        <li>
                                            <a href="">Other Settings</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>

                            <button class="btn bgm-cyan btn-float"><i class="zmdi zmdi-plus"></i></button>
                        </div>

                        <div class="card-body">
                            <div class="listview">

                                <a class="lv-item" href="" data-ng-repeat="w in rpctrl.rpResult.list">
                                    <div class="media">
                                        <div class="pull-left">
                                            <img class="lv-img-sm" data-ng-src="public/img/profile-pics/{{ w.img }}" alt="">
                                        </div>
                                        <div class="media-body">
                                            <div class="lv-title">{{ w.user }}</div>
                                            <small class="lv-small">{{ w.text }}</small>
                                        </div>
                                    </div>
                                </a>

                                <div class="clearfix"></div>

                                <a class="lv-footer" href="">View All</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>

 