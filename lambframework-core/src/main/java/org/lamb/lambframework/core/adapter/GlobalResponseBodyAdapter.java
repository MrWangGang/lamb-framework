package org.lamb.lambframework.core.adapter;

import org.lamb.lambframework.core.templete.ResponseTemplete;
import org.lamb.lambframework.core.annotation.LambFormatJson;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@ControllerAdvice(annotations = LambFormatJson.class)
public class GlobalResponseBodyAdapter implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        
        if(returnValue != null){
            return new ResponseTemplete(returnValue);
        }
        return new ResponseTemplete();
    }



}
