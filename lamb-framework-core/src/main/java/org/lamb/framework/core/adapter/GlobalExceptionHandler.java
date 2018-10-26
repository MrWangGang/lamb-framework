package org.lamb.framework.core.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lamb.framework.core.exception.basic.GlobalException;
import org.lamb.framework.core.templete.LambResponseTemplete;
import org.lamb.framework.core.enumeration.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@Component
@RestControllerAdvice
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    @ExceptionHandler(WebExchangeBindException.class)
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
        } else if(checkGlobalExcetionOStackTrace(e.getStackTrace())){
            return result(((GlobalException)e).getCode(),((GlobalException)e).getMessage());
        } else if(e instanceof WebExchangeBindException){
            return result(ExceptionEnum.EI00000000.getCode(),ExceptionEnum.EI00000000.getMessage());
        }else if(e instanceof NoSuchElementException){
            return result(ExceptionEnum.ES00000027.getCode(),ExceptionEnum.ES00000027.getMessage());
        }else if(e instanceof RedisSystemException){
            return result(ExceptionEnum.ES00000026.getCode(),ExceptionEnum.ES00000026.getMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            return result(ExceptionEnum.EI00000000.getCode(),ExceptionEnum.EI00000000.getMessage());
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

    private Boolean checkGlobalExcetionOStackTrace(StackTraceElement[] es){
       return Arrays.stream(es).anyMatch(e -> GlobalException.class.getName().equals(e.getClassName()));
    }



}
