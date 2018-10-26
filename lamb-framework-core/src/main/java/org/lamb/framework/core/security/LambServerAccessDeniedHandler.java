package org.lamb.framework.core.security;

import org.lamb.framework.core.exception.EventException;
import org.lamb.framework.core.enumeration.ExceptionEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LambServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        throw new EventException(ExceptionEnum.EA00000001);
    }
}
