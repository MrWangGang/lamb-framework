package org.lamb.lambframework.core.security;

import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.EventException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LambServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        throw new EventException(ExceptionEnum.EA00000000);
    }
}
