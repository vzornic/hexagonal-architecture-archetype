#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application.usecase.user;

import ${groupId}.${rootArtifactId}.accounts.AccountsService;
import ${groupId}.${rootArtifactId}.accounts.request.NewUserRequest;
import ${groupId}.${rootArtifactId}.application.Context;
import ${groupId}.${rootArtifactId}.application.ServiceRegistry;
import ${groupId}.${rootArtifactId}.domain.User;
import org.easymock.Capture;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author vedad
 */
@RunWith(EasyMockRunner.class)
public class RegisterUserUseCaseTest extends EasyMockSupport {

    @Mock
    private ServiceRegistry serviceRegistry;

    @Mock
    private AccountsService accountsService;

    @Test
    public void test_execute_successfully_registers_new_user() throws Exception {
        final User expected = new User("", "", "");

        final RegisterUserUseCase.Request request = new RegisterUserUseCase.Request();
        request.setUsername("username");
        request.setEmail("email");
        request.setPassword("password");

        final RegisterUserUseCase useCase = new RegisterUserUseCase(request);

        expect(serviceRegistry.get(AccountsService.class)).andReturn(accountsService);

        Capture<NewUserRequest> capture = Capture.newInstance();
        expect(accountsService.register(capture(capture))).andReturn(expected);


        replayAll();

        final User actual = useCase.execute(serviceRegistry, Context.builder().build());

        verifyAll();

        assertEquals(actual, expected);
        assertEquals(request.getUsername(), capture.getValue().getUsername());
        assertEquals(request.getEmail(), capture.getValue().getEmail());
        assertEquals(request.getPassword(), capture.getValue().getPassword());
    }
}