#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Example ${artifactId} model.
 *
 * @author vedad
 */
@Getter
@Builder
@AllArgsConstructor
public class ExampleDomainModel {
    private final String id;
    private final String name;
    private final String description;
}
