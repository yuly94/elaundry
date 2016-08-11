<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
?>     
    
  </head>

    <body>
    <div ng-controller="DemoCtrl" layout="column" layout-padding="" ng-cloak="" class="inputdemoIcons" ng-app="MyApp">

  <br>
  <md-content class="md-no-momentum">
    <md-input-container class="md-icon-float md-block">
      <!-- Use floating label instead of placeholder -->
      <label>Name</label>
      <md-icon md-svg-src="img/icons/ic_person_24px.svg" class="name"></md-icon>
      <input ng-model="user.name" type="text">
    </md-input-container>

    <md-input-container md-no-float="" class="md-block">
      <md-icon md-svg-src="img/icons/ic_phone_24px.svg"></md-icon>
      <input ng-model="user.phone" type="text" placeholder="Phone Number">
    </md-input-container>

    <md-input-container class="md-block">
      <!-- Use floating placeholder instead of label -->
      <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
      <input ng-model="user.email" type="email" placeholder="Email (required)" ng-required="true">
    </md-input-container>
      
    <md-input-container md-no-float="" class="md-block">
      <input ng-model="user.password" type="text" placeholder="Address">
      <md-icon md-svg-src="img/icons/ic_lock_outline_24px.svg" style="display:inline-block;"></md-icon>
    </md-input-container>  

    <md-input-container md-no-float="" class="md-block">
      <input ng-model="user.address" type="text" placeholder="Address">
      <md-icon md-svg-src="img/icons/ic_place_24px.svg" style="display:inline-block;"></md-icon>
    </md-input-container>

    <md-input-container class="md-icon-float md-icon-right md-block">
      <label>Donation Amount</label>
      <md-icon md-svg-src="img/icons/ic_card_giftcard_24px.svg"></md-icon>
      <input ng-model="user.donation" type="number" step="0.01">
      <md-icon md-svg-src="img/icons/ic_euro_24px.svg"></md-icon>
    </md-input-container>

  </md-content>

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
  
