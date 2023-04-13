## General info
This project is a simple web application that enables management and playing of quizzes.
It was the final project for course Web Application Development
	
## Technologies

#Backend (Java/Servlets/JSP):

The backend of the quiz management system is built using Java, Servlets, and JSP (JavaServer Pages) technologies.
Apache Derby, a lightweight Java-based relational database, is used to store quiz-related data such as quiz questions, options, and results.
CRUD operations (Create, Read, Update, Delete) are implemented using Servlets and JDBC (Java Database Connectivity) to interact with the Apache Derby database.
Admins can create, update, and delete quizzes, as well as view quiz details such as questions and options, through the backend.
Websockets are used to enable real-time communication between the frontend and backend during quiz games, allowing for dynamic updates of quiz status, score, and time.

#Frontend (Vanilla JS/HTML/CSS):

The frontend of the quiz management system is developed using vanilla JavaScript, HTML, and CSS.
The user interface allows admins to manage quizzes, including creating new quizzes, editing existing quizzes, and deleting quizzes.
Guests can view the available quizzes and start playing quizzes that are started by admins.
The frontend communicates with the backend using REST APIs and websockets to perform operations such as fetching quiz data, submitting quiz answers, and receiving real-time updates during quiz games.
HTML and CSS are used for rendering the user interface, while JavaScript handles user interactions, data retrieval, and updates.

#Websockets:

Websockets are utilized for real-time communication between the frontend and backend during quiz games.
When a guest starts a quiz, a websocket connection is established between the frontend and backend, allowing for real-time updates on the quiz status, score, and time.
The backend sends updates to the frontend in real-time as quiz questions are displayed and answers are submitted.
The frontend displays the quiz questions, options, and timer, and sends answers to the backend via websockets for scoring.
Websockets enable seamless communication and dynamic updates between the frontend and backend, providing an interactive and real-time quiz experience for guests.

#Apache Tomcat:

Apache Tomcat, a popular Java web server and servlet container, is used to deploy and serve the quiz management system application.
The backend Java servlets and JSP files are deployed on the Tomcat server, which handles incoming HTTP requests and invokes the appropriate servlets to process the requests.
Tomcat also manages the websocket connections between the frontend and backend, allowing for real-time communication during quiz games.

## Video Demonstration

https://user-images.githubusercontent.com/106106584/219440650-4a9bc454-a7e8-4be0-b0c4-bd514d378577.mp4

