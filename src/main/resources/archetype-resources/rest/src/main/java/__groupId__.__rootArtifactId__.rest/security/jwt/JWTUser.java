#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.security.jwt;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
public class JWTUser extends User {
	private final ${groupId}.${rootArtifactId}.domain.User principal;

	public JWTUser(String username, String password, ${groupId}.${rootArtifactId}.domain.User principal) {
		super(username, password, Collections.emptyList());
		this.principal = principal;
	}
}
