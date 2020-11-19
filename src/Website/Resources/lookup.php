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

$search = $_POST["search"];
//mysql_connect("localhost", "username", "password") OR die (mysql_error());
//mysql_select_db ("your_db_name") or die(mysql_error());
 

$query = "SELECT * FROM sales_orders WHERE userID LIKE '$_SESSION["user_id"]'";

$result = mysql_query($query) or die (mysql_error());

if($result->num_rows > 0) 
 {  
  echo "<table>"; // start a table tag in the HTML   
  while($row = mysql_fetch_array($result)){   //Creates a loop to loop through results
    echo "<tr><td>" . $row['date'] . "</td><td>" . $row['age'] . "</td></tr>";  //$row['index'] the index here is a field name
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