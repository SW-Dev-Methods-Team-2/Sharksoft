<?php
require 'PHPMailerAutoload.php';

$mail = new PHPMailer;

//$mail->SMTPDebug = 3;                               // Enable verbose debug output

$mail->isSMTP();                                      // Set mailer to use SMTP
$mail->Host = 'smtp.sendgrid.net';  // Specify main and backup SMTP servers
$mail->SMTPAuth = true;                               // Enable SMTP authentication
$mail->Username = 'apikey';                 // SMTP username // apikey CS3250Key2
$mail->Password = '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';                           // SMTP password  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
$mail->SMTPSecure = 'TLS';                            // Enable TLS encryption, `ssl` also accepted
$mail->Port = 587;                                    // TCP port to connect to

$mail->setFrom('stuffbuysharks@gmail.com', 'Shark Mailer');
$mail->addAddress($_SESSION["username"]);// Add a recipient    
$mail->addCC($_SESSION["email"] );         //Add CC    // Name is optional
$mail->addReplyTo('stuffbysharks@gmail.com', 'Information');




//$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
//$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
$mail->isHTML(true);                                  // Set email format to HTML
$mail->Subject = 'Shark Order Confirmation';
$mail->Recipient = $_SESSION["email"];
$mail->Header = "Your Shark Order is on it's Way!! ";
$mail->Body = "Hello, <br />";
$mail->Body .= "This is a confirmation of your recent shark order. <br />";
$mail->Body .= "You ordered: <br />";
$mail->Body .= "Item ID#: ";
$mail->Body .= $_SESSION["itemid"]  ;
$mail->Body .= "<br /> Quantity: ";
$mail->Body .= $_SESSION["quantity"] ;
$mail->Body .= "<br />  If you would like to cancel your order please visit the customer portal, navigate to the order history page, and click delete. <br />";
$mail->Body .= "Thanks for your order, <br />";
$mail->Body .= "Sharky <br />";
 
if(!$mail->send()) {
 echo 'Message could not be sent.';
 echo 'Mailer Error: ' . $mail->ErrorInfo;
} else {
 echo 'Message has been sent';
}



