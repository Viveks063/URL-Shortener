URL Shortener - A Full-Stack Java Application
This is a complete URL shortener web application built with Java, Spring Boot, and a modern tech stack. It provides a simple REST API to create short, unique URLs that redirect to the original long URLs, similar to services like Bitly or TinyURL. The project includes a clean web interface for easy use.

This project was created on Saturday, August 2, 2025.

Features
Create Short URLs: A POST endpoint that accepts a long URL and returns a unique, 6-character short URL.

Redirect Service: A GET endpoint that looks up a short code and performs an HTTP 302 redirect to the original long URL.

High-Performance Caching: Uses Redis to cache frequently accessed URLs, reducing database load and providing lightning-fast redirects.

Persistent Storage: Uses MySQL as the primary database to store all URL mappings.

Simple Web UI: A clean, single-page user interface built with HTML, CSS, and JavaScript for creating short URLs.

Containerized Services: Both MySQL and Redis are managed using Docker, making the setup clean, isolated, and easy to run.

Tech Stack
Backend: Java 17, Spring Boot 3

Database: MySQL

Cache: Redis

Build Tool: Gradle

Containerization: Docker

Frontend: Plain HTML, CSS, and JavaScript

Prerequisites
Before you begin, ensure you have the following installed on your system:

Java JDK 17: The application is built to run on Java 17.

Docker Desktop: Required to run the MySQL and Redis containers.

An API Client (Optional but Recommended): A tool like Postman is useful for testing the API endpoints directly.

How to Run the Project
Follow these steps to get the application running on your local machine.

1. Start the Database and Cache
The project uses Docker to manage the database (MySQL) and the cache (Redis).

Open a terminal or command prompt in the root directory of the project (the same folder that contains docker-compose.yml).

Run the following command to start the containers in the background:

docker compose up -d

This will download the necessary images and start the MySQL and Redis services.

2. Configure the Database Password
Open the src/main/resources/application.properties file.

Find the line spring.datasource.password=your_strong_password.

Make sure this password matches the MYSQL_ROOT_PASSWORD you have set in your docker-compose.yml file.

3. Run the Spring Boot Application
Make sure you are still in the root directory of the project in your terminal.

Run the application using the Gradle wrapper:

# On Windows
.\gradlew.bat bootRun

# On macOS/Linux
./gradlew bootRun

Gradle will build the project and start the server. Wait until you see a message like Tomcat started on port(s): 8080.

4. Access the Application
Web UI: Open your web browser and navigate to http://localhost:8080.

You should see the URL Shortener web page. You can now create and use short URLs!

API Endpoints
1. Shorten a URL
Method: POST

URL: http://localhost:8080/shorten

Body: The raw long URL as plain text.

Example using Postman:

Set the method to POST.

Set the URL to http://localhost:8080/shorten.

Go to the "Body" tab, select "raw", and choose "Text".

In the text area, type https://www.google.com.

Click "Send".

Success Response:

Code: 200 OK

Body: http://localhost:8080/{shortCode}

2. Redirect to Long URL
Method: GET

URL: http://localhost:8080/{shortCode}

Example:

Navigate to the short URL you received (e.g., http://localhost:8080/aB1cDe) in your web browser.

Success Response:

Code: 302 Found

The browser will be automatically redirected to the original long URL.

Failure Response (if code doesn't exist):

Code: 404 Not Found

How to View the Database
You can directly access the MySQL database running inside the Docker container to see the data.

Open a terminal and run this command to get a shell inside the container:

docker exec -it mysql-db bash

Once inside, log in to the MySQL client:

mysql -u root -p

Enter your password when prompted.

Run these SQL commands to see your data:

USE url_shortener_db;
SELECT * FROM url_mapping;


