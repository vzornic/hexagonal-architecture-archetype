#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.security.jwt;

public class JWTConstants {
	public static final String PRINCIPAL_CLAIM = "principal";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String TOKEN_HEADER = "Token";
	public static final String AUTHORIZATION_TYPE_SUFFIXED = "Bearer ";
}
