#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application.exception;

import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;

/**
 * Exception thrown in case service requested by use cases is not provided within service registry.
 *
 * @author vedad
 */
public class ServiceNotFoundException extends ApplicationException {
    private static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";

    public ServiceNotFoundException(Class<?> type) {
        super(String.format("Implementation for class %s not found", type), SERVICE_NOT_FOUND);
    }
}
