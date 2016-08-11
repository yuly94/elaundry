<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
?>

    
        <!-- Autoload CSS from vendor -->    
    <?php 
    if (isset($this->cssVendors)) 
        {
            foreach ($this->cssVendors as $css)
                {
                    echo '    <link rel="stylesheet" href="'.URL.'public/vendors/'.$css.'" >'."\n    ";
                }
        }
    ?>   
        
        <!-- Autoload CSS from vendor prefetch -->    
    <?php 
    if (isset($this->cssVendorsPrefetch)) 
        {
            foreach ($this->cssVendorsPrefetch as $css)
                {
                    echo '    <link rel="stylesheet prefetch" href="'.URL.'public/vendors/'.$css.'" >'."\n    ";
                }
        }
    ?>       
        
        
        
        <!-- Autoload CSS -->
    <?php if (isset($this->css)) {
	foreach ($this->css as $css) {
		    echo '    <link href="'.URL.'views/'.$css.'" rel="stylesheet" type="text/css">'."\n    ";
	}
    } ?>    