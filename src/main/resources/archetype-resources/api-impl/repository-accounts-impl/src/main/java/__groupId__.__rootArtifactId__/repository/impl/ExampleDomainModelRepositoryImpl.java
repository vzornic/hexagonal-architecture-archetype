#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.repository.impl;

import ${groupId}.${rootArtifactId}.domain.ExampleDomainModel;
import ${groupId}.${rootArtifactId}.repository.ExampleDomainModelRepository;
import ${groupId}.${rootArtifactId}.repository.impl.entities.ExampleEntity;
import ${groupId}.${rootArtifactId}.repository.impl.internal.InternalExampleEntityRespository;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of {@link ExampleDomainModelRepository} using spring data.
 *
 * @author vedad
 */
public class ExampleDomainModelRepositoryImpl implements ExampleDomainModelRepository {
    private final InternalExampleEntityRespository repository;

    public ExampleDomainModelRepositoryImpl(InternalExampleEntityRespository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ExampleDomainModel> getById(String id) {
        return repository
                .findById(UUID.fromString(id))
                .map(ExampleEntity::toDomainModel);
    }
}
