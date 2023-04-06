<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
 <head>
     <link rel="stylesheet" href="../public/css/signIn.css">
    <script src="https://kit.fontawesome.com/ec00a021e5.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 </head>
 <title>Login Form</title>
 <body>
 	<div class="popup-overlay" id="overlay-window"></div>
     <div class="header">
        <h1 class="oneLine" id="welcome">Welcome! to the e-Survey for Shangri-La</h1>
        <h3 id="info">Please login or create a account to continue</h3>
     </div>  
     
     <div class="loginForm" id="loginForm2">
        <h1 class=formTitle>Login Here</h1>
       
        <form action="/login" method="post" class="loginAccount" id="loginForm">
        	 
            <p id="errorMessage">${error}</p>
      
            <p>User ID</p>
            <input type="text" name="username" placeholder="Enter User ID">
            <p>Password</p>
            <input type="password" name="password" placeholder="Enter Password">
            <input type="submit" value="Log in">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <a class="login-link" id="createAccountLink"href="#">Don't have an account?</a>
            
        </form>

     </div>
     
     <div  class="createAccount" id="createAccountForm">
     		<h1 class=formTitle>Create Account</h1>
            <p>User ID</p>
            <input type="text" name="" placeholder="Enter your Email" id="newUserID">
            <span class="error" id="errorID"></span>
            <p>Full name</p>
            <input type="text" name="" placeholder="Enter your Full Name" id="newFullName">
            <span class="error" id="errorName"></span>
            <p>Date of Birth</p>
            <input type="date" name="dob" id="newDOB">
            <span class="error" id="errordob"></span>
            <p>Home Address</p>
            <input type="text" name="" placeholder="Enter your address" id="newAddress">
            <span class="error" id="erroradd"></span>
            <p>Password</p>
            <input type="password" name="" placeholder="Enter a password" id="newPassword">
            <span class="error" id="errorPassword"></span>
            <p>Confirm Password</p>
            <input type="password" name="" placeholder="Enter a password" id="confirmPassword" onKeyUp="validatePassword()">
            <span class="error" id="errorConfirmPassword"></span>
            <p>SNI</p>
            <span class="inputIcon">
                <span id="qrStart">
                    <span class="tooltiptext">Scan a QR code</span>
                    <i class= "fa fa-qrcode icon"></i>
                </span>   
                <input type="text" name="" placeholder="Enter SNI" id="newSni" onKeyUp="validateSNI()">
            </span>
            <span class="error" id="errorSNI"></span>
            <input type="submit" onclick="createNewUser()" name="signUp" value="Register" id="createAccountButton">
            <a class="login-link" id="loginLink" href="#">Already have an account?</a>
       </div>
       
       <div class="popup none" id="qrPopup">
            <div class="popup-close" onclick="closeQRScanner()">&times;</div>
            <div class="row">
                <div class="col">
                    <div id="qr-reader"></div>
                </div>
            </div>

       </div>
<script src="../public/script/html5-qrcode.min.js"></script>  
<script src="../public/script/signIn.js"></script>    
 </body>
</html>