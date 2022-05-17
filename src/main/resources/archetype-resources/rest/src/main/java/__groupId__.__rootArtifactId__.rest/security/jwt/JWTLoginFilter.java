#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import ${groupId}.${rootArtifactId}.application.usecase.user.AuthenticateUseCase;
import ${groupId}.${rootArtifactId}.rest.json.Json;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static ${groupId}.${rootArtifactId}.rest.security.jwt.JWTConstants.PRINCIPAL_CLAIM;
import static ${groupId}.${rootArtifactId}.rest.security.jwt.JWTConstants.TOKEN_HEADER;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JWTConfiguration configuration;

	public JWTLoginFilter(JWTConfiguration configuration, AuthenticationManager authenticationManager, String loginUrl) {
		this.authenticationManager = authenticationManager;
		this.configuration = configuration;
		setFilterProcessesUrl(loginUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			var params = Json.fromJson(request.getInputStream(), AuthenticateUseCase.Request.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					params.getUsername(), params.getPassword()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) {
		try {
			JWTUser details = (JWTUser) authResult.getPrincipal();

			String token = JWT.create()
					.withSubject( details.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + configuration.getExpiry()))
					.withClaim(PRINCIPAL_CLAIM, Json.toJson(details.getPrincipal()))
					.sign(Algorithm.HMAC512(configuration.getSecret().getBytes()));

			response.setHeader(TOKEN_HEADER, token);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
