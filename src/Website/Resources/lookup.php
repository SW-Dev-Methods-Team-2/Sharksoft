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
                    <a href="order.php">Order Form</a>
                </div>
                <div class="nav-linkwrapper">
                    <a href="logout.php">Logout</a>
                </div>              

            </div>
        </div>
        <div class="page-header">
        <h1>Hi, <b><?php echo htmlspecialchars($_SESSION["username"]); ?></b> Welcome! </h1>
        <h1>Your User ID is : <b><?php echo htmlspecialchars($_SESSION["user_id"]); ?></b></h1>
    </div>
    <div class="content-wrapper">
       <div class="order-form-wrapper">
            <h1>Your past orders:</h1>
            <h2>To cancel an order click <a href="deleteorder.php">here</a></h2>
       </div>
    </div>

<?php

require_once "config.php";

if ($link->connect_error) {
  die("Connection failed: " . $conn->connect_error);
  echo "connection error";
}
  
$user = $_SESSION["user_id"];

//$query = "SELECT * FROM sales_orders WHERE userID like '%$user%'"; //all columns
$query = "SELECT date_,	email,	shipping_address,	product_id,	quantity, sale_price, userID, order_num, status_  FROM sales_orders WHERE userID like '%$user%'"; //specific columns
if ($result = mysqli_query($link, $query)) {
  $all_property = array();  //declare an array for saving property

  //showing property
  if(mysqli_num_rows($result) > 0){
    echo '<table class="data-table">
            <tr class="data-heading">';  //initialize table tag
    while ($property = mysqli_fetch_field($result )) {
        echo '<td><b><u>' . $property->name . '</u></b></td>';  //get field name for header
        array_push($all_property, $property->name);  //save those to array
    }
    echo '</tr>'; //end tr tag

    //showing all data
    while ($row = mysqli_fetch_array($result)) {
        echo "<tr>";
        foreach ($all_property as $item) {
            echo '<td>' . $row[$item] . '</td>'; //get items using property value
        }
        echo '</tr>';
    }
    echo "</table>";
    mysqli_free_result($result);
  } 
  else{
          echo "No records matching your query were found.";
  }
}
else {
  echo "ERROR: Could not execute search. ";
}


?>

</body> 

</html>


