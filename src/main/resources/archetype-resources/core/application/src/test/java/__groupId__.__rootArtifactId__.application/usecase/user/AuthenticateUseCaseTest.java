#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application.usecase.user;

import ${groupId}.${rootArtifactId}.accounts.AccountsService;
import ${groupId}.${rootArtifactId}.accounts.request.AuthenticationRequest;
import ${groupId}.${rootArtifactId}.application.Context;
import ${groupId}.${rootArtifactId}.application.ServiceRegistry;
import ${groupId}.${rootArtifactId}.domain.User;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

/**
 * @author vedad
 */
@RunWith(EasyMockRunner.class)
public class AuthenticateUseCaseTest extends EasyMockSupport {

    @Mock
    private ServiceRegistry serviceRegistry;

    @Mock
    private AccountsService accountsService;

    @Test
    public void test_execute_successfully_authenticates_user() throws Exception {
        final String username = "username";
        final String password = "password";
        final User expected = new User("", "", "");

        expect(serviceRegistry.get(AccountsService.class)).andReturn(accountsService);

        Capture<AuthenticationRequest> capture = Capture.newInstance();
        expect(accountsService.authenticate(EasyMock.capture(capture))).andReturn(expected);

        replayAll();

        AuthenticateUseCase.Request request = new AuthenticateUseCase.Request();
        request.setUsername(username);
        request.setPassword(password);

        final AuthenticateUseCase useCase = new AuthenticateUseCase(request);
        User actual = useCase.execute(serviceRegistry, Context.builder().build());

        verifyAll();

        assertEquals(expected, actual);
        assertEquals(username, capture.getValue().getUsername());
        assertEquals(password, capture.getValue().getPassword());
    }
}