package example.micronaut;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import static io.micronaut.data.model.query.builder.sql.Dialect.POSTGRES;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = POSTGRES) 
public interface ToDoRepository extends PageableRepository<ToDo, Long> { 
}