package todo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;


@RestController
class ToDoController {
    private final ToDoRepository repository;

    ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    // all ToDos 
    @GetMapping("/todos")
    CollectionModel<EntityModel<ToDo>> all() {
    }
}
