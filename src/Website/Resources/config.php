<?php
/* Database credentials. */
define('DB_SERVER', 'cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com');
define('DB_USERNAME', 'admin');
define('DB_PASSWORD', 'cs3250db1');
define('DB_NAME', 'cs3250main');
 
/* Attempt to connect to MySQL database */
$link = mysqli_connect(DB_SERVER, DB_USERNAME, DB_PASSWORD, DB_NAME);

// Check connection
if($link === false){
    die("ERROR: Could not connect the the Shark Database. " . mysqli_connect_error());
}
?>