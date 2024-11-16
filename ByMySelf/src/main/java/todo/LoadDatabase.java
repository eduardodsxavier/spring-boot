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
            todo.save(new ToDo("1", "exemple 1", Status.COMPLETED));
            todo.save(new ToDo("2", "exemple 2", Status.TO_DO));
            todo.save(new ToDo("3", "exemple 3", Status.CANCELLED));

            todo.findAll().forEach(t -> log.info("Preloaded " + t));
        };
    }
}
