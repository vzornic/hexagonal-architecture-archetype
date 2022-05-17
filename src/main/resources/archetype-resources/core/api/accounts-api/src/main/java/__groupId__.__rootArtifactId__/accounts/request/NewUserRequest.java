#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

/**
 * Request model for creating a new user.
 *
 * @author vedad
 */
@Getter
@Value
public class NewUserRequest {
    private final String email;
    private final String username;
    private final String password;
}
