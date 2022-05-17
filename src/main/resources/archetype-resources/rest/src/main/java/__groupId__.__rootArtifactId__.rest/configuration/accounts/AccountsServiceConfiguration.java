#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.configuration.accounts;

import ${groupId}.${rootArtifactId}.accounts.AccountsService;
import ${groupId}.${rootArtifactId}.accounts.impl.AccountsServiceImpl;
import ${groupId}.${rootArtifactId}.accounts.impl.CryptoService;
import ${groupId}.${rootArtifactId}.accounts.impl.internal.InternalUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuring accounts service API impl
 *
 * @author vedad
 */
@Configuration
public class AccountsServiceConfiguration {

    @Bean
    public AccountsService accountsService(
            InternalUserRepository internalUserRepository,
            CryptoService cryptoService
    ) {
        return new AccountsServiceImpl(internalUserRepository, cryptoService);
    }
}
