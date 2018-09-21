package org.lamb.lambframework.core.handler;

import org.lamb.lambframework.core.templete.LambResponseTemplete;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class LambHandler {
    protected   Mono<ServerResponse> converter(Object data){
        return LambResponseTemplete.converter(data);
    }

    protected   Mono<ServerResponse> example(){
        return LambResponseTemplete.example();
    }
}
