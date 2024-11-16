package todo;

class ToDoNotFoundException extends RuntimeException {

    ToDoNotFoundException(Long id) {
        super("Could not find To-Do of id: " + id);
    }
}
