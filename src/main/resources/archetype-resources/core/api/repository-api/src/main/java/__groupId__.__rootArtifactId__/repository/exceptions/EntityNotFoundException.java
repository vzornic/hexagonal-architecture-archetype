#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.repository.exceptions;

import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;

/**
 * Exception thrown in case requested entity could not be found.
 *
 * @author vedad
 */
public class EntityNotFoundException extends ApplicationException {
    private static final String NOT_FOUND = "NOT_FOUND";

    public EntityNotFoundException(String entityId) {
        super(NOT_FOUND, String.format("Entity with given id %s was not found.", entityId));
    }
}
