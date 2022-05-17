#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application;

import ${groupId}.${rootArtifactId}.application.exception.ServiceNotFoundException;

/**
 * Specification for service registry. Service registry is class holding all necessary service implementation (api-impl).
 *
 * @author vedad
 */
public interface ServiceRegistry {

    /**
     * Gets a service for provided type
     * @param type
     * @return
     * @param <T>
     */
    <T> T get(Class<T> type) throws ServiceNotFoundException;
}
