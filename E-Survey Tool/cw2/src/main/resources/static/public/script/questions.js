//Global variables for get request to store data
var allQuestions;
var answeredQuestions;
var userRole;
var userID;
var currentPage = "Questions";
document.addEventListener("DOMContentLoaded", function(event){
	//Get the current userID and role
	//Get all the questions
	if(currentPage == "Questions"){
		getUserID();
		setTimeout(function(){
			getAllQuestions();
			getUserRole();
		},1000);
		
		//If there are no questions then diaplay a message depending on the user role
		setTimeout(function(){
			if(allQuestions.length == 0){
				if(userRole == "RESIDENT"){
					const txt = document.createElement("h1");
					txt.className = "emptyText";
					txt.innerHTML = "Council have not yet added any questions";
					document.body.appendChild(txt);
				}
				else{
					const txt = document.createElement("h1");
					txt.innerHTML = "Create a question for the residents to answer";
					txt.className = "emptyText";
					document.body.appendChild(txt);
				}
			}
		
		
			//Display each question to the user
			for(let i = 0; i < allQuestions.length; i++){
				displayQuestion(allQuestions[i]);
			}
		
		},2000);
		
	}
	
	

	
	
});



//Opens the form to add and edit the question
function openAddQuestionForm(){
    let node = document.getElementById("addQuestionPopup");

    node.innerHTML = `
    <div  class="popup none" id="popup-window">
        <div class="popup-close" onclick="closeCreateQuestionForm()">&times;</div>
        <p>Add the question</p>
        <input type="text" name="" placeholder="Enter the question" id="newQuestion">
		<span class="error" id="errorQuestion"></span>
        <p>Add a option</p>
        <input type="text" placeholder="Enter a option" id="newOption">
		<span class="error" id="errorOption"></span>
        <button id="addOption" class="formBtn" onclick="addNewOption()">Add Option</button>
        <select id="list" multiple></select>
        <button id="removeOption" class="formBtn" onclick="removeNewOption()">Remove Selected Option</button>
        <input type="submit" onclick="createNewQuestion()"  value="Create Question" id="addQuestionBtn">
    </div>
    `
    var overlay = document.getElementById('overlay-window');
    overlay.style.display = 'block';
    document.getElementById('popup-window').classList.add('block');
    document.getElementById('popup-window').classList.remove('none');

}

//Allows the admin to add a option to a new or exisiting question
function addNewOption(){
    
    let optionsToBeAdded = document.getElementById("list");
    let optionText = document.getElementById("newOption");
	let myOptions = document.getElementById("list").options;

    if(optionText.value == ''){
        alert('Please enter a option');
        return;
    }
	
	var match = false;
	
	if(myOptions.length > 0){
		
		for(let i = 0; i < myOptions.length; i++){
			let currentOption = myOptions[i].value;
			if(currentOption === optionText.value){
				alert("Option already exists");
				document.getElementById('errorOption').innerHTML += "Option already exists<br/>";
				match = true;
				break;
			}
		}
	}
	
	if(!match){
		document.getElementById('errorOption').innerHTML = "";
		const option = new Option(optionText.value, optionText.value);
    	optionsToBeAdded.add(option, undefined);
    	optionText.value = '';
    	optionText.focus();	
	}
	
	
   
}

//Removes a option from the list
function removeNewOption(){
    let optionsToBeAdded = document.getElementById("list");
    let selected = [];

    for(let i = 0; i < optionsToBeAdded.options.length; i++){
        selected[i] = optionsToBeAdded.options[i].selected;
    }

    let index = optionsToBeAdded.options.length;
    while(index--){
        if(selected[index]){
            optionsToBeAdded.remove(index);
        }
    }

}

//X button closes the form
function closeCreateQuestionForm(){
   
    document.getElementById("popup-window").classList.remove("block");
    document.getElementById("popup-window").classList.add("none");

    var overlay = document.getElementById('overlay-window');
    overlay.style.display = 'none';
    
}

//Validates if the new question has no errors
function validateNewQuestion(){
	let questionText = document.getElementById("newQuestion").value;
    let myOptions = document.getElementById("list").options;
	let popup = document.getElementById("popup-window");
	var errors = [...popup.getElementsByClassName('error')];
	let errorCounter = 0;
	
	for(let i = 0; i < errors.length; i++){
		errors[i].innerHTML = '';
	}
	
	if(questionText == '' || questionText == null){
		errorCounter++;
		document.getElementById('errorQuestion').innerHTML += "Question cannot be blank<br/>";
	}
	
	if(myOptions.length == 0 || myOptions.length < 2){
		errorCounter++;
		document.getElementById('errorOption').innerHTML += "The question must have at least 2 options<br/>";
	}
	
	return errorCounter === 0;
	
	
	
	
	
}

//Calls the validation, if it passes then
//Save the new question to the database with a ajax call
function createNewQuestion(){
	if(!validateNewQuestion()){
		alert("Question form has errors\nPlease fix these errors before submitting again.");
	}
	else{
		let questionText = document.getElementById("newQuestion").value;
    let myOptions = document.getElementById("list").options;

	let para = "";

	for(let i = 0; i < myOptions.length; i++){
		let currentOption = myOptions[i].value;
		para = para + currentOption;
		if(i != myOptions.length-1){
			para = para + ",";
		}
		
	}


	closeCreateQuestionForm();
	
	const Http = new XMLHttpRequest();
	const url = '/dashboard/saveQuestion?questionText='+questionText+'&options='+para;
	
	Http.open("Post",url);
	Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http.send();
	Http.onload = function(){
		if(this.readyState === 4 && this.status === 200){
			alert("Question Created");
			let newQuestion = eval(Http.responseText);
			
			displayQuestion(newQuestion);
			
			//getAllQuestions();
			
		}
	}
		
	}
    
	
}

//Display the question to the user
//Question display is different depending on the user role
function displayQuestion(questionData){
	if($(".emptyText").length){
		$(".emptyText").remove();
	}
	var ans = false;
	if(userRole === "COUNCIL"){
		if(questionData[0].length == 3){
			ans = true;
		}
	}
	
	let questionID = questionData[0][0];
	let questionText = questionData[0][1];
	let qid = "0"+questionID;
	
	
	
	
	const node = document.createElement("li");
	node.setAttribute('data-key',qid);
	node.className = "questionItem";
	
	const form = document.createElement("form");
	form.className = "questionOptions";
	form.id = qid;
	form.innerHTML = `
		<p class="optionP">Please select an option:</p>
	`;
	
	
	const questionContainer = document.createElement("div");
	questionContainer.className = "questionContainer";
	questionContainer.innerHTML = `
		<span class="questionText">Question: ${questionText}</span>
	`;
	questionContainer.append(form);
	const div = document.createElement("div");
	div.className = "right";
	

	
	if(userRole == "COUNCIL"){
		const responseBtn = document.createElement("button");
		responseBtn.className = "questionBtn";
		responseBtn.innerHTML = "Responses";
		responseBtn.id = qid;
		responseBtn.onclick = function(){viewResponses(questionID)};
		div.appendChild(responseBtn);
		
		const deleteBtn = document.createElement("button");
		deleteBtn.className = "questionBtn";
		deleteBtn.innerHTML = "Delete";
		deleteBtn.id = qid;
		deleteBtn.onclick = function(){deleteQuestion(questionID)};
		if(ans == false){
			const editBtn = document.createElement("button");
			editBtn.className = "questionBtn";
			editBtn.innerHTML = "Edit";
			editBtn.id = qid;
			editBtn.onclick = function(){editQuestion(questionData)};
			div.appendChild(editBtn);	
		}
		

		
		div.appendChild(deleteBtn);
		
		
	}
	
	else{
		const submitBtn = document.createElement("button");
		submitBtn.className = "questionBtn";
		submitBtn.innerHTML = "Submit";
		submitBtn.id = "submit"+qid;
		submitBtn.onclick = function(){submitAnswer(qid)};
	
		div.appendChild(submitBtn);
	}
	
	questionContainer.appendChild(div);
	node.appendChild(questionContainer);
	
	
	var list = document.getElementById("questionList");
	
	
	
	
	for(let i = 1; i < questionData.length; i++){
		let option = questionData[i];
		
		const labelNode = document.createElement("label");
		labelNode.innerHTML = option[1];
		labelNode.for = option[0];
		labelNode.className="labelOptionCon";
		
		const inputNode = document.createElement("input");
		inputNode.type = "radio";
		inputNode.id = option[0];
		inputNode.name = "op"+qid;
		inputNode.value = option[1];
		if(userRole == "COUNCIL"){
			inputNode.disabled = true;
		}
		
		const spanNode = document.createElement("span");
		spanNode.className = "checkmark";
		
		const breakOp = document.createElement("br");
		
		labelNode.appendChild(inputNode);
		labelNode.appendChild(spanNode);
		form.append(labelNode);
		
	}
	
	const item = document.querySelector(`[data-key='${qid}']`);
	
	if(item){
		list.replaceChild(node, item);
		
	}
	else{
		list.append(node);
		
	}
	
	
}

//Gets the current user role
function getUserRole(){
	
	const Http = new XMLHttpRequest();
	const url = '/dashboard/getRole';
	Http.open("Get", url);
	Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http.send();
	Http.onload = function(){
		if(this.readyState === 4){
			userRole = Http.responseText;
			if(Http.responseText == "COUNCIL"){
				const btnCon = document.createElement("div");
				btnCon.className = "button-container";
				
				const btn = document.createElement("button");
				btn.className = "create-question";
				btn.onclick = function(){openAddQuestionForm()};
				btn.innerHTML = "Create Question";
				
				btnCon.appendChild(btn);
				let referenceNode = document.getElementById("questionList");
				referenceNode.before(btnCon)
				
				//document.body.appendChild(btnCon);
				
				const addPopup = document.createElement("div");
				addPopup.id = "addQuestionPopup";
				
				document.body.appendChild(addPopup);
				//$('.button-container').hide();
				//$('#addQuestionPopup').hide();
			}
			
			
		}
	}
}

//Gets all the questions
function getAllQuestions(){
	const Http = new XMLHttpRequest();
	const url = '/dashboard/getAllQuestions?userID='+userID;
	
	Http.open("Get", url);
	Http.send();
	Http.onreadystatechange = (e) => {
		allQuestions = eval(Http.responseText);	
	}
	
}

//Deletes a question from the database using the id
function deleteQuestion(id){
	const Http = new XMLHttpRequest();
	const url = '/dashboard/deleteQuestion/'+id;
	Http.open("Delete", url);
	Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http.send();
	Http.onload = function(){
		if(this.readyState === 4 && this.status === 200){
			let qid = "0"+id;
			const item = document.querySelector(`[data-key='${qid}']`);
			item.remove();
			alert(this.responseText);
			
		}
		if(this.readyState === 4 && this.status === 404){
			alert(this.responseText);
		}
	}
	
}

//edits a questions
function editQuestion(questionData){
	openAddQuestionForm();
	let id = questionData[0][0];
	document.getElementById("addQuestionBtn").value = "Edit Question";

	
	let text = questionData[0][1];
	
	document.getElementById("newQuestion").value = text;
    let questionOptions = document.getElementById("list");
	for(let i = 1; i < questionData.length; i++){
		let op = questionData[i];
		let exOption = document.createElement("option");
		exOption.text = op[1];
		questionOptions.add(exOption);
	}
	
	document.getElementById("addQuestionBtn").onclick = function(){updateQuestion(id)};
		
}

//updates the question in the database and dom
function updateQuestion(id){
	let questionText = document.getElementById("newQuestion").value;
	 let myOptions = document.getElementById("list").options;

	let para = "";

	for(let i = 0; i < myOptions.length; i++){
		let currentOption = myOptions[i].value;
		para = para + currentOption;
		if(i != myOptions.length-1){
			para = para + ",";
		}
		
	}
	
	const Http = new XMLHttpRequest();
	const url = '/dashboard/updateQuestion?questionID='+id+"&questionText="+questionText+"&options="+para;
	Http.open("Get", url);
	Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http.send();
	Http.onload = function(){
		if(this.readyState === 4 && this.status === 200){
			closeCreateQuestionForm();
			let updatedQuestion = eval(this.responseText);
			displayQuestion(updatedQuestion);
			
			
		}
	}
	
}

//gets the current user ID
function getUserID(){
	const Http = new XMLHttpRequest();
	const url = '/dashboard/getUserID';
	Http.open("Get", url);
	Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http.send();
	Http.onload = function(){
		if(this.readyState === 4 && this.status === 200){
			userID = Http.responseText;
		
		}
	}
	
}

//submit the answer to a question to the database
function submitAnswer(questionID){
	var options = document.getElementsByName("op"+questionID);
	var selectedOption;
	var optionHasBeenSelected = false;
	for(let i = 0; i < options.length; i++){
		if(options[i].checked){
			selectedOption = options[i];
			optionHasBeenSelected = true;
			break;
		}
	}
	
	if(!optionHasBeenSelected){
		alert("Please select an option");
	}
	else{
		let selectedOptionID = selectedOption.id;
		
	
		setTimeout(function(){
			const Http = new XMLHttpRequest();
			const url = '/dashboard/saveResponse?questionID='+questionID+"&optionID="+selectedOptionID+"&userID="+userID;
			Http.open("Post", url);
			Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
			Http.setRequestHeader("Access-Control-Allow-Origin", "*");
			Http.send();
			Http.onload = function(){
				if(this.readyState === 4){
					alert("repsonse has been saved");
					deactivateQuestion(questionID,selectedOption);	
				}
			}
		
		},1000);
	
	}
	
}

//Once a response has been submitted
//the question is deactived so it cannot be resubmitted
function deactivateQuestion(questionID,selectedOption){
	var q = document.getElementById(questionID);
	var options = document.getElementsByName("op"+questionID);
	for(let i = 0; i < options.length; i++){
		var currentOption = document.getElementById(options[i].id);
		currentOption.setAttribute("disabled",true);
	}
	
	var submitBtn = document.getElementById("submit"+questionID);
	submitBtn.setAttribute("disabled",true);
}

//For the admin to view a response of a selected question
//Hides the questions and creates new elements for the bar chart
var questionResponseID;
function viewResponses(id){
	
	questionResponseID = id;
	
	$(".create-question").hide();
	$("#questionList").hide();
	
	const totalRes = document.createElement("h2");
	totalRes.id = "total";
	document.body.appendChild(totalRes);
	
	const chartDiv = document.createElement("div");
	chartDiv.id = "chartCon";

	const chartCanvas = document.createElement("canvas");
	
	chartCanvas.id = "responseChart";
	
	chartDiv.appendChild(chartCanvas);
	document.body.appendChild(chartDiv);
	
	const btnCon = document.createElement("div");
	btnCon.className = "button-container";
				
	const btn = document.createElement("button");
	btn.className = "create-question";
	btn.onclick = function(){goBack()};
	btn.innerHTML = "Go Back";
				
	btnCon.appendChild(btn);
	let referenceNode = document.getElementById("chartCon");
	referenceNode.before(btnCon);
	
	getResponseData();
	
}

//Gets all the response data.
var totalandquestion;
var optionText;
var optionResponses;
function getResponseData(){
	
	
	const Http = new XMLHttpRequest();
	const url = '/getTotalResponses?questionID='+questionResponseID;
	Http.open("Get", url);
	Http.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http.send();
	Http.onload = function(){
		if(this.readyState === 4 && this.status === 200){
			totalandquestion = eval(this.responseText);
			document.getElementById("total").innerHTML = "Total number of responses: "+totalandquestion[0];
			
				
		}
		
	}
	
	const Http2 = new XMLHttpRequest();
	const url2 = '/getOptionTextResponses?questionID='+questionResponseID;
	Http2.open("Get", url2);
	Http2.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http2.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http2.send();
	Http2.onload = function(){
		if(this.readyState === 4 && this.status === 200){
			optionText = eval(this.responseText);
			
		}
	}
	
	const Http3 = new XMLHttpRequest();
	const url3 = '/getOptionResponses?questionID='+questionResponseID;
	Http3.open("Get", url3);
	Http3.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	Http3.setRequestHeader("Access-Control-Allow-Origin", "*");
	Http3.send();
	Http3.onload = function(){
		if(this.readyState === 4 && this.status === 200){
			optionResponses = eval(this.responseText);
			
				
		}
		
	}
	
	setTimeout(function(){
		createChart();
	},2000);
	
	
}

//Creates the chart using chart.js
function createChart(){
	
	const labels = optionText;
	const data = {
		labels: labels,
		datasets: [{
			label: "Responses",
			backgroundColor: 'rgb(255, 99, 132)',
      		borderColor: 'rgb(255, 99, 132)',
			data: optionResponses,
		}]
	};
		
	const config = {
		type: 'bar',
		data: data,
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: totalandquestion[1]
				}
			},
			
		}
	};
	
	const myChart = new Chart(
		document.getElementById('responseChart'),
		config
	);
	
	
}

//go back to questions	
function goBack(){
	window.location.reload();
}


