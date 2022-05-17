#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.accounts.impl;

import ${groupId}.${rootArtifactId}.accounts.exceptions.AuthenticationException;
import ${groupId}.${rootArtifactId}.accounts.exceptions.UserAlreadyExistsException;
import ${groupId}.${rootArtifactId}.accounts.impl.entities.UserEntity;
import ${groupId}.${rootArtifactId}.accounts.impl.internal.InternalUserRepository;
import ${groupId}.${rootArtifactId}.accounts.request.AuthenticationRequest;
import ${groupId}.${rootArtifactId}.accounts.request.NewUserRequest;
import ${groupId}.${rootArtifactId}.domain.User;
import org.easymock.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;
import java.util.UUID;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * @author vedad
 */
@RunWith(EasyMockRunner.class)
public class AccountsServiceImplTest extends EasyMockSupport {

    @Mock
    private InternalUserRepository repository;

    @Mock
    private CryptoService crypto;

    private AccountsServiceImpl service;

    @Before
    public void setUp() throws Exception {
        this.service = new AccountsServiceImpl(repository, crypto);
    }

    @Test
    public void test_register_creates_new_user() throws UserAlreadyExistsException {

        NewUserRequest request = new NewUserRequest(
                "email",
                "username",
                "password"
        );

        String salt = "salt";
        String hashed = "hashed";


        expect(crypto.generateSalt()).andReturn(salt);
        expect(crypto.hashPw(request.getPassword(), salt)).andReturn(hashed);

        Capture<UserEntity> userEntityCapture = Capture.newInstance();
        expect(repository.save(capture(userEntityCapture))).andAnswer(userEntityCapture::getValue);

        replayAll();

        User actual = service.register(request);

        verifyAll();

        assertEquals(request.getEmail(), userEntityCapture.getValue().getEmail());
        assertEquals(request.getUsername(), userEntityCapture.getValue().getUsername());
        assertEquals(hashed, userEntityCapture.getValue().getHashedPw());
        assertEquals(salt, userEntityCapture.getValue().getSalt());

        assertEquals(actual.getId(), userEntityCapture.getValue().getId().toString());
        assertEquals(actual.getEmail(), userEntityCapture.getValue().getEmail());
        assertEquals(actual.getUsername(), userEntityCapture.getValue().getUsername());
    }

    @Test
    public void test_authenticate_successfully_authenticates_user_for_correct_password() throws AuthenticationException {
        AuthenticationRequest request = new AuthenticationRequest("username", "password");
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setUsername("username");
        user.setEmail("email");
        user.setSalt("salt");
        user.setHashedPw("hashed");

        expect(repository.findByUsername(request.getUsername())).andReturn(Optional.of(user));
        expect(crypto.hashPw(request.getPassword(), user.getSalt())).andReturn(user.getHashedPw());

        replayAll();

        User actual = service.authenticate(request);

        verifyAll();

        assertEquals(user.getId().toString(), actual.getId());
        assertEquals(user.getUsername(), actual.getUsername());
        assertEquals(user.getEmail(), actual.getEmail());
    }

    @Test(expected = AuthenticationException.class)
    public void test_authenticate_fails_with_authentication_exception_on_wrong_password() throws AuthenticationException {
        AuthenticationRequest request = new AuthenticationRequest("username", "password");
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setUsername("username");
        user.setEmail("email");
        user.setSalt("salt");
        user.setHashedPw("hashed");

        expect(repository.findByUsername(request.getUsername())).andReturn(Optional.of(user));
        expect(crypto.hashPw(request.getPassword(), user.getSalt())).andReturn("WRONG PASSWORD");

        replayAll();

        try {
            service.authenticate(request);
        } finally {
            verifyAll();
        }
    }

    @Test(expected = AuthenticationException.class)
    public void test_authenticate_fails_with_authentication_exception_on_no_user_found() throws AuthenticationException {
        AuthenticationRequest request = new AuthenticationRequest("username", "password");

        expect(repository.findByUsername(request.getUsername())).andReturn(Optional.empty());

        replayAll();

        try {
            service.authenticate(request);
        } finally {
            verifyAll();
        }
    }
}