<?php
// Include config file
require_once "config.php";
 
// Define variables 
$useremail = $password = $confirm_password = "";
$useremail_err = $password_err = $confirm_password_err = "";
//check connection
if ($link->connect_error) {
    die("Connection failed: " . $conn->connect_error);
  }

//set variables

$useremail = filter_input(INPUT_POST, 'useremail');
$password = filter_input(INPUT_POST, 'password');

//when submit is pressed
if($_SERVER["REQUEST_METHOD"] == "POST"){

    //check for empty email
    if(empty(trim($_POST["useremail"]))){
        $useremail_err = "Please enter a valid Email address.";
    }  
    //check for email format
    if (!filter_var($useremail, FILTER_VALIDATE_EMAIL)) {
    $useremail_err = "Invalid email format"; 
    
    }
    
        // Validate password
    if(empty(trim($_POST["password"]))){
        $password_err = "Please enter a password.";     
    } elseif(strlen(trim($_POST["password"])) < 6){
        $password_err = "Password must have atleast 6 characters.";
    } else{
        $password = trim($_POST["password"]);
        }
    
        // Validate confirm password
    if(empty(trim($_POST["confirm_password"]))){
        $confirm_password_err = "Please confirm password.";     
    } else{
        $confirm_password = trim($_POST["confirm_password"]);
        if(empty($password_err) && ($password != $confirm_password)){
            $confirm_password_err = "Password did not match.";
            }
        }
    

    //data validation and sql entry
     if(empty($useremail_err) && empty($password_err) && empty($confirm_password_err)){
        $sql = "INSERT INTO user_data (useremail, password1) VALUES (?, ?)";
            if($stmt = mysqli_prepare($link, $sql)){
                // Bind variables to the prepared statement as parameters
                mysqli_stmt_bind_param($stmt, "ss", $param_useremail, $param_password);

                $param_useremail = $useremail;
                $param_password = password_hash($password, PASSWORD_DEFAULT); // Creates a password hash
        
                
            
                // Attempt to execute the prepared statement
                if(mysqli_stmt_execute($stmt)){
                    // Redirect to login page
                    header("location: login.php");
                } else{
                    echo "Something went wrong. Please try again later.";
                }
    
                // Close statement
                mysqli_stmt_close($stmt);
            }

  
    }
}

$link->close();
    




?>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title>orderform</title>
    <meta name="description" content="page with orderform">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
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
                
            </div>
        
        </div>

    <div class="wrapper">
        <h2>Sign Up</h2>
        <p>Please fill this form to create an account.</p>
        <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
            <div class="form-group <?php echo (!empty($useremail_err)) ? 'has-error' : ''; ?>">
                <label>User Email</label>
                <input type="text" name="useremail" class="form-control" value="<?php echo $useremail; ?>">
                <span class="help-block"><?php echo $useremail_err;?></span>
            </div>    
            <div class="form-group <?php echo (!empty($password_err)) ? 'has-error' : ''; ?>">
                <label>Password</label>
                <input type="password" name="password" class="form-control" value="<?php echo $password; ?>">
                <span class="help-block"><?php echo $password_err; ?></span>
            </div>
            <div class="form-group <?php echo (!empty($confirm_password_err)) ? 'has-error' : ''; ?>">
                <label>Confirm Password</label>
                <input type="password" name="confirm_password" class="form-control" value="<?php echo $confirm_password; ?>">
                <span class="help-block"><?php echo $confirm_password_err; ?></span>
            </div>
            <div class="form-group">
                <input type="submit" class="btn btn-primary" value="Submit">
                <input type="reset" class="btn btn-default" value="Reset">
                
            </div>
            <p>Already have an account? <a href="login.php">Login here</a>.</p>
        </form>
    </div>    
</body>
</html>




