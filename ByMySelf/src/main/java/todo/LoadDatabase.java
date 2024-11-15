package todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ToDoRepository todo) {

        return args -> {
            todo.save(new ToDo("read", "read 10 min", Status.COMPLETED));
            todo.save(new ToDo("write", "write 30 min", Status.TO_DO));
            todo.save(new ToDo("gym", "go to the gym", Status.CANCELLED));

            todo.findAll().forEach(t -> log.info("Preloaded " + t));
        };
    }
}
