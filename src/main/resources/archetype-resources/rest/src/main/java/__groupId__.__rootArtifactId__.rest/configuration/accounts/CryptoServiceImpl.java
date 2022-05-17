#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.configuration.accounts;

import ${groupId}.${rootArtifactId}.accounts.impl.CryptoService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.Validation;

/**
 * @author vedad
 */
@Component
public class CryptoServiceImpl implements CryptoService {

    @Override
    public String generateSalt() {
        return BCrypt.gensalt();
    }

    @Override
    public String hashPw(String raw, String salt) {
        return BCrypt.hashpw(raw, salt);
    }
}
