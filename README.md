# E-Survey Tool 

## A web application that allows admins to create questions for a survey to learn about the residents. The residents can sign up for the survey to answer the questions. The admins can then review the responses for each question. Also there is a API available to get the question or responses if needed extrenally.  

* Spring framework used for the backend application.  
* MVC architecture used to create the application.  
* Created model classes for each data to be stored, and set up the relationships accordingly.
* JSPs used for the views. Using HTML, JavaScript and CSS for a clear and simple design for easy reading and no distractions.  
* Controllers for each table of data, with each method having the appropiate HTTP method, like GET for fetching the questions or POST for creating a new question.  
* Spring security used for secure login, authenticating the user before access the survey.  
* CRUD repository used to access the data from the database. 
* Using JavaScript, allowed the user to scan a QR code for their SNI number when signing up.
* Chart.js used to display the responses in a chart for clear analysis.  
* Stored the data within MySQL Workbench, but connecting Spring to the database using JDBC.

## How it works and looks.  

### Demo for the Admin side.

https://github.com/JP0132/E-Survey-and-REST-API-with-Spring/assets/78804278/79b4e860-1a31-4fef-87a6-e2e1e08ff993  

### Demo for the Resident  

https://github.com/JP0132/E-Survey-and-REST-API-with-Spring/assets/78804278/461c7662-4da1-44d0-aed0-7476f07037ec  

## How to run.

### Prerequisites  
* Spring Tool Suite or preferred code editor for Spring.  
* MySQL Workbench.  
* A desktop browser like Chrome, Firefox, Opera etc.  

### MySql Workbench Setup
In MySQL Workbench:
- Create a schema with name 'co3102db'
- Create a user with name 'co3102' with password: 'password'
- Give the user all privileges for 'co3102db'
- These are the settings in the application.properties file of the project these can be changed to your user and an empty schema.

### Running it  
1. Clone the project or download the folder.  
2. Open a workspace in Spring tool suite or your chosen editor.  
3. Now import the project. To import in Spring tool suite.  
   - File -> Import -> Existing Gradle Project.  
4. When importing the project make sure to **import the folder called cw2** inside E-Survey Tool. Do not import E-Survey Tool or it will not recongise it as a gradle project.  
5. Then carry on with the process and click finish when everything is done.   
6. Right click on the project folder, and select Gradle -> refresh.   
7. This will make sure the gradle dependencies are downloaded and nothing is amiss.  
8. Right click on the project and select Run as -> **Spring Boot App**.  
9. This will start the application, and then create the tables and data to be inserted into the database.
10. Now open the browser adn enter https://localhost:8443/  
    - May need to accept risks when accessing first time.  
11. The homepage is set as the login page. If that is shown then the project has been ran correctly.  
12. Logging:  
    - To login in as a admin, the password and username are located in the CW2Application.java file.  
    - Signing up as a resident you need to use the SNI numbers located in CW2Application.java file.  

# CW2: REST Api  

## How to run.  

## Requirements  
- Spring Tool Suite or preferred code editor for Spring.    
- MySQL Workbench.    
- A desktop browser or Postman (tested mostly using Postman).  

## Setup  
- Ensure it is the same database setup as the e-survey tool  

## Running it.  
1. Same set up as the e-Survey tool expect at step 4 select cw2RestAPI folder, then continue to step 9.  
2. Let it set up so it can access the database  
3. Open the browser or Postman and enter 'http://localhost:8080/'+ the rest api request  
   - Example  
      * http://localhost:8080/GetAllQuestions  
      * http://localhost:8080/GetQuestionOptions/2/  
      * http://localhost:8080/GetQuestionResponse/2/  

