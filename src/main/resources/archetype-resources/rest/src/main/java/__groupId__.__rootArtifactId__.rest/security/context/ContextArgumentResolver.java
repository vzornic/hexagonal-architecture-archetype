#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${groupId}.${rootArtifactId}.rest.security.context;

import ${groupId}.${rootArtifactId}.application.Context;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ContextArgumentResolver implements HandlerMethodArgumentResolver {
	public static final String CONTEXT_ATTRIBUTE = "${groupId}.context";

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return Context.class.equals(methodParameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
								  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {

		final Object contextObject = nativeWebRequest.getAttribute(CONTEXT_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

		if (contextObject == null) {
			throw new IllegalStateException("No context object set to request");
		}

		return contextObject;
	}
}
