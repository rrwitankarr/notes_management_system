# Notes Management System

A simple web-based DBMS project developed using Java Servlets and Oracle Database for storing and retrieving notes.

## Features
- Add and save notes
- Automatically stores note ID and creation date
- Retrieve and display all saved notes
- Simple and user-friendly interface

## Technologies Used
- Java
- Java Servlets
- HTML
- Oracle Database (SQL*Plus)
- Apache Tomcat Server

## Database
The project uses an Oracle database table named `notes` containing:
- `id` (Primary Key)
- `content`
- `created_at`

## Project Structure

```text
src/
└── com/
    └── notes/
        └── app/
            ├── DBConnection.java
            ├── SaveNoteServlet.java
            └── GetNotesServlet.java
