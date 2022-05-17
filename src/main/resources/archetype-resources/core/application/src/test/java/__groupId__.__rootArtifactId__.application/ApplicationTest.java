#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.application;

import ${groupId}.${rootArtifactId}.domain.exception.ApplicationException;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author vedad
 */
@RunWith(EasyMockRunner.class)
public class ApplicationTest extends EasyMockSupport {

    @Mock
    private ServiceRegistry registry;

    @Test
    public void test_execute_calls_use_case_execute() throws ApplicationException {
        Application ${artifactId} = new Application(registry);

        String expected = "expected";
        UseCase<String> useCase = mock(UseCase.class);
        Context context = Context.builder().build();

        useCase.validate();
        expectLastCall();

        expect(useCase.execute(registry, context)).andReturn(expected);

        replayAll();

        String actual = ${artifactId}.execute(useCase, context);

        verifyAll();

        assertEquals(expected, actual);
    }

    @Test(expected = ApplicationException.class)
    public void test_execute_propagates_${artifactId}_exceptions() throws ApplicationException {
        Application ${artifactId} = new Application(registry);

        UseCase<String> useCase = mock(UseCase.class);
        Context context = Context.builder().build();

        useCase.validate();
        expectLastCall();

        expect(useCase.execute(registry, context)).andThrow(new ApplicationException("CODE"));

        replayAll();

        try {
            ${artifactId}.execute(useCase, context);
        } finally {
            verifyAll();
        }
    }
}