#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.repository;

import ${groupId}.${rootArtifactId}.domain.ExampleDomainModel;

import java.util.Optional;

/**
 * Specification for interacting with data store for domain model use case.
 *
 * @author vedad
 */
public interface ExampleDomainModelRepository {

    /**
     * Gets domain model by id.
     *
     * @param id Example entity id
     * @return optionally found model
     */
    Optional<ExampleDomainModel> getById(String id);
}
