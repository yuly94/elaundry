<?php

//include('../libs/PhpMailer/phpmailer.php');

//require '../vendor/autoload.php';

class EmailModel extends PHPMailer
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
    
    
 

}
