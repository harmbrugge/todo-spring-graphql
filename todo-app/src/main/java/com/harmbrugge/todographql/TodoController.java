package com.harmbrugge.todographql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @QueryMapping
    public Iterable<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @MutationMapping
    public Todo createTodo(@Argument String name, @Argument String info) {
        Todo todo = new Todo();
        todo.setName(name);
        todo.setInfo(info);
        return todoRepository.save(todo);
    }

    @MutationMapping
    public Todo updateTodo(@Argument Integer id, @Argument String info, @Argument String name, @Argument boolean done) {
        Todo todo = todoRepository.findById(id).get();
        todo.setName(name);
        todo.setInfo(info);
        todo.setId(id);
        todo.setDone(done);
        todo.setUpdatedAt(LocalDateTime.now());
        return todoRepository.save(todo);
    }

    @MutationMapping
    public Todo deleteTodo(@Argument Integer id) {
        Todo todo = todoRepository.findById(id).get();
        todoRepository.deleteById(id);
        return todo;
    }

}
