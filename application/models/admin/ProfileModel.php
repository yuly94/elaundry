<?php

/* 
 * Copyright (C) 2016 Yuly Nurhidayati <elaundry.pe.hu>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

class ProfileModel {
	
public static function updateProfile($konsumen_nama, $konsumen_alamat, $konsumen_email, $konsumen_nohp, $konsumen_id) {
    
        $app = \Slim\Slim::getInstance();
        // fetching user by email

	//$update = time();
	$sql = "UPDATE konsumen SET konsumen_nama =:konsumen_nama, "
                . "konsumen_alamat =:konsumen_alamat, konsumen_email =:konsumen_email,"
                . " konsumen_nohp =:konsumen_nohp, konsumen_update_pada = NOW() WHERE konsumen_id = :konsumen_id";
        
        $stmt = $app->db->prepare($sql);
        $mengupdate=$stmt->execute(array(
            'konsumen_nama'=>$konsumen_nama,
            'konsumen_alamat'=>$konsumen_alamat,
            'konsumen_email'=>$konsumen_email,
            'konsumen_nohp'=>$konsumen_nohp,
            'konsumen_id'=>$konsumen_id
        ));
		
      //  $stmt->fetch(PDO::FETCH_ASSOC);
        
        if($mengupdate)
            {
                //Update user password success
                return TRUE;
            } else {
               //Update user password failed
				
            $stmt->close();
            return FALSE;
        }
    }
	
    
}   