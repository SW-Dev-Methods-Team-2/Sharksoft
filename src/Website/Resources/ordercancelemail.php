<?php
require 'PHPMailerAutoload.php';

$mail = new PHPMailer;

//$mail->SMTPDebug = 3;                               // Enable verbose debug output

$mail->isSMTP();                                      // Set mailer to use SMTP
$mail->Host = $mailHost;  // Specify main and backup SMTP servers
$mail->SMTPAuth = true;                               // Enable SMTP authentication
$mail->Username = $mailUser;                 // SMTP username // apikey CS3250Key2
$mail->Password = $mailword;                           // SMTP password  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
$mail->SMTPSecure = $mailSecure;                            // Enable TLS encryption, `ssl` also accepted
$mail->Port = $mailPort;                                  // TCP port to connect to

$mail->setFrom('stuffbuysharks@gmail.com', 'Shark Mailer');
$mail->addAddress($_SESSION["username"]);// Add a recipient    
$mail->addCC($_SESSION["email"] );         //Add CC    // Name is optional
$mail->addReplyTo('stuffbysharks@gmail.com', 'Information');




//$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
//$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
$mail->isHTML(true);                                  // Set email format to HTML

$mail->isHTML(true); // Set email format to HTML
$mail->Subject = 'Shark Order Cancelation Confirmation';
$mail->Recipient = $_SESSION["email"];
$mail->Header = "Your Shark Order Has Been Canceled!! ";

$mail->Body = "Hello, <br />";
$mail->Body .= "This is a confirmation of your recent shark order cancelasion. <br />";
$mail->Body .= "You ordered: <br />";
$mail->Body .= "Item ID#: ";
$mail->Body .= $_SESSION["itemid"] ;
$mail->Body .= "<br /> Quantity: ";
$mail->Body .= $_SESSION["quantity"] ;
$mail->Body .= "<br /> Order Number: ";
$mail->Body .= $_SESSION["Order Number"] ;
$mail->Body .= "<br /> This order has been canceled and will not be shipped. Please log into your account and reorder if you change your mind. <br />";
$mail->Body .= "Thank you for your sharky business!, <br />";
$mail->Body .= "Sharky <br />";
 
if(!$mail->send()) {
 echo 'Message could not be sent.';
 echo 'Mailer Error: ' . $mail->ErrorInfo;
} else {
 echo 'Message has been sent';
}

