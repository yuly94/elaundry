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


$app->post('/konsumen/profile/update/','authKonsumen', function() use ($app) {

            global $api_konsumen_id;
            // check for required params
            BantuanModel::verifyRequiredParams(array('konsumen_nama', 'konsumen_alamat', 'konsumen_email', 'konsumen_nohp'));

            // reading post params
            $konsumen_id = $api_konsumen_id;
            $konsumen_nama = $app->request->post('konsumen_nama');
            $konsumen_alamat = $app->request->post('konsumen_alamat');
            $konsumen_email = $app->request->post('konsumen_email');
            $konsumen_nohp = $app->request->post('konsumen_nohp');
                 
            if (ProfileModel::updateProfile($konsumen_nama,  $konsumen_alamat, $konsumen_email, $konsumen_nohp, $konsumen_id)) {

             // get the user by email
            $konsumen = KonsumenModel::konsumenById($api_konsumen_id);

            if ($konsumen) {
    
                // user is found

                $response["error"] = FALSE;
                $response["message"] = "Successfully update you";
                $response["konsumen"] = $konsumen;
                
                //send email
                
                $kirim_email = new KirimEmailModel();
                $kirim_email->updateProfile($konsumen_email,$konsumen_nama);
                

                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "An error occurred. Please try again";
                }
          
      
                }else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "Gagal memperbarui profile, silahkan coba kembali";
                }
  
        BantuanModel::echoRespnse(200, $response);
    });
  	