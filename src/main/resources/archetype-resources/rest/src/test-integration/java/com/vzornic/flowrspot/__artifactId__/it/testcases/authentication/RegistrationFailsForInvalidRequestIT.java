#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.it.testcases.authentication;

import ${groupId}.${rootArtifactId}.accounts.request.NewUserRequest;
import ${groupId}.${rootArtifactId}.it.configuration.BaseIT;
import ${groupId}.${rootArtifactId}.json.Json;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author vedad
 */
@ActiveProfiles("test")
public class RegistrationFailsForInvalidRequestIT extends BaseIT {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/auth/setup.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/auth/teardown.sql")
    public void test() throws Exception {
        NewUserRequest request = new NewUserRequest("wrong-email", "test", "test");

        // 1. Perform registration, assert it's ok
        this.mockMvc.perform(post("/api/v1/public/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.toJson(request))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("${symbol_dollar}.message").value("Errors found while validating request."))
                .andExpect(jsonPath("${symbol_dollar}.code").value("VALIDATION_ERRORS"))
                .andExpect(jsonPath("${symbol_dollar}.errors[0].path").value("email"))
                .andExpect(jsonPath("${symbol_dollar}.errors[0].message").value("must be a well-formed email address"));

    }
}
