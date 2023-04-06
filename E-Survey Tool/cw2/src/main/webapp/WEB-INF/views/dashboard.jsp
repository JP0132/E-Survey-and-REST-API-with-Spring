<!DOCTYPE html>
<html>
 <head>
    <link rel="stylesheet" href="../public/css/dashboard.css">
    <link rel="stylesheet" href="../public/css/questions.css">
    <script src="https://kit.fontawesome.com/ec00a021e5.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 </head>
 <title>Dashboard</title>
 <body>
	  <div class="popup-overlay" id="overlay-window"></div>

    <div class="header">
        <h1 class="oneLine" id="welcome">Hello! to your</h1>
        <h1 class="oneLine" id="app"> e-Survey Shangri-La</h1>
        <div class="logoutContainer">
         	<button id="logoutBtn" onclick="location.href='https://localhost:8443/logout';">Logout</button>
     	</div>
     </div>
     
   
     
     <ul class="questions" id="questionList"></ul>

     

    <script src="../public/script/questions.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
 
 </body>
</html>