<?php
require 'PHPMailerAutoload.php';

$mail = new PHPMailer;

//$mail->SMTPDebug = 3;                               // Enable verbose debug output

$mail->isSMTP();                                      // Set mailer to use SMTP
$mail->Host = 'mail.smtp2go.com';  // Specify main and backup SMTP servers
$mail->SMTPAuth = true;                               // Enable SMTP authentication
$mail->Username = 'orders@stuffbuysharks.xyz';                 // SMTP username
$mail->Password = 'CS3250!!';                           // SMTP password
$mail->SMTPSecure = 'TLS';                            // Enable TLS encryption, `ssl` also accepted
$mail->Port = 2525;                                    // TCP port to connect to

$mail->setFrom('stuffbysharks@gmail.com', 'Shark Mailer');
$mail->addAddress($_SESSION["username"]);// Add a recipient    
$mail->addCC($_SESSION["email"] );         //Add CC    // Name is optional
$mail->addReplyTo('stuffbysharks@gmail.com', 'Information');


//$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
//$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
$mail->isHTML(true);                                  // Set email format to HTML

$mail->Subject = 'Shark Order Confirmation';
$mail->Recipient = $_SESSION["email"];
$mail->Header = "Your Shark Order is on it's Way!! ";
$mail->Body  = "Hello, \r\n\n";
$mail->Body .= "This is a confirmation of your recent shark order. \r\n";
$mail->Body .= "You ordered:. \r\n\n";
$mail->Body .= "Item ID#: $_SESSION["productid"] Quantity: $_SESSION["productquantity"] \r\n";
$mail->Body .= "If you would like to cancel your order please visit the customer portal, navigate to the order history page, and click delete. \r\n\n";
$mail->Body .= "Thanks for your order, \r\n\n";
$mail->Body .= "Sharky \r\n";


if(!$mail->send()) {
    echo 'Message could not be sent.';
    echo 'Mailer Error: ' . $mail->ErrorInfo;
} else {
    echo 'Message has been sent';
}


