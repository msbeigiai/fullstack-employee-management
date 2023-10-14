# Spring Full Stack

## Overview

This GitHub repository hosts a comprehensive **full-stack web application** crafted with the **Java Spring framework**
for the backend and **React.js** for the frontend. It's a robust system featuring **JWT-based user authentication and
authorization**, along with complete **CRUD (Create, Read, Update, Delete) operations**, powered by essential Spring
framework libraries like **Spring Security**, **Spring Web**, **Spring Data JPA**, and **Spring JDBC** on the server
side. For the user interface, we've harnessed the capabilities of **React**, **ReactRouter**, and the sleek **Chakra**
framework. As the underlying database, we rely on **Postgres**.

This repository not only showcases the core functionality of the application but also provides a rich user experience.
Users can **register**, **reset their passwords**, and **change their passwords** effortlessly, making it a compelling
and fully-featured project for both beginners and experienced developers. Explore, experiment, and contribute to this
open-source gem!

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
    - [Backend (Spring Framework)](#backend-spring-framework)
    - [Frontend (React)](#frontend-react)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- JDK 17 (both Java 8 and 11 also supported), Node.js v18 or above
- Docker installed in your machine and postgres v15.3 or above running in a container.

## Getting Started

To get this project up and running, follow the steps below for both the backend (Spring Framework) and frontend (React)
components.

### Backend (Spring Framework)

#### To get running backend:

1. Clone the repository:

   ```bash
   git clone git@github.com:msbeigiai/fullstack-employee-management.git
   ```

2. Navigate to the project directory:

   ```bash
    cd fullstack-employee-management
   ```

**Note: Before running the application make sure your Postgres database is up and running.** \
**You also need to run `create database employee_db` query in your Postgres to create database.**

3. Navigate to the backend folder and run:

   ```bash
   cd fullstack-employee-backend
   mvn clean run
   ```

preceeding command will run the backend application

---

### Frontend (React)

#### To get running frontend:

1. Navigate back to frontend, then navigate to react folder and run:

    ```bach
    cd ../fullstack-employee-frontend
    npm run dev
    ```




