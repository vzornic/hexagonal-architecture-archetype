#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.exceptions;

import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;

/**
 * Exception thrown in case of unauthenticated action.
 *
 * @author vedad
 */
public class AuthenticationException extends ApplicationException {
    private static final String UNAUTHENTICATED = "UNAUTHENTICATED";

    public AuthenticationException() {
        super(UNAUTHENTICATED);
    }
}
