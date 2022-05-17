#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ${groupId}.${rootArtifactId}.accounts.exceptions.AuthenticationException;
import ${groupId}.${rootArtifactId}.application.Context;
import ${groupId}.${rootArtifactId}.domain.User;
import ${groupId}.${rootArtifactId}.rest.json.Json;
import ${groupId}.${rootArtifactId}.rest.security.context.ContextArgumentResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ${groupId}.${rootArtifactId}.rest.security.jwt.JWTConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final JWTConfiguration configuration;

	public JWTAuthorizationFilter(JWTConfiguration configuration, AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.configuration = configuration;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = request.getHeader(AUTHORIZATION_HEADER);

		if (StringUtils.isNotEmpty(token) && StringUtils.startsWith(token, AUTHORIZATION_TYPE_SUFFIXED)) {
			String sanitized = StringUtils.removeStart(token, AUTHORIZATION_TYPE_SUFFIXED);

			try {
				authenticate(sanitized, request);
				chain.doFilter(request, response);
			} catch (AuthenticationException e) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
			}
		} else {
			response.setStatus(HttpStatus.FORBIDDEN.value());
		}
	}

	// Reads the JWT from the Authorization header, and then uses JWT to validate the token
	private void authenticate(String token, HttpServletRequest request) throws AuthenticationException {
		if (StringUtils.isNotEmpty(token)) {
			try {
				DecodedJWT jwt = JWT.require(Algorithm.HMAC512(configuration.getSecret().getBytes()))
						.build()
						.verify(token);

				String username = jwt.getSubject();

				if (StringUtils.isNotEmpty(username)) {

					User principal = Json.fromJson(jwt.getClaim(PRINCIPAL_CLAIM).asString(), JsonizedUser.class);
					request.setAttribute(
							ContextArgumentResolver.CONTEXT_ATTRIBUTE,
							Context.builder()
									.user(principal)
									.build()
					);

				} else {
					throw new AuthenticationException();
				}
			} catch (Exception e) {
				throw new AuthenticationException();
			}
		}
	}

	private static class JsonizedUser extends User {
		@JsonCreator
		public JsonizedUser(
				@JsonProperty("id") String id,
				@JsonProperty("email") String email,
				@JsonProperty("username") String username) {
			super(id, email, username);
		}
	}
}
