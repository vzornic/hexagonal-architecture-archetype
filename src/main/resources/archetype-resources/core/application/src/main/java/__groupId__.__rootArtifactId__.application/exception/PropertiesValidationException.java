#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application.exception;

import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.List;

/**
 * Exception thrown in case of use case request validation errors.
 *
 * @author vedad
 */
@Getter
public class PropertiesValidationException extends ApplicationException {
    public static final String VALIDATION_ERRORS = "VALIDATION_ERRORS";
    private final List<ConstraintViolation<?>> violations;

    public PropertiesValidationException(List<ConstraintViolation<?>> violations) {
        super(VALIDATION_ERRORS);
        this.violations = violations;
    }
}
