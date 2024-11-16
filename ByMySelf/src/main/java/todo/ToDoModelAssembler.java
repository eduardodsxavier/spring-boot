package todo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ToDoModelAssembler implements RepresentationModelAssembler<ToDo, EntityModel<ToDo>> {

    @Override
    public EntityModel<ToDo> toModel(ToDo todo) {

        return EntityModel.of(todo);
    }
}
