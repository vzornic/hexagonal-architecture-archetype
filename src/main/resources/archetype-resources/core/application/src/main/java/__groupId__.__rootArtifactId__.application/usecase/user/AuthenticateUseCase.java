#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application.usecase.user;

import ${groupId}.${rootArtifactId}.accounts.AccountsService;
import ${groupId}.${rootArtifactId}.accounts.request.AuthenticationRequest;
import ${groupId}.${rootArtifactId}.application.Context;
import ${groupId}.${rootArtifactId}.application.ServiceRegistry;
import ${groupId}.${rootArtifactId}.application.UseCase;
import ${groupId}.${rootArtifactId}.domain.User;
import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Use case for authenticating user.
 *
 * @author vedad
 */
public class AuthenticateUseCase extends UseCase<User> {

    private final Request request;

    public AuthenticateUseCase(Request request) {
        this.request = request;
    }

    @Override
    public User execute(ServiceRegistry registry, Context context) throws ApplicationException {
        final AccountsService accountsService = registry.get(AccountsService.class);

        return accountsService.authenticate(
                new AuthenticationRequest(request.username, request.password)
        );
    }

    @Setter
    @Getter
    public static class Request {
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }
}
