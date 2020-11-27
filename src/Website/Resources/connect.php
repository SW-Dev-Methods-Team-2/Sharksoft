<?php

require 'logindata.php';

$username = filter_input(INPUT_POST, 'email');
if (!empty($username)){
$host = "localhost"; //I think that we have to add the host of the website later here     
$dbservername =  $dbserver ;
$dbusername = $dbusern;
$dbpassword = $dbpassw;
// Create connection
$conn = new mysqli($host, $dbservername, $dbusername, $dbpassword);

// Check connection
if (!$conn) {die("Connection failed: " .mysqli_connect_error());}
echo "Connected Successfully";
}
else{
$sql = "INSERT INTO Account(email) values ('$username')";
if ($conn->query($sql)){
echo "New Record is inserted successfully";    
}
else{
echo "Error: ". $sql ."
". $conn->error;    
}
$conn->close();
}
else{
echo "Email should not be empty.";
die();    
}
?>
