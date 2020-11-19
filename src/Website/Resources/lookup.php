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

if ($link->connect_error) {
  die("Connection failed: " . $conn->connect_error);
  }
  
if($_SERVER["REQUEST_METHOD"] == "POST"){

$user = filter_input(INPUT_POST, 'user_id');

$sql = "SELECT * FROM sales_orders WHERE userID like (userID)
VALUES ('$user')";

}

$result = query($sql);
//$result = $link->query ($sql) or die (error());

if($result->num_rows > 0) 
 {  
  echo "<table>"; // start a table tag in the HTML   
  while($row = fetch_assoc($result)){   
  //Creates a loop to loop through results
    echo "<tr><td>" . $row['1'] . "</td><td>" . $row['2'] . "</td></tr>";  
    }
  echo "</table>"; //Close the table in HTML
    
 }
 else
   { 
     echo "No result";  
   }
?>

</body> 

</html>
