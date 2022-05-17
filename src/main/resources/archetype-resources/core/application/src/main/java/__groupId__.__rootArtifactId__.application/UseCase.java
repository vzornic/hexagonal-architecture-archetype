#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application;

import ${groupId}.${rootArtifactId}.application.exception.PropertiesValidationException;
import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Abstract use case class. Use case is a class whose responsibility is to execute one business use case.
 * @author vedad
 */
public abstract class UseCase<R> {

    /**
     * Executes use case.
     *
     * @param registry Service registry
     * @param context Use case context
     *
     * @return Use case result
     * @throws ApplicationException in case of business exceptions
     */
    abstract public R execute(ServiceRegistry registry, Context context) throws ApplicationException;

    /**
     * Validates requests and potentially throws PropertiesValidationException in case any is found.
     *
     * Default implementation will use reflection to retrieve all declared fields and run validation over them.
     *
     * @throws ${groupId}.${rootArtifactId}.exception.PropertiesValidationException
     */
    protected void validate() throws ApplicationException {
        Field[] fields = getClass().getDeclaredFields();
        List<ConstraintViolation<?>> violations = new ArrayList<>();

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            for (Field field: fields) {
                field.setAccessible(true);
                Object value = field.get(this);

                if (value != null) {
                    Set<ConstraintViolation<Object>> contraints = validator.validate(value);
                    violations.addAll(contraints);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failure during validate", e);
        }

        if (!violations.isEmpty()) {
            throw new PropertiesValidationException(violations);
        }
    }
}
