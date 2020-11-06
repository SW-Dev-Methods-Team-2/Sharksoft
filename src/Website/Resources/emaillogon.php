<?php
 require_once "Mail.php";
 
 $from = "Shartest <stuffbysharks@gmail.com>";
 $to = $_SESSION["username"];
 $subject = "Order Confirmation";
 $body ="testing email functions";
 $host = "smtp.gmail.com";
 $username = "stuffbysharks@gmail.com";
 $password = "CS2050!!";
 
 $headers = array ('From' => $from,
   'To' => $to,
   'Subject' => $subject);
 $smtp = Mail::factory('smtp',
   array ('host' => $host,
     'auth' => true,
     'username' => $username,
     'password' => $password));
 
 $mail = $smtp->send($to, $headers, $body);
 
 if (PEAR::isError($mail)) {
   echo("<p>" . $mail->getMessage() . "</p>");
  } else {
   echo("<p>Message successfully sent!</p>");
  }
 ?>