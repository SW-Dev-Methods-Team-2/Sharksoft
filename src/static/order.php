<?php

$email = filter_input(INPUT_POST, 'email');
$userid = filter_input(INPUT_POST, 'userid');
$productid = filter_input(INPUT_POST, 'itemid');
$productquantity = filter_input(INPUT_POST, 'itemquant');

$servername = "cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
$username = "admin";
$password = "cs3250db1";
//$dbname = "myDB";

// Create connection
$conn = new mysqli($servername, $username, $password);

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "INSERT INTO sales_orders (product_id, quantity, user_ID)
VALUES ($productid, $productquantity, $userid)";

if ($conn->query($sql) === TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>