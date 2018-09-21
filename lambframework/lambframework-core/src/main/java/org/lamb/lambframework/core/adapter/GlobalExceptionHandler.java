package org.lamb.lambframework.core.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.basic.GlobalException;
import org.lamb.lambframework.core.templete.LambResponseTemplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        return write(serverWebExchange.getResponse(),handleTransferException(throwable));
    }

    private LambResponseTemplete handleTransferException(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        logger.debug(str);
        if(e instanceof GlobalException){
            return result(((GlobalException)e).getCode(),((GlobalException)e).getMessage());
        }  else if (e.getCause()!=null) {
            if(e.getCause() instanceof GlobalException){
                return result(((GlobalException)e.getCause()).getCode(),((GlobalException)e.getCause()).getMessage());
            }
        }else if (e instanceof MethodArgumentNotValidException) {
            return result(ExceptionEnum.EI00000001.getCode(),ExceptionEnum.EI00000001.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            return result(ExceptionEnum.ES00000000.getCode(),ExceptionEnum.ES00000000.getMessage());
        } else if (e instanceof NullPointerException) {
            return result(ExceptionEnum.ES00000000.getCode(),ExceptionEnum.ES00000000.getMessage());
        } else if (e instanceof RuntimeException) {
            return result(ExceptionEnum.ES00000000.getCode(),ExceptionEnum.ES00000000.getMessage());
        }else if ( e instanceof Exception){
            return result(ExceptionEnum.ES00000000.getCode(),ExceptionEnum.ES00000000.getMessage());
        }
        return result(ExceptionEnum.ES00000000.getCode(),ExceptionEnum.ES00000000.getMessage());
    }

    private LambResponseTemplete result(String code, String message){
        LambResponseTemplete lambResponseTemplete = new LambResponseTemplete();
        lambResponseTemplete.setService_code(code);
        lambResponseTemplete.setService_message(message);
        return lambResponseTemplete;
    }



    private  <T> Mono<Void> write(ServerHttpResponse httpResponse, T object) {
        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return httpResponse
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = httpResponse.bufferFactory();
                    try {
                        return bufferFactory.wrap((new ObjectMapper()).writeValueAsBytes(object));
                    } catch (Exception ex) {
                        logger.warn("Error writing response", ex);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }
}
