#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application.usecase.user;

import ${groupId}.${rootArtifactId}.accounts.AccountsService;
import ${groupId}.${rootArtifactId}.accounts.request.NewUserRequest;
import ${groupId}.${rootArtifactId}.application.Context;
import ${groupId}.${rootArtifactId}.application.ServiceRegistry;
import ${groupId}.${rootArtifactId}.application.UseCase;
import ${groupId}.${rootArtifactId}.domain.User;
import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import lombok.Getter;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Use case for registering (creating) new user.
 *
 * @author vedad
 */
public class RegisterUserUseCase extends UseCase<User> {
    private final Request request;

    public RegisterUserUseCase(Request request) {
        this.request = request;
    }

    @Override
    public User execute(ServiceRegistry registry, Context context) throws ApplicationException {
        final AccountsService accountsService = registry.get(AccountsService.class);

        return accountsService.register(new NewUserRequest(request.email, request.username, request.password));
    }

    @Getter
    @Setter
    public static class Request {
        @Email
        private String email;
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }
}
