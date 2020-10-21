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
            <h1>Thank you for your order!</h1>
       </div>
    </div>
 
  <?php

  $servername = "cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
  $username = "admin";
  $password = "cs3250db1";
  $dbname = "cs3250main";
  $tablename = "sales_orders";
  $maintable = "simsharktable"

  // Create connection
  $conn = new mysqli($servername, $username, $password, $dbname);
    //echo "connected successfully";

  // Check connection
  if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
  }

  $email = filter_input(INPUT_POST, 'email');
  $useraddress = filter_input(INPUT_POST, 'address');
  $user = filter_input(INPUT_POST, 'user_id');
  $productid = filter_input(INPUT_POST, 'itemid');
  $productquantity = filter_input(INPUT_POST, 'itemquant');
  $orderdate = time();

  //$sql = "select $maintable"

  //to do: only allow them to order as much as we have

  //add a date field, email, location, fufilled
  //add fufilled column and date column/time stamp
  $sql = "INSERT INTO $tablename (product_id, quantity, userID, order_date, fufilled )
  VALUES ('$productid', '$productquantity', '$user', '$orderdate', 'no')";
 //to do: post price

  if ($conn->query($sql) === TRUE) {
    echo "<b>Your order: </b><br><br>";
    echo "<b>User ID:</b> $user <br><br><b>Email:</b> $email <br><br><b>Shipping Address:</b> $useraddress<br><br>";
    echo "<b>Item ID#:</b> $productid  <b>Quantity:</b> $productquantity";
  } else {
    echo "<br>There was an error with your order: " . $sql . "<br>" . $conn->error;
  }

//to do: update shark table with orders
  $sql = "UPDATE $maintable SET quantity= $newquantity  WHERE product_id = $productid ";
  if ($conn->query($sql) === TRUE) {
    $sql = "UPDATE $tablename SET fufilled= 'yes'  WHERE product_id = $productid ";
  }

  //send confirmation email
  //send internal order alert email

  $conn->close();
  ?>

</body> 

</html>