package example.micronaut;

import java.util.Collection;
import java.util.Optional;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import static io.micronaut.data.model.query.builder.sql.Dialect.POSTGRES;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = POSTGRES) 
public interface AuthorRepository extends CrudRepository<Author, Long> { 

    Optional<Author> findByUsername(String username); 

    Collection<Author> findByIdIn(Collection<Long> ids); 

    default Author findOrCreate(String username) { 
        return findByUsername(username).orElseGet(() -> save(new Author(username)));
    }
}