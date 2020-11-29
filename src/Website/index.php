<?php
// Initialize the session
session_start();
 
// Check if the user is logged in, if not then redirect him to login page
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: Resources/login.php");
    exit;
}
?>

<!DOCTYPE HTML>
<html>
<head> 
    <meta charset="utf-8" />
    <title>Home Page</title> 
    <link rel="stylesheet" href="Resources/styles.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
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
                    <a href="Resources/order.php">Order Form</a>
                </div>
                <div class="nav-linkwrapper">
                    <a href="Resources/lookup.php">Past Orders</a>
                </div>
                <div class="nav-linkwrapper">
                    <a href="Resources/login.php">Switch User</a>
                </div>
                <div class="nav-linkwrapper">
                    <a href="Resources/logout.php">Logout</a>
                </div>
                <div class="nav-linkwrapper">
                    <a href="Resources/deleteorder.php">Cancel Order</a>
                </div>
            </div>
        </div>
        <div class="page-header">
        <h1>Hi, <b><?php echo htmlspecialchars($_SESSION["username"]); ?></b> Welcome! </h1>
        <h1>Your User ID is : <b><?php echo htmlspecialchars($_SESSION["user_id"]); ?></b></h1>
    </div>

        <div class="content-wrapper">
            <div class="portfolio-items-wrapper">
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(Resources/images/shark1.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div> 
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(Resources/images/shark2.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(/Resources/images/shark3.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(Resources/images/mantaray.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            NOT A SHARK
                        </div>
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(/Resources/images/shark5.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(/Resources/images/shark6.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
                <!-- extra sharks
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(/Resources/images/shark7.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(Website/images/shark8.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>                
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(/Resources/images/shark4.jpg)"></div>

                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(Website/images/shark10.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(Website/images/shark11.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div> 
                <div class="portfolio-item-wrapper">
                    <div class="portfolio-img-background" style="background-image:url(/Resources/images/shark4.jpg)"></div>
                    <div class="img-text-wrapper">
                        <div class="subtitle">
                            A SHARK
                        </div>
                    </div>
                </div>
            -->
                
            </div>
        </div>
    </div>
</body>
</html>