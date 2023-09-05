package com.harmbrugge.todographql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureGraphQlTester
public class TodoControllerTests {

    @Autowired
    private GraphQlTester tester;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void createTodo() {
        String query = """
                mutation createTodo {
                  createTodo(
                      name: "Afwas doen",
                      info: "Wel de goede zeep gebruiken"
                  ) {
                    id
                    name
                    info
                  }
                }""";
        Todo todo = tester.document(query)
                .execute()
                .path("data.createTodo")
                .entity(Todo.class)
                .get();
        Assertions.assertNotNull(todo);
        Assertions.assertNotNull(todo.getId());
        Assertions.assertNotNull(todo.getName());

        todoRepository.save(todo);

        Optional<Todo> todoDb = todoRepository.findById(todo.getId());
        Assertions.assertTrue(todoDb.isPresent());
        Assertions.assertEquals(todo.getId(), todoDb.get().getId());
        Assertions.assertEquals(todo.getName(), todoDb.get().getName());
    }

}
