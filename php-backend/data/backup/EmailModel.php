<?php

include('../libs/PhpMailer/phpmailer.php');

//require '../vendor/autoload.php';

class EmailModel extends PhpMailer
{
    
  


    public function sentEmail($to,$subject,$body){

			$mail = new EmailModel();
			$mail->setFrom(SITEEMAIL);
			$mail->addAddress($to);
			$mail->subject($subject);
			$mail->body($body);
			$mail->send();

if(!$mail->send()) {

   return $mail->ErrorInfo;

  } else {

    return true;

  }

}
 
  // Set default variables for all new objects
    public $From     = 'noreply@yuly-laundry.com';
    public $FromName = 'Yuly Laundry';
    //public $Host     = 'smtp.gmail.com';
    //public $Mailer   = 'smtp';
    //public $SMTPAuth = true;
    //public $Username = 'email';
    //public $Password = 'password';
    //public $SMTPSecure = 'tls';
    public $WordWrap = 75;

    public function subject($subject)
    {
        $this->Subject = $subject;
    }

    public function body($body)
    {
        $this->Body = $body;
    }

    public function send()
    {
         
        $this->AltBody = strip_tags(stripslashes($this->Body))."\n\n";
        $this->AltBody = str_replace("&nbsp;", "\n\n", $this->AltBody);
        return parent::send();
    }
    
    

    public function sentEmailRegistrasi($to,$subject){
            
    $subject = "Pendaftaran berhasil";
    $body = '<html xmlns="http://www.w3.org/1999/xhtml"><head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Neopolitan Confirm Email</title>
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

 .force-width-80 {
  width: 80% !important;
 }

  </style>

  <style type="text/css" media="screen">
      @media screen {
         /*Thanks Outlook 2013! http://goo.gl/XLxpyl*/
        td, h1, h2, h3 {
          font-family: `Droid Sans`, `Helvetica Neue`, `Arial`, `sans-serif` !important;
        }
      }
  </style>

  <style type="text/css" media="only screen and (max-width: 480px)">
    /* Mobile styles */
    @media only screen and (max-width: 480px) {

      table[class="w320"] {
        width: 320px !important;
      }

      td[class="mobile-block"] {
        width: 100% !important;
        display: block !important;
      }


    }
  </style>
</head>
<body class="body" style="padding:0; margin:0; display:block; background:#ffffff; -webkit-text-size-adjust:none" bgcolor="#ffffff">
<table align="center" cellpadding="0" cellspacing="0" class="force-full-width" height="100%">
  <tbody><tr>
    <td align="center" valign="top" bgcolor="#ffffff" width="100%">
      <center>
        <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="600" class="w320">
          <tbody><tr>
            <td align="center" valign="top">

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" class="force-full-width">
                  <tbody><tr>
                    <td style="font-size: 30px; text-align:center;">
                      <br>
                        Awesome Co
                      <br>
                      <br>
                    </td>
                  </tr>
                </tbody></table>

                <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" class="force-full-width" bgcolor="#4dbfbf">
                  <tbody><tr>
                    <td>
                    <br>
                      <img src="https://www.filepicker.io/api/file/carctJpuT0exMaN8WUYQ" width="224" height="240" alt="robot picture">
                    </td>
                  </tr>
                  <tr>
                    <td class="headline">
                      Good News!
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <center>
                        <table style="margin: 0 auto;" cellpadding="0" cellspacing="0" width="60%">
                          <tbody><tr>
                            <td style="color:#187272;">
                            <br>
                             Your order has shipped! To track your order or make any changes please click the button below.
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
                            <a href="http://" style="background-color:#178f8f;border-radius:4px;color:#ffffff;display:inline-block;font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:bold;line-height:50px;text-align:center;text-decoration:none;width:200px;-webkit-text-size-adjust:none;">My Order</a>
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

</body></html>';
            

			$mail = new EmailModel();
			$mail->setFrom(SITEEMAIL);
			$mail->addAddress($to);
			$mail->subject($subject);
			$mail->body($body);
			$mail->send();

if(!$mail->send()) {

   return $mail->ErrorInfo;

  } else {

    return true;

  }

}
    
		

}