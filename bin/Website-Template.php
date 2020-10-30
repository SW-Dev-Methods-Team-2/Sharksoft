<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><?php echo $title; ?></title>
        <link rel="stylesheet" type="text/css" href="Stylesheet.css" />
    </head>
    <body>
        <div id="wrapper">
            <div id="banner">
            </div>
            
            <nav id="navigation">
                <ul id="nav">
                    <li><a href="index.php">HOME</a></li>
                    <li><a href="#">ORDER</a></li>
                    <li><a href="#">LOG IN</a></li>
                    <li><a href="#">ABOUT</a></li>
               </ul>
            </nav>

            <div id="content_area">
                <?php echo $content; ?>
            </div>
            <div id="sidebar">
            
            </div>
        
            <footer><p>All rights reserved</p>
            </footer>
        </div>
    </body>
</html>