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

## Add a todo item
Given the above scheme, a todo item can be added using the following syntax:
```graphql
mutation createTodo {
  createTodo(
      name: "Do the dishes",
      info: "Use the right detergent"
  ) {
    name
    info
    id
  }
}
```
Spring will provide an interface to test your queries at:
http://localhost:8080/graphiql?path=/graphql

Or you can use curl to post a request:
```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "query": "mutation { createTodo(name: \"Do the dishes\", info: \"Use the right detergent\") { name info id } }"
}' localhost:8080/graphql
```

A successful response will yield the todo item with the database identifier
```graphql
{
  "data": {
    "createTodo": {
      "name": "Do the dishes",
      "info": "Use the right detergent",
      "id": "1"
    }
  }
}
```


## Update a todo item
A todo item can be updated using the database identifier
```graphql
mutation updateTodo {
  updateTodo(
    id: 1,
    name: "Do the dishes"
    info: "Use the best detergent"
    done: true,
  ) {
    name
    info
    id
  }
}
```
Using curl:
```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "query": "mutation { updateTodo(id: 1, name: \"Do the dishes!\", info: \"Use the best detergent\", done: true) { name info id } }"
}' localhost:8080/graphql
```

## Delete a todo item
A todo item can be deleted using the corresponding database identifier
```graphql
mutation deleteTodo {
  deleteTodo(
    id: 1
  ) {
    name
  }
}
```
Using curl:
```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "query": "mutation { deleteTodo(id: 354) { name info id } }"
}' localhost:8080/graphql
```
## List all todo items
All todo items can be listed with the following query:
```graphql
query getTodos {
  getAllTodos {
    name
    info
    id
    done
    createdAt
    updatedAt
  }
}
```
Using curl:
```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "query": "query { getAllTodos { name info id done createdAt updatedAt} }"
}' localhost:8080/graphql
```


