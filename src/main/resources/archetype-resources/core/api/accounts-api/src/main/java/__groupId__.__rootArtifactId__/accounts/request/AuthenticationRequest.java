#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.request;

import lombok.Value;

/**
 * Authentication request.
 *
 * @author vedad
 */
@Value
public class AuthenticationRequest {
    private final String username;
    private final String password;
}
