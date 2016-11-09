<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



class KirimEmailModel{
    
    /* ------------- `login konsumen` ------------------ */
    
    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
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

				$response["error"] = "true";
      				$response["message"] = "Email Reset Password Failure sent";
      				BantuanModel::echoRespnse(200, $response);
				}
                                
}

 public function menggantiPassword($konsumen_id, $reset_password) {

if ( PasswordModel::menggantiPassword($konsumen_id, $reset_password)) {

			$to = $email;
			$subject = "Pemberitahuan pembaruan password";
			$body = "'Hai '.$nama.',<br><br> <p>Password account anda berhasil diperbarui</p>
			<p> Jika anda tidak merasa memperbarui password anda atau memiliki account, maka abaikan saja email ini</p>";

			EmailModel::sentEmail($to,$subject,$body);

			return 0;
		  
			} else {
			return 1;

		  	  }


 }
 

 
  public function  resetPassword( $konsumen_email) {
      
			$to = $konsumen_email;
			$subject = "Pemberitahuan pembaruan password";
			$body = "'Hai '.$nama.',<br><br> <p>Password account anda berhasil diperbarui</p>
			<p> Jika anda tidak merasa memperbarui password anda atau memiliki account, maka abaikan saja email ini</p>";
                     
                        $kirim_email = new EmailModel();   
                        $kirim_email->sentEmail($to,$subject,$body);
 }
 
 
  public  function emailAktifasi($konsumen_email, $token_aktifasi, $konsumen_nama) {
                 //send email
                $to = $konsumen_email;
		$subject = "Pendaftaran berhasil";
		$body = 
 
'                     
<html xmlns="http://www.w3.org/1999/xhtml"><head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Email Aktifasi</title>
  <!-- Designed by https://github.com/kaytcat -->
  <!-- Robot header image designed by Freepik.com -->

  <style type="text/css">
  @import url(http://fonts.googleapis.com/css?family=Droid+Sans);

  /* Take care of image borders and formatting */

  img {
    max-width: 600px;
    outline: none;
    text-decoration: none;
    -ms-interpolation-mode: bicubic;
  }

  a {
    text-decoration: none;
    border: 0;
    outline: none;
    color: #bbbbbb;
  }

  a img {
    border: none;
  }

  /* General styling */

  td, h1, h2, h3  {
    font-family: Helvetica, Arial, sans-serif;
    font-weight: 400;
  }

  td {
    text-align: center;
  }

  body {
    -webkit-font-smoothing:antialiased;
    -webkit-text-size-adjust:none;
    width: 100%;
    height: 100%;
    color: #37302d;
    background: #ffffff;
    font-size: 16px;
  }

   table {
    border-collapse: collapse !important;
  }

  .headline {
    color: #ffffff;
    font-size: 36px;
  }

 .force-full-width {
  width: 100% !important;
 }




  </style>

  <style type="text/css" media="screen">
      @media screen {
         /*Thanks Outlook 2013! http://goo.gl/XLxpyl*/
        td, h1, h2, h3 {
          font-family: "Droid Sans", "Helvetica Neue", "Arial", "sans-serif" !important;
        }
      }
  </style>

  <style type="text/css" media="only screen and (max-width: 480px)">
    /* Mobile styles */
    @media only screen and (max-width: 480px) {

      table[class="w320"] {
        width: 320px !important;
      }


    }
  </style>
</head>
<body class="body" style="padding:0; margin:0; display:block; background:#ffffff; -webkit-text-size-adjust:none" bgcolor="#ffffff">
<table align="center" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tbody><tr>
    <td align="center" valign="top" bgcolor="#ffffff" width="100%">
      <center>
        <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="600" class="w320">
          <tbody><tr>
            <td align="center" valign="top">

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="100%">
                  <tbody><tr>
                    <td style="font-size: 30px; text-align:center;">
                      <br>
                      
                    </td>
                  </tr>
                </tbody></table>

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="100%" bgcolor="#4dbfbf">
                  <tbody><tr>
                    <td>
                    <br>
                      <img src="https://www.filepicker.io/api/file/Pv8CShvQHeBXdhYu9aQE" width="216" height="189" alt="robot picture">
                    </td>
                  </tr>
                  <tr>
                    <td class="headline">
                      Welcome '.$konsumen_nama.' !
                    </td>
                  </tr>
                  <tr>
                    <td>

                      <center>
                        <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="60%">
                          <tbody><tr>
                            <td style="color:#187272;">
                            <br>
                            
		<p>Pendaftaran anda di elaundry telah berhasil dilakukan</p>
		<p>Silahkan melakukan aktifasi dengan mengklik link berikut ini :
                <a href="'.DIR.'konsumen/registrasi/aktifasi/'.$konsumen_nama.'/'.$token_aktifasi.'">'.DIR.'konsumen/registrasi/aktifasi/'.$konsumen_nama.'/'.$token_aktifasi.'</a></p>
       
<br> 
                            <br>
                            </td>
                          </tr>
                        </tbody></table>
                      </center>

                    </td>
                  </tr>
                  <tr>
                    <td>
                      <div><!--[if mso]>
                        <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" href="http://" style="height:50px;v-text-anchor:middle;width:200px;" arcsize="8%" stroke="f" fillcolor="#178f8f">
                          <w:anchorlock/>
                          <center>
                        <![endif]-->
                              <a href="'.DIR.'konsumen/registrasi/aktifasi/'.$konsumen_nama.'/'.$token_aktifasi.'/aktifkan/" target="_blank" style="background-color:#178f8f;border-radius:4px;color:#ffffff;display:inline-block;font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:bold;line-height:50px;text-align:center;text-decoration:none;width:200px;-webkit-text-size-adjust:none;">Activate Account!</a>
                        <!--[if mso]>
                          </center>
                        </v:roundrect>
                      <![endif]--></div>
                      <br>
                      <br>
                    </td>
                  </tr>
                </tbody></table>

                

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" class="force-full-width" bgcolor="#414141">
                  <tbody><tr>
                    <td style="background-color:#414141;">
                    <br>
                    <br>
                      <img src="https://www.filepicker.io/api/file/R4VBTe2UQeGdAlM7KDc4" alt="google+">
                      <img src="https://www.filepicker.io/api/file/cvmSPOdlRaWQZnKFnBGt" alt="facebook">
                      <img src="https://www.filepicker.io/api/file/Gvu32apSQDqLMb40pvYe" alt="twitter">
                      <br>
                      <br>
                    </td>
                  </tr>
                  <tr>
                    <td style="color:#bbbbbb; font-size:12px;">
                      <a href="#">View in browser</a> | <a href="#">Unsubscribe</a> | <a href="#">Contact</a>
                      <br><br>
                    </td>
                  </tr>
                  <tr>
                    <td style="color:#bbbbbb; font-size:12px;">
                       © 2014 All Rights Reserved
                       <br>
                       <br>
                    </td>
                  </tr>
                </tbody></table>





            </td>
          </tr>
        </tbody></table>
    </center>
    </td>
  </tr>
</tbody></table>

</body></html>'                        
                        ;
                
                
                $kirim_email = new EmailModel();

                $kirim_email->sentEmail($to,$subject,$body);
                
  }
    
    public function emailAktifasiSukses($konsumen_email, $konsumen_nama) {
     //send email
            $to = $konsumen_email; 
            $subject = "aktifasi berhasil";
            $body = 
                    
                // Awal body email disini
'

<html xmlns="http://www.w3.org/1999/xhtml"><head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Neopolitan Progress Email</title>
  <!-- Designed by https://github.com/kaytcat -->
  <!-- Robot header image designed by Freepik.com -->

  <style type="text/css">

  /*
>>>>>>>>> CHANGING PROGRESS STEP IMAGES <<<<<<<<<<<<<<<

-If you would like to change the progress step images from 3 to any other step please view the html comments below.
-You just need to switch the provided image urls.


*/

  @import url(http://fonts.googleapis.com/css?family=Droid+Sans);

  /* Take care of image borders and formatting */

  img {
    max-width: 600px;
    outline: none;
    text-decoration: none;
    -ms-interpolation-mode: bicubic;
  }

  a {
    text-decoration: none;
    border: 0;
    outline: none;
    color: #bbbbbb;
  }

  a img {
    border: none;
  }

  /* General styling */

  td, h1, h2, h3  {
    font-family: Helvetica, Arial, sans-serif;
    font-weight: 400;
  }

  td {
    text-align: center;
  }

  body {
    -webkit-font-smoothing:antialiased;
    -webkit-text-size-adjust:none;
    width: 100%;
    height: 100%;
    color: #37302d;
    background: #ffffff;
    font-size: 16px;
  }

   table {
    border-collapse: collapse !important;
  }

  .headline {
    color: #ffffff;
    font-size: 36px;
  }

 .force-full-width {
  width: 100% !important;
 }

 .step-width {
  width: 110px;
  height: 111px;
 }



  </style>

  <style type="text/css" media="screen">
      @media screen {
         /*Thanks Outlook 2013! http://goo.gl/XLxpyl*/
        td, h1, h2, h3 {
          font-family: "Droid Sans", "Helvetica Neue", "Arial", "sans-serif" !important;
        }
      }
  </style>

  <style type="text/css" media="only screen and (max-width: 480px)">
    /* Mobile styles */
    @media only screen and (max-width: 480px) {

      table[class="w320"] {
        width: 320px !important;
      }

      img[class="step-width"] {
        width: 80px !important;
        height: 81px !important;
      }


    }
  </style>
</head>
<body class="body" style="padding:0; margin:0; display:block; background:#ffffff; -webkit-text-size-adjust:none" bgcolor="#ffffff">
<table align="center" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tbody><tr>
    <td align="center" valign="top" bgcolor="#ffffff" width="100%">
      <center>
        <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="600" class="w320">
          <tbody><tr>
            <td align="center" valign="top">

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="100%">
                  <tbody><tr>
                    <td style="font-size: 30px; text-align:center;">
    
                      <br>
                    </td>
                  </tr>
                </tbody></table>

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="100%" bgcolor="#4dbfbf">
                  <tbody><tr>
                    <td class="headline">
                    <br>
                      Registrasi Success
                    </td>
                  </tr>
                  <tr>
                    <td>
                    <br>
                      <center>
                        <table style="margin:0 auto;" cellspacing="0" cellpadding="0" class="force-width-80">
                          <tbody><tr>



                            <td>
                            <!-- ======== STEP ONE ========= -->
                            <!-- ==== Please use this url: https://www.filepicker.io/api/file/cdDaXwrhTd6EpvjpwqP1 in the src below in order to set the progress to one.

                            Then replace step two with this url: https://www.filepicker.io/api/file/MD29ZQs3RdK7mSu0VqxZ


                            Then replace step three with this url: https://www.filepicker.io/api/file/qnkuUNPS6TptLRIjWERA ==== -->
                              <img class="step-width" src="https://www.filepicker.io/api/file/MMVdxAuqQuy7nqVEjmPV" alt="Registrasi">
                            </td>



                            <!-- ======== STEP TWO ========= -->
                            <!-- ==== Please use this url: https://www.filepicker.io/api/file/QKOMsiThQcePodddaOHk in the src below in order to set the progress to two.

                            Then replace step three with this url: https://www.filepicker.io/api/file/qnkuUNPS6TptLRIjWERA

                            Then replace step one with this url: https://www.filepicker.io/api/file/MMVdxAuqQuy7nqVEjmPV ==== -->
                            <td>
                              <img class="step-width" src="https://www.filepicker.io/api/file/MD29ZQs3RdK7mSu0VqxZ" alt="Aktifasi">
                            </td>


                            <!-- ======== STEP THREE ========= -->
                            <!-- ==== Please use this url: https://www.filepicker.io/api/file/mepNOdHRTCMs1Jrcy2fU in the src below in order to set the progress to three.

                            Then replace step one with this url: https://www.filepicker.io/api/file/MMVdxAuqQuy7nqVEjmPV

                            Then replace step two with this url: https://www.filepicker.io/api/file/MD29ZQs3RdK7mSu0VqxZ ==== -->
                            <td>
                              <img class="step-width" src="https://www.filepicker.io/api/file/mepNOdHRTCMs1Jrcy2fU" alt="Sukses">
                            </td>
                          </tr>


                          <tr>
                            <td style="vertical-align:top; color:#187272; font-weight:bold;">
                              One
                            </td>
                            <td style="vertical-align:top; color:#187272; font-weight:bold;">
                              Two
                            </td>
                            <td style="vertical-align:top; color:#187272; font-weight:bold;">
                              Done!
                            </td>
                          </tr>
                        </tbody></table>
                      </center>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <center>
                        <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="60%">
                          <tbody><tr>
                            <td style="color:#187272;">
                            <br>
                            <br>
                             Selamat , Account anda telah berhasil diaktifasi, silahkan login untuk menikmati layanan kami
                            <br>
                            <br>
                            </td>
                          </tr>
                        </tbody></table>
                      </center>

                    </td>
                  </tr>
                  <tr>
                    <td>
                      <div><!--[if mso]>
                        <v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" href="http://" style="height:50px;v-text-anchor:middle;width:200px;" arcsize="8%" stroke="f" fillcolor="#178f8f">
                          <w:anchorlock/>
                          <center>
                        <![endif]-->
                            <a href="http://" style="background-color:#178f8f;border-radius:4px;color:#ffffff;display:inline-block;font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:bold;line-height:50px;text-align:center;text-decoration:none;width:200px;-webkit-text-size-adjust:none;">My Account</a>
                        <!--[if mso]>
                          </center>
                        </v:roundrect>
                      <![endif]--></div>
                      <br>
                      <br>
                    </td>
                  </tr>
                </tbody></table>

                

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" class="force-full-width" bgcolor="#414141">
                  <tbody><tr>
                    <td style="background-color:#414141;">
                    <br>
                    <br>
                      <img src="https://www.filepicker.io/api/file/R4VBTe2UQeGdAlM7KDc4" alt="google+">
                      <img src="https://www.filepicker.io/api/file/cvmSPOdlRaWQZnKFnBGt" alt="facebook">
                      <img src="https://www.filepicker.io/api/file/Gvu32apSQDqLMb40pvYe" alt="twitter">
                      <br>
                      <br>
                    </td>
                  </tr>
                  <tr>
                    <td style="color:#bbbbbb; font-size:12px;">
                      <a href="#">View in browser</a> | <a href="#">Unsubscribe</a> | <a href="#">Contact</a>
                      <br><br>
                    </td>
                  </tr>
                  <tr>
                    <td style="color:#bbbbbb; font-size:12px;">
                       © 2014 All Rights Reserved
                       <br>
                       <br>
                    </td>
                  </tr>
                </tbody></table>

            </td>
          </tr>
        </tbody></table>
    </center>
    </td>
  </tr>
</tbody></table>

</body></html>


'
 // Akhir body email disini
 ;
            $kirim_email = new EmailModel();   
                    
            $kirim_email->sentEmail($to,$subject,$body);
            
    }
  
}
