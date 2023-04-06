
$('#createAccountForm').hide();



//When sign up text button is clicked
$('#createAccountLink').click(function(e){
    e.preventDefault();
    $(this).addClass('active');
	
    //Show the create account form
    $('#createAccountForm').show();
    //Hide the sign in form
    $('#loginForm2').hide();

})




//When sign in text button is clicked
$('#loginLink').click(function(e){
    e.preventDefault();
    $(this).addClass('active');
	
    //Show the sign in form
    $('#loginForm2').show();
    //Hide the create account form
    $('#createAccountForm').hide();
	
	

})

//Using Html5-QRCode scanner by mebjas -> github link: https://github.com/mebjas/html5-qrcode
//Starts the qr code scanner on icon click
document.getElementById("qrStart").addEventListener("click", function(){
    openQRScanner();
})

//creates a new QR code scanner
var html5QrcodeScanner = new Html5QrcodeScanner("qr-reader", { fps: 10, qrbox: 250 });

//Opens the scanner with a overlay
function openQRScanner(){
    document.getElementById("qrPopup").classList.remove("none");
    document.getElementById("qrPopup").classList.add("block");
    var overlay = document.getElementById('overlay-window');
    overlay.style.display = 'block';
    

    html5QrcodeScanner.render(onScanSuccess, onScanError);
    function onScanSuccess(qrCodeMessage) {
        document.getElementById("newSni").value = qrCodeMessage;
        html5QrcodeScanner.clear();
        closeQRScanner();    
    }
    
    function onScanError(errorMessage) {
       
           
    }
    

}
//Closes the scanner
function closeQRScanner(){
    html5QrcodeScanner.clear();

    document.getElementById("qrPopup").classList.remove("block");
    document.getElementById("qrPopup").classList.add("none");

    var overlay = document.getElementById('overlay-window');
    overlay.style.display = 'none';
}


//validates the SNI number whilsts typing
function validateSNI(){
	var checksni = document.getElementById('newSni').value;
	if(checksni.length < 8 || checksni.length > 8){
		document.getElementById('errorSNI').innerHTML = "";
		document.getElementById('errorSNI').innerHTML += "SNI numbers must be only 8 characters long<br/>";
		document.getElementById('newSni').style.color = "red";
	}
	else{
		document.getElementById('errorSNI').innerHTML = "";
		document.getElementById('newSni').style.color = "";
	}
}

//validates confirm password field if it matches with the field before it
function validatePassword(){
	var checkPassword = document.getElementById('newPassword').value;
	var conPassword = document.getElementById('confirmPassword').value;
	if(conPassword != checkPassword){
		document.getElementById('errorConfirmPassword').innerHTML = "";
		document.getElementById('errorConfirmPassword').innerHTML += "Passwords do not match<br/>";
		document.getElementById('confirmPassword').style.color = "red";
	}
	else{
		document.getElementById('errorConfirmPassword').innerHTML = "";
		document.getElementById('confirmPassword').style.color = "";
	}
}

//Gets the age from provided date of birth
function getAge(dob){
	var today = new Date();
    var birthDate = new Date(dob);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
	return age;
}

//Validates the whole form when submitting
//Shows error messages if errors are found
function validateForm(){
	var userID = document.getElementById('newUserID').value;
	var fullName = document.getElementById('newFullName').value;
	var dob = document.getElementById('newDOB').value;
	var password = document.getElementById('newPassword').value;
	var address = document.getElementById('newAddress').value;
	var sni = document.getElementById('newSni').value;
	
	var form = document.getElementById("createAccountForm");
	var errors = [...form.getElementsByClassName('error')];
	let errorCounter = 0;
	
	for(let i = 0; i < errors.length; i++){
		errors[i].innerHTML = '';
	}
	
	var emailRegex = /\S+@\S+\.\S+/;
	
	if(userID == '' || userID == null){
		errorCounter++;
		document.getElementById('errorID').innerHTML += "User ID cannot be blank <br/>";
		//let content = document.createTextNode("User ID cannot be blank");
		//document.getElementById('errorID').appendChild(content);
	}
	
	if(!emailRegex.test(userID)){
		errorCounter++;
		document.getElementById('errorID').innerHTML += "Email entered is invalid <br/>";
	}	
	
	if(fullName == '' || fullName == null){
		errorCounter++;
		document.getElementById('errorName').innerHTML += "Full name cannot be blank <br/>";
	}
	
	if(dob == '' || dob == null){
		errorCounter++;
		document.getElementById('errordob').innerHTML += "DOB cannot be blank <br/>";
	}
	
	if(dob != '' || dob != null){
		if(getAge(dob) < 16){
			errorCounter++;
			document.getElementById('errordob').innerHTML += "Must be 16 or over to have a SNI number<br/>";
		}
	}
	
	if(password == '' || password == null){
		errorCounter++;
		document.getElementById('errorPassword').innerHTML += "Password cannot be blank <br/>";
	}
	
	if(address == '' || address == null){
		errorCounter++;
		document.getElementById('erroradd').innerHTML += "Address cannot be blank <br/>";
	}
	if(sni == '' || sni == null){
		errorCounter++;
		document.getElementById('errorSNI').innerHTML += "SNI number cannot be blank <br/>";
	}
	
	return errorCounter === 0;
}

//Creates a new user
//If validation passes
//Alerts the user in the ajax call if SNI number or User id is in use
//and if the sni number does not exists
function createNewUser(){
	
	if(!validateForm()){
		alert("The sign up form has errors.\nPlease fix these errors before submitting again.");
		
	}
	else{
		var userID = document.getElementById('newUserID').value;
		var fullName = document.getElementById('newFullName').value;
		var dob = document.getElementById('newDOB').value;
		var password = document.getElementById('newPassword').value;
		var address = document.getElementById('newAddress').value;
		var sni = document.getElementById('newSni').value;
	
		console.log(userID);
	
		const Http =new XMLHttpRequest();
		const url = '/signIn/newUser?userID='+userID+'&fullname='+fullName+'&dob='+dob+'&address='+address+'&password='+password+'&sni='+sni;
		Http.open("POST", url);
		Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
		Http.setRequestHeader("Access-Control-Allow-Origin", "*");
		Http.send();
		Http.onload = function(){
			if(this.readyState === 4 && this.status === 200){
				window.location.href = "/signIn";
			
			}
			else if(this.readyState === 4 && this.status === 409){
				alert(this.responseText);
				document.getElementById('errorID').innerHTML += this.responseText+"</br>";
			
			}
			else if(this.readyState === 4 && this.status == 404){
				alert(this.responseText);
				document.getElementById('errorSNI').innerHTML += this.responseText+"</br>";
			}
		}
		
	}
	
	
}






