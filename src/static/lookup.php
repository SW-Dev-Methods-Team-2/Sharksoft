<?php


$user = filter_input(INPUT_POST, 'user_id');

$servername = "cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
$username = "admin";
$password = "cs3250db1";
$dbname = "cs3250main";
$tablename = "sales_orders";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
  echo "connected successfully";

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

//$sql = "INSERT INTO $tablename (product_id, quantity, userID)
//VALUES ('$productid', '$productquantity', '$user')";

if ($conn->query($sql) === TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>