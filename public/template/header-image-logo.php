<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

?>

<ul class="header-inner clearfix">
    <li id="menu-trigger" data-target="mainmenu" data-toggle-sidebar data-model-left="mactrl.sidebarToggle.left" data-ng-class="{ 'open': mactrl.sidebarToggle.left === true }">
                <div class="line-wrap">
                         <div class="line top">
                    
                        </div>
                        <div class="line center">
            
                        </div>
                        <div class="line bottom"></div>
                
                </div></li>
                <li class="hidden-xs">
                <a href="<?php echo URL; ?>/index.php" class="m-l-10" data-ng-click="mactrl.sidebarStat($event)">
                    <img src="public/img/demo/logo.png" alt="">
                </a></li>
                <li class="pull-right">
                    <ul class="top-menu"><li id="top-search" data-ng-click="hctrl.openSearch()">
                            <a href="">
                                <span class="tm-label">Search</span></a>
                        </li>
                        <li class="dropdown" uib-dropdown>
                            <a uib-dropdown-toggle href="">
                                <span class="tm-label">Messages</span></a>
                                <div class="dropdown-menu dropdown-menu-lg pull-right">
                                    <div class="listview">
                                        <div class="lv-header">Messages</div>
                                        <div class="lv-body"><a class="lv-item" href="">
                                                <div class="media">
                                                    <div class="pull-left">
                                                        <img class="lv-img-sm" src="public/img/profile-pics/1.jpg" alt=""></div>
                                                        <div class="media-body">
                                                            <div class="lv-title">David Belle</div>
                                                            <small class="lv-small">Cum sociis natoque penatibus et magnis dis parturient montes</small>
                                                        </div></div></a> <a class="lv-item" href="">
                                                            <div class="media">
                                                                <div class="pull-left">
                                                                    <img class="lv-img-sm" src="public/img/profile-pics/2.jpg" alt="">
                                                                </div><div class="media-body"><div class="lv-title">Jonathan Morris</div>
                                                                    <small class="lv-small">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small>
                                                                </div></div></a> <a class="lv-item" href=""><div class="media">
                                                                            <div class="pull-left">
                                                                                <img class="lv-img-sm" src="public/img/profile-pics/3.jpg" alt=""></div>
                                                                            <div class="media-body">
                                                                                <div class="lv-title">Fredric Mitchell Jr.</div>
                                                                                <small class="lv-small">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small></div>
                                                                    </div>
                                                                </a> <a class="lv-item" href="">
                                                                                    <div class="media"><div class="pull-left"><img class="lv-img-sm" src="public/img/profile-pics/4.jpg" alt="">
                                                                                        </div>
                                                                                        <div class="media-body"><div class="lv-title">Glenn Jecobs</div>
                                                                                            <small class="lv-small">Ut vitae lacus sem ellentesque maximus, nunc sit amet varius dignissim, dui est consectetur neque</small>
                                                                                        </div></div></a> <a class="lv-item" href="">
                                                                                                <div class="media"><div class="pull-left">
                                                                                                        <img class="lv-img-sm" src="public/img/profile-pics/4.jpg" alt=""></div>
                                                                                                        <div class="media-body"><div class="lv-title">Bill Phillips</div>
                                                                                                            <small class="lv-small">Proin laoreet commodo eros id faucibus. Donec ligula quam, imperdiet vel ante placerat</small>
                                                                                                        </div></div>
                                                                                            </a></div><a class="lv-footer" href="">View All</a>
                                    </div>
                                </div></li><li class="dropdown hidden-xs" uib-dropdown><a uib-dropdown-toggle href="">
                                        <span class="tm-label">Notification</span></a><div class="dropdown-menu dropdown-menu-lg pull-right">
                                        <div class="listview" id="notifications">
                                            <div class="lv-header">Notification<ul class="actions"><li class="dropdown"><a href="" data-clear="notification">
                                                            <i class="zmdi zmdi-check-all"></i></a></li>
                                                </ul></div><div class="lv-body"><a class="lv-item" href="">
                                                    <div class="media"><div class="pull-left">
                                                            <img class="lv-img-sm" src="public/img/profile-pics/1.jpg" alt=""></div>
                                                            <div class="media-body"><div class="lv-title">David Belle</div>
                                                                <small class="lv-small">Cum sociis natoque penatibus et magnis dis parturient montes</small>
                                                            </div></div></a> <a class="lv-item" href="">
                                                                <div class="media"><div class="pull-left">
                                                                            <img class="lv-img-sm" src="public/img/profile-pics/2.jpg" alt="">
                                                                        </div><div class="media-body"><div class="lv-title">Jonathan Morris</div>
                                                                            <small class="lv-small">Nunc quis diam diamurabitur at dolor elementum, dictum turpis vel</small>
                                                                        </div></div></a> <a class="lv-item" href=""><div class="media">
                                                                                    <div class="pull-left"><img class="lv-img-sm" src="public/img/profile-pics/3.jpg" alt=""></div>
                                                                                    <div class="media-body"><div class="lv-title">Fredric Mitchell Jr.</div>
                                                                                        <small class="lv-small">Phasellus a ante et est ornare accumsan at vel magnauis blandit turpis at augue ultricies</small></div></div></a> 
                                                                                        <a class="lv-item" href=""><div class="media"><div class="pull-left">
                                                                                                    <img class="lv-img-sm" src="public/img/profile-pics/4.jpg" alt=""></div>
                                                                                                    <div class="media-body"><div class="lv-title">Glenn Jecobs</div>
                                                                                                        <small class="lv-small">Ut vitae lacus sem ellentesque maximus, nunc sit amet varius dignissim, dui est consectetur neque</small></div></div></a> <a class="lv-item" href="">
                                                                                                            <div class="media"><div class="pull-left">
                                                                                                                    <img class="lv-img-sm" src="public/img/profile-pics/4.jpg" alt=""></div>
                                                                                                                    <div class="media-body"><div class="lv-title">Bill Phillips</div>
                                                                                                                        <small class="lv-small">Proin laoreet commodo eros id faucibus. Donec ligula quam, imperdiet vel ante placerat</small>
                                                                                                                    </div></div></a></div><a class="lv-footer" href="">View Previous</a></div></div>
                                </li><li class="hidden-xs"><a target="_blank" href="https://wrapbootstrap.com/theme/superflat-simple-responsive-admin-theme-WB082P91H"><span class="tm-label">Link</span></a></li>
                    </ul></li></ul><!-- Top Search Content --><div id="top-search-wrap"><div class="tsw-inner"><i id="top-search-close" data-ng-click="hctrl.closeSearch()" class="zmdi zmdi-arrow-left"></i>
        <input type="text"></div></div>