package example.micronaut;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import io.micronaut.context.annotation.Bean;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.errors.SchemaMissingError;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import jakarta.inject.Singleton;

@Factory 
public class GraphQLFactory {

    @Singleton 
    public GraphQL graphQL(ResourceResolver resourceResolver,
                           ToDosDataFetcher toDosDataFetcher,
                           CreateToDoDataFetcher createToDoDataFetcher,
                           CompleteToDoDataFetcher completeToDoDataFetcher,
                           AuthorDataFetcher authorDataFetcher) {
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        // Load the schema
        InputStream schemaDefinition = resourceResolver
                .getResourceAsStream("classpath:schema.graphqls")
                .orElseThrow(SchemaMissingError::new);

        // Parse the schema and merge it into a type registry
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        typeRegistry.merge(schemaParser.parse(new BufferedReader(new InputStreamReader(schemaDefinition))));

        // Create the runtime wiring.
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring  
                        .dataFetcher("toDos", toDosDataFetcher))

                .type("Mutation", typeWiring -> typeWiring 
                        .dataFetcher("createToDo", createToDoDataFetcher)
                        .dataFetcher("completeToDo", completeToDoDataFetcher))

                .type("ToDo", typeWiring -> typeWiring 
                        .dataFetcher("author", authorDataFetcher))

                .build();

        // Create the executable schema.
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

        // Return the GraphQL bean.
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
