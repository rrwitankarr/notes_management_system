# Notes Management System

A web-based DBMS project developed using Java Servlets and Oracle Database for managing notes efficiently through CRUD operations.

## Features
- Create and save notes
- Retrieve and display all saved notes
- Edit and update existing notes
- Delete notes
- Automatically stores note ID and creation date
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
notesapp/
│
├── WEB-INF/
│
├── index.html
│
├── DBConnection.java
├── SaveNoteServlet.java
├── GetNotesServlet.java
├── EditNoteServlet.java
├── UpdateNoteServlet.java
└── DeleteNoteServlet.java
