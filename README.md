# Todo Spring GraphQL

This is a sample application for the creation of todo items.

## Features

- Create, read, update, and delete todos using GraphQL APIs.
- Spring Boot backend.
- MySQL database for data persistence.
- The Project is wrapped in a docker container.

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
  
Optional (if you want to build the project yourself):
- Gradle
- Java 17+

## Running the Application with Docker Compose

First, clone the project:
```bash
git clone https://github.com/harmbrugge/todo-spring-graphql.git
```

Run docker compose inside the project directory:
```bash
docker compose up
```
The application will be attached to port 8080 by default, this can be configured using the .env file 

## GraphQL

A todo item is defined using the following GraphQL scheme:
```graphql
type Todo {
    id: ID
    name: String
    info: String
    done: Boolean
    createdAt: String
    updatedAt: String
}

type Query {
    getAllTodos: [Todo]
}

type Mutation {
    createTodo(name: String!, info: String): Todo!
    updateTodo(id: ID!, name: String, info: String, done: Boolean): Todo!
    deleteTodo(id: ID!): Todo!
}
```

# Add a todo item
```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "query": "mutation { createTodo(name: \"New Todo\", info: \"Description of the new todo\") { id name info done createdAt updatedAt } }"
}' localhost:8080
```
