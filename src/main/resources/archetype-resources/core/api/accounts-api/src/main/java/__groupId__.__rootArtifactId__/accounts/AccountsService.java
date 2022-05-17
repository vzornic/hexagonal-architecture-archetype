#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts;

import ${groupId}.${rootArtifactId}.accounts.exceptions.AuthenticationException;
import ${groupId}.${rootArtifactId}.accounts.exceptions.UserAlreadyExistsException;
import ${groupId}.${rootArtifactId}.accounts.request.AuthenticationRequest;
import ${groupId}.${rootArtifactId}.accounts.request.NewUserRequest;
import ${groupId}.${rootArtifactId}.domain.User;

/**
 * Specification for all interactions with accounts use cases.
 *
 * @author vedad
 */
public interface AccountsService {

    /**
     * Creates a new user.
     *
     * @param request Request for creating new user
     *
     * @return created user
     */
    User register(NewUserRequest request) throws UserAlreadyExistsException;

    /**
     * Authenticates user or trows exception in case authentication failed.
     *
     * @param request request for authentication
     * @return Authenticated user
     */
    User authenticate(AuthenticationRequest request) throws AuthenticationException;
}
