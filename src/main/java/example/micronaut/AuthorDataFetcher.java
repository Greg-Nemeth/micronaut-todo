package example.micronaut;

import java.util.concurrent.CompletionStage;

import org.dataloader.DataLoader;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Singleton;

@Singleton 
public class AuthorDataFetcher implements DataFetcher<CompletionStage<Author>> {

    @Override
    public CompletionStage<Author> get(DataFetchingEnvironment environment) {
        ToDo toDo = environment.getSource();
        DataLoader<Long, Author> authorDataLoader = environment.getDataLoader("author"); 
        return authorDataLoader.load(toDo.getAuthorId());
    }
}