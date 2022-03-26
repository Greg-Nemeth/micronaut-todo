package example.micronaut;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.annotation.Factory;
import io.micronaut.runtime.http.scope.RequestScope;


@Factory
public class DataLoaderRegistryFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DataLoaderRegistryFactory.class);

    @SuppressWarnings("unused")
    @RequestScope
    public DataLoaderRegistry dataLoaderRegistry(AuthorDataLoader authorDataLoader) {
        DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
        dataLoaderRegistry.register(
                    "author", 
                    DataLoader.newMappedDataLoader(authorDataLoader)
        );

        LOG.trace("created new data loader registry");

        return dataLoaderRegistry;
    }
}