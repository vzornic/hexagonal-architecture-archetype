#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.it.configuration;

import ${groupId}.${rootArtifactId}.RestApplication;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Base configuration for running spring boot integration tests.
 *
 * @author vedad
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
@ContextConfiguration(initializers = {BaseIT.Initializer.class})
@AutoConfigureMockMvc
@DirtiesContext
public abstract class BaseIT {

    @Autowired
    protected MockMvc mockMvc;
    @ClassRule
    public static PostgreSQLContainer POSTGRES = new PostgreSQLContainer("postgres")
            .withDatabaseName("example")
            .withUsername("example")
            .withPassword("example");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRES.getUsername(),
                    "spring.datasource.password=" + POSTGRES.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }
}
