<?php
// Initialize the session
session_start();
 
// Check if the user is logged in, if not then redirect him to login page
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}
?>


<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8" />
    <title>Search</title>
    <meta name="description" content="Searching">
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
                    <a href="../index.php">Home</a>
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
            <h1>Your past orders:</h1>
       </div>
    </div>

<?php

require_once "config.php";
  maintable = 
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

</body> 

</html>

<?php  
/*
  $search = $_POST["search"];
  mysql_connect("localhost", "username", "password") OR die (mysql_error());
  mysql_select_db ("your_db_name") or die(mysql_error());

  $query = "SELECT * FROM `profile` WHERE `email`='$search'";

  $result = mysql_query($query) or die (mysql_error());

  if($result) 
   {    
      while($row=mysql_fetch_row($result))   
       {      
          echo $row[0],$row[1],$row[2];   
       }    
     }
   else
     { 
       echo "No result";  
     }
 ?>

<form action="profile.php" method="post">  
  <input type="text" name="search"><br>  
  <input type="submit">
</form>
*/   