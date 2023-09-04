package com.harmbrugge.todographql;

import com.harmbrugge.todographql.exception.TodoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<Todo> todoDatabase = todoRepository.findById(id);
        if (todoDatabase.isPresent()) {
            Todo todo = todoDatabase.get();
            todo.setName(name);
            todo.setInfo(info);
            todo.setId(id);
            todo.setDone(done);
            todo.setUpdatedAt(LocalDateTime.now());
            return todoRepository.save(todo);
        } else {
            throw new TodoNotFoundException(String.format("No todo item with id %d", id));
        }
    }

    @MutationMapping
    public Todo deleteTodo(@Argument Integer id) {
        Optional<Todo> todoDatabase = todoRepository.findById(id);
        if (todoDatabase.isPresent()) {
            todoRepository.deleteById(id);
            return todoDatabase.get();
        }
        else {
            throw new TodoNotFoundException(String.format("No todo item with id %d", id));
        }
    }

}
