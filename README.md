# Spring Full Stack

## Overview

This is a full-stack web application in Java Spring framework for backend and React.js
for frontend that I have developed.
It provides for JWT user authentication and authorization, and fully CRUD operations
by using most common Spring
framework libraries **Spring Security, Spring Web, Spring Data JPA,
Spring JDBC** for backend and **React**, **ReactRouter**
and **Chakra** technologies for frontend, and also **Postgres** as a database. \
All the `Unit tests` and `Integration tests` are implemented and the application
is fully **CI/CD** compatible. \
The backend is fully automated deployable
in **AWS Elastic BeansTalk** and frontend is fully automated deployable in **AWS Amplify**.
Also using **AWS S3** to store profile pictures.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Backend (Spring Framework)](#backend-spring-framework)
  - [Frontend (React)](#frontend-react)
- [Configuration](#configuration)
- [Usage](#usage)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- JDK 17 (both Java 8 and 11 also supported), Node.js v18 or above
- Docker installed in your machine and postgres v15.3 or above running in a container.

## Getting Started

To get this project up and running, follow the steps below for both the backend (Spring Framework) and frontend (React) components.

### Backend (Spring Framework)

#### To get running backend:

1. Clone the repository:

   ```bash
   git clone git@github.com:msbeigiai/fullstack-employee-management.git
   ```

2. Navigate to the project directory:

   ```bash
    cd spring-full-stack
   ```

3. Navigate to the backend folder and run:

   ```bash
   mvn clean run
   ```

preceeding command will run the backend application

---

### Frontend (React)

#### To get running frontend:

1. Navigate back to frontend, then navigate to react folder and run:

```bach
cd ../frontend/react
docker build . -t msbeigiai/msbeigi-react
docker push msbeigiai/msbeigi-react
```

this will also push latest version of frontend into Docker hub. 2. To boot up the entire project navigate back to the project root and then run docker compose command:

```bash
cd spring-boot-example/
docker compose up -d
```

### Configuration

1. In case you see error booting up backend just navigate inside `postgres` database by running:

```bash
docker exec -it postgres bash
```

and then run:

```bash
psql -U msbeigi
```

while you are prompted for password, supply `password`.\
inside postgres database create `customer` database by writing:

```sql
CREATE DATABASE customer;
```

by now your application must be up and running.

### Usage

By configuring the project as above guidelines, project can run perfectly.

### If you have any question feel free to ask me.

```

```
