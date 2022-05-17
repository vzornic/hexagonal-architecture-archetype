#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.security;

import ${groupId}.${rootArtifactId}.rest.security.jwt.JWTAuthorizationFilter;
import ${groupId}.${rootArtifactId}.rest.security.jwt.JWTConfiguration;
import ${groupId}.${rootArtifactId}.rest.security.jwt.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class WebSecurityConfiguration {

    @EnableWebSecurity
    @Order(0)
    static class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {
        private static final String USER_LOGIN_URL = "/api/v1/users/authenticate";
        private static final RequestMatcher AUTHENTICATED_REQUESTS = new NegatedRequestMatcher(
                new OrRequestMatcher(
                        new AntPathRequestMatcher("/api/v1/public/**"),
                        new AntPathRequestMatcher("/swagger-ui/**")
                )
        );

        private final UserDetailsService userDetailsService;
        private final BCryptPasswordEncoder passwordEncoder;
        private final JWTConfiguration jwtConfiguration;

        @Autowired
        public UserSecurityConfiguration(UserDetailsService userDetailsService,
                                         JWTConfiguration jwtConfiguration,
                                         BCryptPasswordEncoder passwordEncoder) {
            this.userDetailsService = userDetailsService;
            this.passwordEncoder = passwordEncoder;
            this.jwtConfiguration = jwtConfiguration;
        }

		@Override
		public void configure(WebSecurity web) throws Exception {
			web
					.ignoring()
					.antMatchers("/api/v1/public/**", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs");
		}

		@Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatcher(AUTHENTICATED_REQUESTS)
                    	.authorizeRequests()
                    	.anyRequest().permitAll() //Permit all requests not covered with AUTH apis
                    .and()
                    	.sessionManagement()
                    	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    	.exceptionHandling()
                    .and()
                    	.headers()
                    	.frameOptions()
                    .	sameOrigin()
                    .and()
                    	.addFilter(new JWTLoginFilter(jwtConfiguration, authenticationManager(), USER_LOGIN_URL))
                        .addFilter(new JWTAuthorizationFilter(jwtConfiguration, authenticationManager()))
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }
    }
}
