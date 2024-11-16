package todo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ToDoModelAssembler implements RepresentationModelAssembler<ToDo, EntityModel<ToDo>> {

    @Override
    public EntityModel<ToDo> toModel(ToDo todo) {

        EntityModel<ToDo> todoModel = EntityModel.of(todo,
                linkTo(methodOn(ToDoController.class).one(todo.getId())).withSelfRel(),
                linkTo(methodOn(ToDoController.class).all()).withRel("orders"));

        if (todo.getStatus() == Status.TO_DO) {
            todoModel.add(linkTo(methodOn(ToDoController.class).cancel(todo.getId())).withRel("cancel"));
            todoModel.add(linkTo(methodOn(ToDoController.class).complete(todo.getId())).withRel("complete"));
        } else {
            todoModel.add(linkTo(methodOn(ToDoController.class).reset(todo.getId())).withRel("reset"));
        }

        return  todoModel;
    }
}
