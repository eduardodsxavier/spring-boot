package todo;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

    // put/update a todo
    @PutMapping("/todos/{id}")
    ResponseEntity<?> updateToDo(@RequestBody ToDo newToDo, @PathVariable Long id) {
        ToDo updateToDo = repository.findById(id).map(todo -> {
            todo.setName(newToDo.getName());
            todo.setDescription(newToDo.getDescription());
            return repository.save(todo);
        }).orElseGet(() -> {
            return repository.save(newToDo);
        });

        return ResponseEntity //
            .created(assembler.toModel(updateToDo).getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(assembler.toModel(updateToDo));
    }

    // complete todo
    @PutMapping("/todos/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id) {
        ToDo todo = repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));

        if (todo.getStatus() == Status.TO_DO) {
            todo.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(repository.save(todo)));
        }

        return ResponseEntity //
            .status(HttpStatus.METHOD_NOT_ALLOWED) //
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
            .body(Problem.create() //
                    .withTitle("Method not allowed") //
                    .withDetail("You can't complete an order that is in the " + todo.getStatus() + " status"));
    }

    // return to to_do status
    @PutMapping("/todos/{id}/reset")
    ResponseEntity<?> reset(@PathVariable Long id) {
        ToDo todo = repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));

        if (todo.getStatus() == Status.COMPLETED || todo.getStatus() == Status.CANCELLED) {
            todo.setStatus(Status.TO_DO);
            return ResponseEntity.ok(assembler.toModel(repository.save(todo)));
        }

        return ResponseEntity //
            .status(HttpStatus.METHOD_NOT_ALLOWED) //
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
            .body(Problem.create() //
                    .withTitle("Method not allowed") //
                    .withDetail("You can't reset an order that is in the " + todo.getStatus() + " status"));

    }



    // cancell a todo
    @DeleteMapping("/todos/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {
        ToDo todo = repository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));

        if (todo.getStatus() == Status.TO_DO) {
            todo.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(repository.save(todo)));
        } 


        return ResponseEntity //
            .status(HttpStatus.METHOD_NOT_ALLOWED) //
            .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
            .body(Problem.create() //
                    .withTitle("Method not allowed") //
                    .withDetail("You can't cancel an order that is in the " + todo.getStatus() + " status"));
    }
}
