<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8" />
    <title>orderform</title>
    <meta name="description" content="Order Processing">
    <link rel="stylesheet" href="styles.css">
</head>


<body class="">
    <div class="container">
        <div class="nav-wrapper">
            <div class="leftside">
                <div class="brand">
                    <div>STUFFBUYSHARKS</div>
                </div>
                <div class="motto">
                    <div>THE ONE STOP SHOP FOR ALL YOUR SHARK NEEDS</div>
                </div>
            </div>
            <div class="rightside">
                <div class="nav-linkwrapper">
                    <a href="index.html">Home</a>
                </div>

                <div class="nav-linkwrapper">
                    <a href="orderform.html">Order Form</a>
                </div>
                <!-- 
                <div class="nav-linkwrapper">
                    <a href="login.html">Login</a>
                </div>
                -->

            </div>
        </div>
    </div>
    <div class="content-wrapper">
       <div class="order-form-wrapper">
            <h1>Thank you for logging in!</h1>
       </div>
    </div>
 

<?php

$email = filter_input(INPUT_POST, 'email');
$user = filter_input(INPUT_POST, 'user_id');


$servername = "cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
$username = "admin";
$password = "cs3250db1";
$dbname = "cs3250main";
$tablename = "user_data";
//$dbname = "cs-3250main.sales_orders";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
  //echo "connected successfully";

// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}
//columns for user data: user_id, first_name, last_name, 
$sql = "INSERT INTO $tablename (product_id, quantity, userID)
VALUES ('$productid', '$productquantity', '$user')";

if ($conn->query($sql) === TRUE) {
  echo "New record created successfully";
} else {
  echo "Error: " . $sql . "<br>" . $conn->error;
}


$conn->close();
?>

</body> 

</html>