<?php
// Initialize the session
session_start();
 
// Check if the user is logged in, if not then redirect him to login page
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}
require_once "orderconfirmationemail.php";

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
                    <a href="../index.php">Home</a>
                </div>

                <div class="nav-linkwrapper">
                    <a href="order.php">Order Form</a>
                </div>
                <div class="nav-linkwrapper">
                    <a href="logout.php">Logout</a>
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
            <h1>Thank you for your order!</h1>
            <h1>Your order of </h1><?php echo htmlspecialchars($_SESSION["quantity"]); ?><h1> </h1><?php echo htmlspecialchars($_SESSION["itemid"]); ?><h1>is being processed</h1>
       </div>
       <h1>To place another order click on the order button above! </h1>
    </div>
    
</body> 

</html>