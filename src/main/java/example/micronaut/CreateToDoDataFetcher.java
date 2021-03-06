package example.micronaut;

import javax.transaction.Transactional;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;

@Singleton 
public class CreateToDoDataFetcher implements DataFetcher<ToDo> {

    private final ToDoRepository toDoRepository;
    private final AuthorRepository authorRepository;

    public CreateToDoDataFetcher(ToDoRepository toDoRepository, 
                                 AuthorRepository authorRepository) {
        this.toDoRepository = toDoRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public ToDo get(DataFetchingEnvironment env) {
        String title = env.getArgument("title");
        String username = env.getArgument("author");

        Author author = authorRepository.findOrCreate(username); 

        ToDo toDo = new ToDo(title, author.getId());

        return toDoRepository.save(toDo); 
    }
}