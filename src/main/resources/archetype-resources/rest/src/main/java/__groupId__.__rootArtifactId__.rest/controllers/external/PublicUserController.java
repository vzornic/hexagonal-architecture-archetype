#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.controllers.external;

import ${groupId}.${rootArtifactId}.application.Application;
import ${groupId}.${rootArtifactId}.application.usecase.user.RegisterUserUseCase;
import ${groupId}.${rootArtifactId}.domain.User;
import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Non authenticated APIs dealing for users logic.
 *
 * @author vedad
 */
@RestController
@RequestMapping("/api/v1/public/users")
public class PublicUserController {

    private final Application application;

    @Autowired
    public PublicUserController(Application application) {
        this.application = application;
    }

    @PostMapping
    public User register(
            @RequestBody RegisterUserUseCase.Request request
    ) throws ApplicationException {
        return application.execute(new RegisterUserUseCase(request), null);
    }
}
