#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application.usecase.example;

import ${groupId}.${rootArtifactId}.application.Context;
import ${groupId}.${rootArtifactId}.application.ServiceRegistry;
import ${groupId}.${rootArtifactId}.application.UseCase;
import ${groupId}.${rootArtifactId}.domain.ExampleDomainModel;
import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import ${groupId}.${rootArtifactId}.repository.ExampleDomainModelRepository;
import ${groupId}.${rootArtifactId}.repository.exceptions.EntityNotFoundException;

/**
 * Use case for getting example domain model.
 *
 * @author vedad
 */
public class GetExampleDomainModelByIdUseCase extends UseCase<ExampleDomainModel> {

    private final String id;

    public GetExampleDomainModelByIdUseCase(String id) {
        this.id = id;
    }

    @Override
    public ExampleDomainModel execute(ServiceRegistry registry, Context context) throws ApplicationException {
        final ExampleDomainModelRepository exampleDomainModelRepository = registry.get(ExampleDomainModelRepository.class);

        return exampleDomainModelRepository.getById(this.id).orElseThrow(() -> new EntityNotFoundException(this.id));
    }
}
