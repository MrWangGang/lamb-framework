package org.lamb.lambframework.core.adapter;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.EventException;
import org.lamb.lambframework.core.exception.basic.GlobalException;
import org.lamb.lambframework.core.templete.LambResponseTemplete;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@RestControllerAdvice
public class GlobalExceptionAdapter {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionAdapter.class);


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public LambResponseTemplete handleTransferException(Exception e) {
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
            return result(ExceptionEnum.ES00000019.getCode(),ExceptionEnum.ES00000019.getMessage());
        } else if (e instanceof NullPointerException) {
            return result(ExceptionEnum.ES00000019.getCode(),ExceptionEnum.ES00000019.getMessage());
        } else if (e instanceof RuntimeException) {
            return result(ExceptionEnum.ES00000019.getCode(),ExceptionEnum.ES00000019.getMessage());
        }else if ( e instanceof Exception){
            return result(ExceptionEnum.ES00000019.getCode(),ExceptionEnum.ES00000019.getMessage());
        }

        return result(ExceptionEnum.ES00000019.getCode(),ExceptionEnum.ES00000019.getMessage());
    }

    private LambResponseTemplete result(String code, String message){
        LambResponseTemplete lambResponseTemplete = new LambResponseTemplete();
        lambResponseTemplete.setService_code(code);
        lambResponseTemplete.setService_message(message);
        return lambResponseTemplete;
    }
}
