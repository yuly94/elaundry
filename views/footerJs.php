<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

?>

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

        <!-- Autoload JS from vendor -->    
    <?php 
    if (isset($this->jsVendorsConstruct)) 
        {
            foreach ($this->jsVendorsConstruct as $js)
                {
                    echo '    <script type="text/javascript" src="'.URL.'public/vendors/'.$js.'"></script>'."\n    ";
                }
        }
    ?>
        
        <!-- Autoload JS from vendor -->    
    <?php 
    if (isset($this->jsVendors)) 
        {
            foreach ($this->jsVendors as $js)
                {
                    echo '    <script type="text/javascript" src="'.URL.'public/vendors/'.$js.'"></script>'."\n    ";
                }
        }
    ?>
                    

        
        <!-- Autoload JS from vendor -->    
    <?php 
    if (isset($this->jsCdn)) 
        {
            foreach ($this->jsCdn as $js)
                {
                    echo '    <script type="text/javascript" src="'.$js.'"></script>'."\n    ";
                }
        }
    ?>    

        
        <!-- Autoload JS from view folder-->
    <?php 
    if (isset($this->js)) 
        {
            foreach ($this->js as $js)
                {
                    echo '    <script type="text/javascript" src="'.URL.'views/'.$js.'"></script>'."\n    ";
                }
        }
    ?>

        
