#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ${groupId}.${rootArtifactId}.accounts.AccountsService;
import ${groupId}.${rootArtifactId}.accounts.exceptions.AuthenticationException;
import ${groupId}.${rootArtifactId}.accounts.exceptions.UserAlreadyExistsException;
import ${groupId}.${rootArtifactId}.accounts.impl.entities.UserEntity;
import ${groupId}.${rootArtifactId}.accounts.impl.internal.InternalUserRepository;
import ${groupId}.${rootArtifactId}.accounts.request.AuthenticationRequest;
import ${groupId}.${rootArtifactId}.accounts.request.NewUserRequest;
import ${groupId}.${rootArtifactId}.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of accounts service
 *
 * @author vedad
 */
@Slf4j
public class AccountsServiceImpl implements AccountsService {

    private final InternalUserRepository repository;
    private final CryptoService crypto;

    public AccountsServiceImpl(InternalUserRepository repository, CryptoService crypto) {
        this.repository = repository;
        this.crypto = crypto;
    }

    @Override
    public User register(NewUserRequest request) throws UserAlreadyExistsException {
        String salt = crypto.generateSalt();
        String hashedPw = crypto.hashPw(request.getPassword(), salt);


        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setId(UUID.randomUUID());
        user.setHashedPw(hashedPw);
        user.setSalt(salt);

        try {
            return repository.save(user).toDomainModel();
        } catch (DataIntegrityViolationException e) {
            log.warn("Attempt to create new user with same data as already existing one", e);
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public User authenticate(AuthenticationRequest request) throws AuthenticationException {
        UserEntity user = repository.findByUsername(request.getUsername()).orElseThrow(AuthenticationException::new);

        String hashed = crypto.hashPw(request.getPassword(), user.getSalt());

        if (user.getHashedPw().equals(hashed)) {
            return user.toDomainModel();
        } else {
            throw new AuthenticationException();
        }
    }
}
