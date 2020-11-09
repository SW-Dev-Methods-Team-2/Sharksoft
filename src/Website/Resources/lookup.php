<?php
// Initialize the session
session_start();
 
// Check if the user is logged in, if not then redirect him to login page
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}
/*require_once "config.php";
if ($link->connect_error) {
    die("Connection Failed: " . $conn->connect_error);
}*/

// Search Method
$ordersearch = "SELECT date_, userID, product_id, quantity, sale_price, shipping_address 
  FROM sales_orders ";
$result = $link->query($ordersearch);

// Check to see if the search returned any results
if($result->num_rows > 0) {
  
  // Output data of each row
  while($row = $result->fetch_assoc()){
    echo "<br> Order Date: " . $row["date_"]. "User ID: " .$row["userID"]. "Product: " .$row["product_id"]. 
         "Quantity: " .$row["quantity"]. "Price: " .$row["sale_price"]. "Shipping Address: " .$row["shipping_address"]. "<br>";
  }
} else {
    echo "No Orders Found for that User ID.";
}
?>
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
    </body>
</html>