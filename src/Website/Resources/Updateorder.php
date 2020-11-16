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
// Delete order method
$deleteOrder = "DELETE "order number" FROM sales_orders WHERE id=3";

if ($link->query($deleteOrder) === TRUE) {
    echo "Order Cancelled Successfully";
} else {
    echo "Error Order Not Cancelled: " .$link->error;
}

//Update Order Method
$editOrder = "UPDATE sales_orders SET WHERE id=3";

if ($link->query($editOrder) === TRUE) {
    echo "Order Updated Successfully";
} else {
    echo "Error Order Not Updated: " .$link->error;
}
?>
