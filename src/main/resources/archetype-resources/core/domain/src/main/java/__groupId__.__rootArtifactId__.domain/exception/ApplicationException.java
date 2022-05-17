#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.domain.exception;

import lombok.Getter;

/**
 * Base exception for all known application exceptions.
 *
 * @author vedad
 */
@Getter
public class ApplicationException extends Exception {
    /**
     * Code that should be unique for each exception.
     */
    private final String code;

    public ApplicationException(String code) {
        this(null, code);
    }

    public ApplicationException(String message, String code) {
        super(message);
        this.code = code;
    }
}
