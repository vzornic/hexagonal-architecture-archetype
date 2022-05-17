#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.it.testcases.authentication;

import ${groupId}.${rootArtifactId}.accounts.request.AuthenticationRequest;
import ${groupId}.${rootArtifactId}.accounts.request.NewUserRequest;
import ${groupId}.${rootArtifactId}.it.configuration.BaseIT;
import ${groupId}.${rootArtifactId}.json.Json;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author vedad
 */
@ActiveProfiles("test")
public class NewRegisteredUserIsAbleToLoginIT extends BaseIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/auth/setup.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/auth/teardown.sql")
    public void test() throws Exception {
        NewUserRequest request = new NewUserRequest("test@test.com", "test", "test");

        // 1. Perform registration, assert it's ok
        this.mockMvc.perform(post("/api/v1/public/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.toJson(request))
                )
                .andExpect(status().isOk())
                .andReturn();

        // 2. Perform login, verify it logs in and returns token
        this.mockMvc.perform(post("/api/v1/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.toJson(new AuthenticationRequest("test", "test")))
        )
                .andExpect(status().isOk())
                .andExpect(header().exists("Token"));

    }
}
