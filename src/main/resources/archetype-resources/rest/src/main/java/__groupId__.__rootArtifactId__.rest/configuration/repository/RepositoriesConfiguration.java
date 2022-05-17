#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.configuration.repository;

import ${groupId}.${rootArtifactId}.repository.ExampleDomainModelRepository;
import ${groupId}.${rootArtifactId}.repository.impl.ExampleDomainModelRepositoryImpl;
import ${groupId}.${rootArtifactId}.repository.impl.internal.InternalExampleEntityRespository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuring repositories implementations.
 *
 * @author vedad
 */
@Configuration
public class RepositoriesConfiguration {

    @Bean
    public ExampleDomainModelRepository exampleDomainModelRepository(
            InternalExampleEntityRespository internalExampleEntityRespository
    ) {
        return new ExampleDomainModelRepositoryImpl(internalExampleEntityRespository);
    }
}
