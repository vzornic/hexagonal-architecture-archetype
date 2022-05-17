#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.domain;


import lombok.Value;
import lombok.experimental.NonFinal;

/**
 * User ${artifactId} model.
 *
 * @author vedad
 */
@Value
@NonFinal
public class User {
    private final String id;
    private final String email;
    private final String username;
}
