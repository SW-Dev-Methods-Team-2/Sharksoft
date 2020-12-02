<?php
require 'PHPMailerAutoload.php';
require 'logindata.php';

//$mail->SMTPDebug = 3;                               // Enable verbose debug output

$sql1 = "SELECT TOP 1 product_id FROM sharktable ";
    $query1 = mysqli_query($link,$sql1);
    $result1 = mysqli_fetch_assoc($query1);
    $randomproduct = $result1['product_id'];

$mail = new PHPMailer(true);


$mail->isSMTP();                                      // Set mailer to use SMTP
$mail->Host = $mailHost;  // Specify main and backup SMTP servers
$mail->SMTPAuth = true;                               // Enable SMTP authentication
$mail->Username = $mailUser;                 // SMTP username // apikey CS3250Key2
$mail->Password = $mailword;                           // SMTP password  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
$mail->SMTPSecure = $mailSecure;                            // Enable TLS encryption, `ssl` also accepted
$mail->Port = $mailPort;                                    // TCP port to connect to

$mail->setFrom('stuffbuysharks@gmail.com', 'Shark Mailer');
$mail->addAddress($_SESSION["username"]);// Add a recipient    
$mail->addCC($_SESSION["email"] );         //Add CC    // Name is optional
$mail->addReplyTo('stuffbysharks@gmail.com', 'Information');




//$mail->addAttachment('/var/tmp/file.tar.gz');         // Add attachments
//$mail->addAttachment('/tmp/image.jpg', 'new.jpg');    // Optional name
$mail->isHTML(true);                                  // Set email format to HTML

$mail->isHTML(true); // Set email format to HTML
$mail->Subject = 'You have to check out this shark!!';
$mail->Recipient = $_SESSION["email"];
$mail->Header = "Other Sharks you might like!! ";

$mail->Body = "Hey, <br />";
$mail->Body .= "We think you'd really like shark #";
$mail->Body .= $randomproduct;
$mail->Body .= "<br /> Something to think about <br />";
$mail->Body .= "Thanks for your order, <br />";
$mail->Body .= "Sharky <br />";
 
if(!$mail->send()) {
 echo 'Message could not be sent.';
 echo 'Mailer Error: ' . $mail->ErrorInfo;
} else {
 echo 'Message has been sent';
}

