/*
package org.lamb.lambframework.core.adapter;

import org.lamb.lambframework.core.annotation.LambFormatJson;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.EventException;
import org.lamb.lambframework.core.templete.LambResponseTemplete;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;


*/
/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 *//*

@RestControllerAdvice(annotations = LambFormatJson.class)
public class GlobalResponseBodyAdapter implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public LambResponseTemplete beforeBodyWrite(Object returnValue, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        
        if(returnValue != null){
            if(!(returnValue instanceof LambResponseTemplete)){
                throw new EventException(ExceptionEnum.ES00000023);
            }
            return (LambResponseTemplete) returnValue;
        }
        return new LambResponseTemplete();
    }
}
*/
