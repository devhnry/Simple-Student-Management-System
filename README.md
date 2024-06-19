# Student Management API

## Overview

Welcome to the **Student Management System** repository. My first API Repo. 
This backend service provides basic CRUD operations for managing students as well as Basic Authentication. 
It exposes specific endpoints under `/api/students` to create, delete, update, and fetch students.

## Endpoints

- **GET /api/students/**: Retrieve all Students.
- **GET /api/students/{email}**: Retrieve student information based on email.
- **POST /api/students/**: Add a new Student to the DB
- **PUT /api/students/{studentID}**: Update an existing student based on email.
- **DELETE /api/students/{studentID}**: Delete a student by ID.
- **POST /register/user**: Allows for a user to sign up to the Application.

## Usage

1. **Retrieve all students**:
    - **Request**: `GET /api/students`
    - **Response**: Returns a list of all students in the database.

2. **Retrieve a student by email**:
    - **Request**: `GET /api/students/{email}`
    - **Response**: Returns a student in the database based on the email provided.

3. **Create a new student**:
    - **Request**: `POST /api/students`
    - **Body**: JSON object containing student details.
    - **Response**: Returns the newly created student with its assigned ID.

4. **Update an existing student**:
    - **Request**: `PUT /api/students/{studentID}`
    - **Body**: JSON object containing updated students details.
    - **Response**: Prints out the updated student details.

5. **Delete a student by ID**:
    - **Request**: `DELETE /api/students/{studentID}`
    - **Response**: Deletes the student with the specified ID from the database.

6. **Register a User**:
    - **Request**: `POST /register/user`
    - **Response**: Registers a User on the application.


## Contribution

Contributions are welcome! If you have any suggestions, improvements, or bug fixes, please feel free to open an issue or submit a pull request.