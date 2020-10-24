

<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8" />
    <title>orderform</title>
    <meta name="description" content="page with orderform">
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
                    <a href="index.php">Home</a>
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
            <h2>Online Orders</h2>
            <h1>Use the form below to send us your order.</h1>
            
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

            $sql = "SELECT quantity, salecost FROM $maintable WHERE product_id = $productid ";

            ?>
            <p><span class="error">* required field</span></p>
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