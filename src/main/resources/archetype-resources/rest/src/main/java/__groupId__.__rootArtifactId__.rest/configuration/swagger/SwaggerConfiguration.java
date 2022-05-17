#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.HttpAuthenticationScheme;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * @author vedad
 */
@EnableSwagger2
@Component
public class SwaggerConfiguration {
    @Bean
    public Docket docket() {
        HttpAuthenticationScheme authenticationScheme = HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name("Authorization")
                .build();

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("${groupId}"))
                .build()
                .securitySchemes(Collections.singletonList(authenticationScheme))
                .securityContexts(Collections.singletonList(securityContext()))
                .pathMapping("/");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(securityReferences())
                .build();
    }

    private List<SecurityReference> securityReferences() {
        return Collections.singletonList(
                new SecurityReference("Authorization", new AuthorizationScope[] { new AuthorizationScope("global", "global") })
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Flowrspot")
                .description("Povio assignment")
                .version("1.0.0")
                .build();
    }
}
