package org.lamb.lambframework.core.handler;

import org.lamb.lambframework.core.templete.LambResponseTemplete;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public abstract class LambHandler {

    protected Mono<ServerResponse> reactive(Object data){
        LambResponseTemplete lambResponseTemplete = new LambResponseTemplete(data);
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(lambResponseTemplete),LambResponseTemplete.class);
    }

    protected Mono<ServerResponse> reactive(){
        LambResponseTemplete lambResponseTemplete = new LambResponseTemplete();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just(lambResponseTemplete),LambResponseTemplete.class);
    }

    protected Mono<LambResponseTemplete> returning(Object data){
        return Mono.just(new LambResponseTemplete(data));
    }

    protected Mono<LambResponseTemplete> returning(){
        return Mono.just(new LambResponseTemplete());
    }
}
