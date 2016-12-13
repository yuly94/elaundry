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


$app->post('/kurir/profile/update/','authKurir', function() use ($app) {

            global $api_kurir_id;
            // check for required params
            BantuanModel::verifyRequiredParams(array('kurir_nama', 'kurir_alamat', 'kurir_email', 'kurir_nohp'));

            // reading post params
            $kurir_id = $api_kurir_id;
            $kurir_nama = $app->request->post('kurir_nama');
            $kurir_alamat = $app->request->post('kurir_alamat');
            $kurir_email = $app->request->post('kurir_email');
            $kurir_nohp = $app->request->post('kurir_nohp');
                 
            if (ProfileModel::updateProfile($kurir_nama,  $kurir_alamat, $kurir_email, $kurir_nohp, $kurir_id)) {

             // get the user by email
            $kurir = KurirModel::kurirById($api_kurir_id);

            if ($kurir) {
    
                // user is found

                $response["error"] = FALSE;
                $response["message"] = "Successfully update you";
                $response["kurir"] = $kurir;
                
                //send email
                
                $kirim_email = new KirimEmailModel();
                $kirim_email->updateProfile($kurir_email,$kurir_nama);
                

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
  	