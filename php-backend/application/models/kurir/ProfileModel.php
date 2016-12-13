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
	
public static function updateProfile($kurir_nama, $kurir_alamat, $kurir_email, $kurir_nohp, $kurir_id) {
    
        $app = \Slim\Slim::getInstance();
        // fetching user by email

	//$update = time();
	$sql = "UPDATE kurir SET kurir_nama =:kurir_nama, "
                . "kurir_alamat =:kurir_alamat, kurir_email =:kurir_email,"
                . " kurir_nohp =:kurir_nohp, kurir_update_pada = NOW() WHERE kurir_id = :kurir_id";
        
        $stmt = $app->db->prepare($sql);
        $mengupdate=$stmt->execute(array(
            'kurir_nama'=>$kurir_nama,
            'kurir_alamat'=>$kurir_alamat,
            'kurir_email'=>$kurir_email,
            'kurir_nohp'=>$kurir_nohp,
            'kurir_id'=>$kurir_id
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