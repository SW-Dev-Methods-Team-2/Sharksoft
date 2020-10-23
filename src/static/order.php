<?php
// Initialize the session
session_start();
 
// Check if the user is logged in, if not then redirect him to login page
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}

require_once "config.php";
if ($link->connect_error) {
    die("Connection failed: " . $conn->connect_error);
  }

  if($_SERVER["REQUEST_METHOD"] == "POST"){
    $email = filter_input(INPUT_POST, 'email');
    $useraddress = filter_input(INPUT_POST, 'address');
    $user = filter_input(INPUT_POST, 'user_id');
    $productid = filter_input(INPUT_POST, 'itemid');
    $productquantity = filter_input(INPUT_POST, 'itemquant');
    $orderdate = time();
    $sql = "INSERT INTO sales_orders (product_id, quantity, userID)
    VALUES ('$productid', '$productquantity', '$user')";

    if ($link->query($sql) === TRUE) {
        echo "<b>Your order: </b><br><br>";
        echo "<b>User ID:</b> $user <br><br><b>Email:</b> $email <br><br><b>Shipping Address:</b> $useraddress<br><br>";
        echo "<b>Item ID#:</b> $productid  <b>Quantity:</b> $productquantity";
    }
    else{
        echo "failed to update db";
    }
  }


?>


<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8" />
    <title>orderform</title>
    <meta name="description" content="page with orderform">
    <link rel="stylesheet" href="styles.css">
</head>
<div class="page-header">
        <h1>Hi, <b><?php echo htmlspecialchars($_SESSION["username"]); ?></b>. Welcome! .</h1>
        <h1>Your User ID is : <b><?php echo htmlspecialchars($_SESSION["user_id"]); ?></b></h1>
    </div>

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
            <h1>Online Orders</h1>
            <p>Use the form below to send us your order.</p>
            
            <form action="order.php" method="post">
                <label for="user_id">User ID:</label><br>
                <input type="text" id="user_id" name="user_id" value="userid#" required><br><br>
                <label for="email">Enter your email:</label><br>
                <input type="email" id="email" name="email" required><br><br>
                <label for="address">Shipping address:</label><br>
                <input type="text" id="address" name="address" value="123 shark lane Sharksville CO 12345" required><br><br>
                <label for="itemid">Item ID:</label><br>
                <input type="text" id="itemid" name="itemid" value="item id#" ><br>
                <label for="itemquant"> Item quantity:</label><br>
                <input type="number" id="itemquant" name="itemquant" min="1" max="10" ><br><br>
                <label for="myfile">Or upload an order file:</label><br>
                <input type="file" id="myfile" name="myfile"><br><br>
                <input type="submit" value="Submit">
                <input type="reset">
              </form>
       </div>
    </div>
    
</body> 
</html>





