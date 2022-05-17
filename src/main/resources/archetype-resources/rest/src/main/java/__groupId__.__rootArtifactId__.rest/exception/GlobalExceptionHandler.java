#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.exception;

import ${groupId}.${rootArtifactId}.accounts.exceptions.UserAlreadyExistsException;
import ${groupId}.${rootArtifactId}.application.exception.PropertiesValidationException;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vedad
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(e.getCode())
                .message("Unable to create user as one with provided details already exists.")
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertiesValidationException.class)
    public ResponseEntity<ValidationErrorResponse> handlePropertiesValidationException(PropertiesValidationException e) {
        List<ValidationError> errors = e.getViolations().stream()
                .map(constraint -> new ValidationError(constraint.getPropertyPath().toString(), constraint.getMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(ValidationErrorResponse.builder()
                .errors(errors)
                .code(e.getCode())
                .message("Errors found while validating request.")
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    @SuperBuilder(toBuilder = true)
    @Getter
    public static class ErrorResponse {
        private String code;
        private String message;
    }

    @SuperBuilder(toBuilder = true)
    @Getter
    public static class ValidationErrorResponse extends ErrorResponse {
        private List<ValidationError> errors;
    }

    @Value
    public static class ValidationError {
        String path;
        String message;
    }
}
