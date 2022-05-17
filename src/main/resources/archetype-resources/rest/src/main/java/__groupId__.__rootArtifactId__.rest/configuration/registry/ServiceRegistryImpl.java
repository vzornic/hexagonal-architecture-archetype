#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.configuration.registry;

import ${groupId}.${rootArtifactId}.application.ServiceRegistry;
import ${groupId}.${rootArtifactId}.application.exception.ServiceNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Implementing {@link ServiceRegistry} using spring's ApplicationContext.
 *
 * @author vedad
 */
@Component
public class ServiceRegistryImpl implements ServiceRegistry {

    private final ApplicationContext context;

    @Autowired
    public ServiceRegistryImpl(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public <T> T get(Class<T> type) throws ServiceNotFoundException {
        try {
            return context.getBean(type);
        } catch (BeansException e) {
            throw new ServiceNotFoundException(type);
        }
    }
}
