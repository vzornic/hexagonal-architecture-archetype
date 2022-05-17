#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.security.userdetails;

import ${groupId}.${rootArtifactId}.accounts.impl.internal.InternalUserRepository;
import ${groupId}.${rootArtifactId}.rest.security.jwt.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final InternalUserRepository internalUserRepository;

    @Autowired
    public UserDetailsServiceImpl(InternalUserRepository internalUserRepository) {
        this.internalUserRepository = internalUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = internalUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new JWTUser(user.getUsername(), user.getHashedPw(), user.toDomainModel());
    }
}
