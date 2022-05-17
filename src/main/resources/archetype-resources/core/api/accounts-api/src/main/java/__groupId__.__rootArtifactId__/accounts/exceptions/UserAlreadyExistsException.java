#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.exceptions;

import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;

/**
 * Exception thrown in case of attempt to register user while it already exists.
 *
 * @author vedad
 */
public class UserAlreadyExistsException extends ApplicationException {
    private static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";

    public UserAlreadyExistsException() {
        super(USER_ALREADY_EXISTS);
    }
}
