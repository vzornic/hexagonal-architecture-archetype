#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.impl;

/**
 * Specifies crypto service to be provided by upper layer to give instructions on how to hash password.
 *
 * @author vedad
 */
public interface CryptoService {

    /**
     * Generates random salt.
     *
     * @return Generated salt
     */
    String generateSalt();

    /**
     *  Generates password hash for provided raw password and salt.
     *
     * @param raw Raw passowrd
     * @param salt Salt for hashing function
     * @return Hashed password
     */
    String hashPw(String raw, String salt);
}
