#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest;

import ${groupId}.${rootArtifactId}.application.Application;
import ${groupId}.${rootArtifactId}.application.ServiceRegistry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuring spring web app and {@link ${groupId}.${rootArtifactId}.application.Application} bean.
 *
 * @author vedad
 */
@Configuration
@ComponentScan(basePackages = "${groupId}.*")
@EnableJpaRepositories(basePackages = "${groupId}.*")
@EntityScan(basePackages = "${groupId}.*")
@EnableWebMvc
public class WebAppConfiguration {

    /**
     * Creates {@link Application} bean.
     *
     * @param registry Service Registry
     * @return Application instance
     */
    @Bean
    public Application application(ServiceRegistry registry) {
        return new Application(registry);
    }
}
