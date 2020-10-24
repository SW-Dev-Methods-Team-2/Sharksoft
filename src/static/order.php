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

// Include config file
require_once "config.php";
  //$maintable = "shark_table"

  // Check connection
  if ($link->connect_error) {
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

<<<<<<< HEAD
  if ($conn->query($sql) === TRUE) {
=======
 //to do: update shark table with orders
  if ($link->query($sql) === TRUE) {
>>>>>>> 015259e72b4916d21ed884367523df29162d23d5
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

  //send confirmation email (needs installed and working email system!)
  //$to = $email;
  //$subject = "New SharkSoft Order Confirmation";
  //$msg = "Thank you for your order! \nYour Order Details:\n Product ID: " + $productid + "\nQuantity: " + $productquantity + "\nShipping Address: " + $useraddress + "\n\nPlease let us know if there are any of this information is incorrect. \nThank You!";
  //$msg = wordwrap($msg,70);
  //$headers = "From: webmaster@example.com" . "\r\n" .

   //mail($to,$subject,$msg,$headers);
  //send internal order alert email
<<<<<<< HEAD

  $conn->close();
=======
  $link->close();
>>>>>>> 015259e72b4916d21ed884367523df29162d23d5
  ?>

</body> 

</html>