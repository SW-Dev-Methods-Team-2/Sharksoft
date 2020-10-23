<?php

$dbservername =  "cs-3250-database-1testing.ctxpxr8jzoap.us-west-1.rds.amazonaws.com";
$dbusername = "admin";
$dbpassword = "cs3250db1";
$dbsheet = "cs3250main.sharktable"

$connect = mysql_connect($dbservername, $dbusername, $dbpassword); 
if (!connect) { die('Connection Failed: ' . mysql_error()); 
    { mysql_select_db(“database_name”, $connect);


        1