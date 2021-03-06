<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class KirimEmailModel{
    
    public function lupaPassword($email,  $nama, $token) {
                $to = $email;
                $subject = "Password Reset Nofification";
                $body = 'Hai '.$nama.',<br><br> Kode untuk mereset password kamu adalah : <b>'.$token.'
                </b> Kode ini akan kadaluarsa setelah 180 detik. 
                Masukkan kode ini sebelum 180 detik untuk mereset password anda.<br><br>Thanks';

                $kirim_email = new EmailModel();
                $terkirim = $kirim_email->sentEmail($to,$subject,$body);

                    if($terkirim) { //4
                    $response["error"] = "false";
                    $response["message"] = "Email Reset Password Success";
                    BantuanModel::echoRespnse(200, $response);
                            } //4

                        else {

                            $response["message"] = "Email Reset Password Failure sent";
                            $response["error"] = "true";
                            BantuanModel::echoRespnse(200, $response);
                        }
                                
}

 public function gantiPassword($ganti_password, $nama) {
            $to = $ganti_password;
            $subject = "Pemberitahuan pembaruan password";
            $body = "Hai $nama ,<br><br> <p>Password account anda berhasil diperbarui</p>
            <p> Jika anda tidak merasa memperbarui password anda atau memiliki account, maka abaikan saja email ini</p>";

            $kirim_email = new EmailModel();
            $kirim_email->sentEmail($to,$subject,$body);
 
 }
 
 
  public function updateProfile($konsumen_password, $konsumen_nama) {
            $to = $konsumen_password;
            $subject = "Pemberitahuan pembaruan password";
            $body = "Hai $konsumen_nama ,<br><br>
            <p>Profile anda berhasil diperbarui</p>
            <p> Jika anda tidak merasa memperbarui profile anda atau memiliki account, maka abaikan saja email ini</p>";

            $kirim_email = new EmailModel();
            $kirim_email->sentEmail($to,$subject,$body);
 
 }

 
  public function  resetPassword( $konsumen_email, $konsumen_nama) {
      
            $to = $konsumen_email;
            $subject = "Pemberitahuan pembaruan password";
            $body = "'Hai $konsumen_nama',<br><br> 
            <p>Password account anda berhasil diperbarui</p>
            <p> Jika anda tidak merasa memperbarui password anda atau memiliki account, maka abaikan saja email ini</p>";

            $kirim_email = new EmailModel();   
            $kirim_email->sentEmail($to,$subject,$body);
 }
 
 
  public  function emailAktifasi($konsumen_email, $token_aktifasi, $konsumen_nama) {
             //send email
            $to = $konsumen_email;
            $subject = "Pendaftaran berhasil";
            $body = 'Selamat '.$konsumen_nama.' !              
            <p>Pendaftaran anda di elaundry telah berhasil dilakukan</p>
            <p>Silahkan melakukan aktifasi dengan mengklik link berikut ini :
            <a href="'.BASE_URL.'/konsumen/registrasi/aktifasi/'.$konsumen_nama.'/'.$token_aktifasi.'">'.DIR.'/konsumen/registrasi/aktifasi/'.$konsumen_nama.'/'.$token_aktifasi.'</a></p>' ;

            $kirim_email = new EmailModel();
            $kirim_email->sentEmail($to,$subject,$body);
                
  }
    
    public function emailAktifasiSukses($konsumen_email, $konsumen_nama) {
            //send email
            $to = $konsumen_email; 
            $subject = "aktifasi berhasil";
            $body = 'Selamat '.$konsumen_nama.' !              
            <p>Account anda telah aktif</p>';
            
            $kirim_email = new EmailModel();           
            $kirim_email->sentEmail($to,$subject,$body);
            
    }
    
  
}
