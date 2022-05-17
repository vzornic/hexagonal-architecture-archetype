#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application;

import ${groupId}.${rootArtifactId}.domain.User;
import lombok.Builder;
import lombok.Getter;

/**
 * Use case execution context.
 *
 * @author vedad
 */
@Builder
@Getter
public class Context {
    // Actor for use case
    private final User user;
}
