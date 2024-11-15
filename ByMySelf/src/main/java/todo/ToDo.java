package todo;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class ToDo { 

    private @Id @GeneratedValue Long id;
    private String name;
    private String description;
    private Status status;

    ToDo() {}

    ToDo(String name, String description, Status status) {

        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override 
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ToDo)) {
            return false;
        }

        ToDo todo = (ToDo) o;
        return Objects.equals(this.id, todo.id)
            && Objects.equals(this.name, todo.name)
            && Objects.equals(this.description, todo.description)
            && Objects.equals(this.status, todo.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.description, this.status);
        
    }

    @Override
    public String toString() {
        return "ToDo{id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", status=" + this.status + "}";
    }
}
