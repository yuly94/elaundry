<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
?>     


    
    </head>

  <body>

    <div ng-controller="AppCtrl as postCtrl" ng-cloak="" class="inputdemoErrorsAdvanced" ng-app="MyApp"
         layout="column" layout-align="center center" layout-fill="layout-fill">
   <div layout="column" class="loginBox md-whiteframe-z1">
    <md-toolbar>
      <h2 class="md-toolbar-tools"><span>Login</span></h2>
    </md-toolbar>
  <md-content layout="column" class="md-padding">
    <form name="userForm" ng-submit="postCtrl.postForm()">
<!--      <div layout="row" layout-xs="column" layout-sm="column" layout-align="space-between center">
        <md-input-container>
          <md-switch ng-model="showHints">Showing {{showHints ? "Hints" : "Errors"}}</md-switch>
        </md-input-container>
      </div>-->

      <div layout-gt-sm="row">
         <md-input-container class="md-block" flex-gt-sm="">
          <label>Email</label>
           <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
          <input name="email" ng-model="postCtrl.inputData.username" required="" minlength="10" 
                 maxlength="100" ng-pattern="/^.+@.+\..+$/">
          <div class="hint" ng-if="!userForm.email.$touched">Please enter your email here</div>
          <div ng-messages="userForm.email.$error" ng-hide="Errors" ng-if="userForm.email.$touched">
            <div ng-message-exp="['required', 'minlength', 'maxlength', 'pattern']">
              Your email must be between 10 and 100 characters long and look like an e-mail address.
            </div>
          </div>
          <div class="errorhint" ng-messages="userForm.email.$error" ng-show="errorMsg" ng-if="!userForm.email.$touched">
              Login Failed, Please check your username or password
          </div>
        </md-input-container>

        <div flex="5" hide-xs="" hide-sm="">
          <!-- Spacer //-->
        </div>

      </div>

      <div layout-gt-sm="row">

        <md-input-container class="md-block" flex-gt-sm="">
         <label>Password</label>
           <md-icon md-svg-src="img/icons/ic_lock_open_outline_24px.svg" class="email"></md-icon>
          <input name="password" ng-model="postCtrl.inputData.password" required="" minlength="2" maxlength="100">
          <div class="hint" ng-if="!userForm.password.$touched" >Please enter your credential here</div>
          <div ng-messages="userForm.password.$error">
            <div ng-message-exp="['required', 'minlength', 'maxlength', 'pattern']">
              Your email must be between 2 and 100 characters long and look like an e-mail address.
            </div>
          </div>
          
           <div class="errorhint" ng-messages="userForm.password.$error" ng-show="errorMsg">
              Login Failed, Please check your username or password
          </div>

        </md-input-container>
      <div flex="5" hide-xs="" hide-sm="">
          <!-- Spacer //-->
        </div>
        <style>
          /*
           * The Material demos system does not currently allow targeting the body element, so this
           * must go here in the HTML.
           */
          body[dir=rtl] .hint {
            right: 2px;
            left: auto;
          }
        </style>
      </div>

           
            <div layout="row" layout-align="center center" style="padding-top:20px;">
                <md-button class="md-raised md-primary" type="submit">Login</md-button> 
                    <div flex="flex"></div>
                <md-button href="" md-no-ink="md-no-ink"ng-click="openToast()">Forgot Password</md-button>
            </div> 
      </div>
    </form>
  </md-content>
</div>
   </div>       
 

<!--
Copyright 2016 Google Inc. All Rights Reserved. 
Use of this source code is governed by an MIT-style license that can be in foundin the LICENSE file at http://material.angularjs.org/license.
-->



 
      <!-- Your application bootstrap  -->
    <script type="text/javascript">    
    /**
     * You must include the dependency on 'ngMaterial' 
     */
    </script> 
  
