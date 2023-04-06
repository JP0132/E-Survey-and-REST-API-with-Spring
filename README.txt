# CW 2: e-Survey Tool

## Requirements
- Spring Tool Suite
- MySQL Workbench
- A desktop browser (Tested mostly on Chrome)

## Setup
In MySQL Workbench:
- Create a schema with name 'co3102db'
- Create a user with name 'co3102' with password: 'password'
- Give the user all privileges for 'co3102db'
- These are the default settings in the application.properties file of the project these can be changed to your user and an empty schema.

## How to run it?
- Download the zip file called cw2Task1.
- Open Spring Tool Suite
- If a you do not have work space created already, create one and open it
- Click file->import->General->existing project into workspace
- Select archive file option
- Select the location where the zip file is located
- Click finish
- Refresh the Gradle project, right-click on the project and select Gradle->refresh
- Wait for all the dependencies to download
- Right click on the project and run as Spring Boot Application
- Let all the tables be created and the data be inserted
- Open the browser and enter 'https://localhost:8443/'
- May need to accept risks
- The homepage is set as the sign up page, if that is shown then the project has been ran correctly
## Usage
- The default admin logins have been created in Cw2Application
- Use them to login into the application admin dashboard
- Use the create question button, to create a new question
- Use the logout button to logout back to the sign up page
- Create a new account using the create account link
- Fill in the form to create a new user and sign in with its credentials
- User can view the question created and answer it
- On the admin dashboard click responses to view a bar chart of the responses of an question

# CW2: REST Api
## Requirements
- Spring Tool Suite\
- MySQL Workbench\
- A desktop browser or Postman (tested mostly using Postman)

## Setup
- Same set up as the e-Survey tool
- Ensure it is the same database setup as the e-survey tool

## How to run it?
- Download the zip file called cw2Task2.
- Open Spring Tool Suite
- If a you do not have work space created already, create one and open it
- Click file->import->gradle->existing gradle project
- Select the location where the zip file is located
- Click finish
- Refresh the Gradle project, right-click on the project and select Gradle->refresh
- Wait for all the dependencies to download
- Right click on the project and run as Spring Boot Application
- Let it set up so it can access the database
- Open the browser or Postman and enter 'http://localhost:8080/'+ the rest api request
- Example
  - http://localhost:8080/GetAllQuestions
  - http://localhost:8080/GetQuestionOptions/2/
  - http://localhost:8080/GetQuestionResponse/2/

