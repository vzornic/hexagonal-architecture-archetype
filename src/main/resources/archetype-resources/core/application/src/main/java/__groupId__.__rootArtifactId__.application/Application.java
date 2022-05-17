#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application;

import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import lombok.AllArgsConstructor;

/**
 * Simple implementation of Application (use case executor).
 *
 * @author vedad
 */
@AllArgsConstructor
public class Application {
    private final ServiceRegistry registry;

    /**
     * Simple implementation of executing use case via Application.
     *
     * More complex solution here would require UseCaseExecutionListeners with additional functionalities such as
     * pre/post execution hooks, transaction support etc...
     *
     * @param useCase use case to execute
     * @param context use case context
     *
     * @return result of use case execution
     *
     * @param <R> Result of use case execution
     *
     * @throws ApplicationException in case of handled issues during use case execution
     */
    public <R> R execute(UseCase<R> useCase, Context context) throws ApplicationException {
        useCase.validate();
        return useCase.execute(registry, context);
    }
}
