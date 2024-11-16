package todo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final ToDoModelAssembler assembler;

    ToDoController(ToDoRepository repository, ToDoModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // get all ToDos 
    @GetMapping("/todos")
    CollectionModel<EntityModel<ToDo>> all() {
        List<EntityModel<ToDo>> orders = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(orders, //
                linkTo(methodOn(ToDoController.class).all()).withSelfRel());
    }

    // get one todo
    @GetMapping("/todos/{id}")
    EntityModel<ToDo> one(@PathVariable Long id) {

        ToDo todo = repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));

        return assembler.toModel(todo);

    }

    // post one todo
    @PostMapping("/todos")
    ResponseEntity<EntityModel<ToDo>> newToDo(@RequestBody ToDo todo) { 

        todo.setStatus(Status.TO_DO);
        ToDo newToDo = repository.save(todo);

        return ResponseEntity //
            .created(linkTo(methodOn(ToDoController.class).one(newToDo.getId())).toUri()) //
            .body(assembler.toModel(newToDo));
    }
}
