#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.controllers.authenticated.request;

import ${groupId}.${rootArtifactId}.application.Application;
import ${groupId}.${rootArtifactId}.application.Context;
import ${groupId}.${rootArtifactId}.application.usecase.example.GetExampleDomainModelByIdUseCase;
import ${groupId}.${rootArtifactId}.domain.ExampleDomainModel;
import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling public domain model APIs.
 *
 * @author vedad
 */
@RestController
@RequestMapping("/api/v1/example-model")
public class ExampleDomainModelController {

    private final Application application;

    @Autowired
    public ExampleDomainModelController(Application application) {
        this.application = application;
    }

    @GetMapping("/{id}")
    public ExampleDomainModel getExampleModel(
            @PathVariable String id,
            Context context
    ) throws ApplicationException {
        return application.execute(new GetExampleDomainModelByIdUseCase(id), context);
    }

}
