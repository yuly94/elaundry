<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

?>


<div class="container">

    <div class="block-header">
        <h2>Malinda Hollaway <small>Web/UI Developer, Dubai, United Arab Emirates</small></h2>

        <ul class="actions m-t-20 hidden-xs">
            <li class="dropdown" uib-dropdown>
                <a href="" uib-dropdown-toggle>
                    <i class="zmdi zmdi-more-vert"></i>
                </a>

                <ul class="dropdown-menu dropdown-menu-right">
                    <li>
                        <a href="">Privacy Settings</a>
                    </li>
                    <li>
                        <a href="">Account Settings</a>
                    </li>
                    <li>
                        <a href="">Other Settings</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>

    <div class="card" id="profile-main" data-ng-controller="profileCtrl as pctrl">
        <div class="pm-overview c-overflow">
            <div class="pmo-pic">
                <div class="p-relative">
                    <a href="">
                        <img class="img-responsive" src="<?php echo URL; ?>public/img/profile-pics/profile-pic-2.jpg" alt="">
                    </a>

                    <div class="dropdown pmop-message" uib-dropdown>
                        <button uib-dropdown-toggle class="btn bgm-white btn-float">
                            <i class="zmdi zmdi-comment-text-alt"></i>
                        </button>

                        <div class="dropdown-menu stop-propagate">
                            <textarea placeholder="Write something..."></textarea>

                            <button class="btn bgm-green btn-float"><i class="zmdi zmdi-mail-send"></i></button>
                        </div>
                    </div>

                    <a href="" class="pmop-edit">
                        <i class="zmdi zmdi-camera"></i> <span class="hidden-xs">Update Profile Picture</span>
                    </a>
                </div>


                <div class="pmo-stat">
                    <h2 class="m-0 c-white">1562</h2>
                    Total Connections
                </div>
            </div>

            <div class="pmo-block pmo-contact hidden-xs">
                <h2>Contact</h2>

                <ul>
                    <li><i class="zmdi zmdi-phone"></i> {{ pctrl.mobileNumber }}</li>
                    <li><i class="zmdi zmdi-email"></i> {{ pctrl.emailAddress }}</li>
                    <li><i class="zmdi zmdi-facebook-box"></i> {{ pctrl.skype }}</li>
                    <li><i class="zmdi zmdi-twitter"></i> {{ pctrl.twitter }} (twitter.com/malinda)</li>
                    <li>
                        <i class="zmdi zmdi-pin"></i>
                        <address class="m-b-0">
                            {{ pctrl.addressSuite }}, <br/>
                            {{ pctrl.addressCity }}, <br/>
                            {{ pctrl.addressCountry }}
                        </address>
                    </li>
                </ul>
            </div>

            <div class="pmo-block pmo-items hidden-xs">
                <h2>Connections</h2>

                <div class="pmob-body">
                    <div class="row">
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/1.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/2.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/3.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/4.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/5.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/6.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/7.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/8.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/1.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/2.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/3.jpg" alt="">
                        </a>
                        <a href="" class="col-xs-2">
                            <img class="img-circle" src="<?php echo URL; ?>public/img/profile-pics/4.jpg" alt="">
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="pm-body clearfix">
            <ul class="tab-nav tn-justified" data-ng-include="'<?php echo URL; ?>public/template/profile-menu.html'"></ul>

            <data ui-view></data>
        </div>
    </div>
</div>
