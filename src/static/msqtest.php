<?php
require_once "config.php";

// Create connection

// Check connection
if ($link->connect_error) {
  die("Connection failed: " . $link->connect_error);
} 

$sql = "INSERT INTO user_data (useremail, password1)
VALUES ('testing123', 'testing123')";

if ($link->query($sql) === TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>