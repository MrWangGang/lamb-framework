package org.lamb.lambframework.core.adapter;

import org.apache.commons.lang.StringUtils;
import org.lamb.lambframework.core.adapter.response.JsonResponser;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.EventException;
import org.apache.log4j.Logger;
import org.lamb.lambframework.core.listener.ErrorMessageListener;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@RestController
@ControllerAdvice
public class GlobalExceptionAdapter {

    private Logger logger = Logger.getLogger(GlobalExceptionAdapter.class);

    /**
     * 接口调用成功,业务方面出错
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EventException.class)
    @ResponseBody
    public String handleTransferException(EventException e) {

        if(e==null){
            return returnInitErrorMsg();
        }

        Map map = ErrorMessageListener.exception;
        if(map == null){
            return returnInitErrorMsg();
        }

        if(!map.containsKey(e.getCode())){
            return returnInitErrorMsg();
        }

        JsonResponser jsonResponser = new JsonResponser();

        return jsonResponser.setBusinessResponseBody(e.getCode(),map.get(e.getCode()).toString()).process();

    }


    /**
     * bean validation 接口校验出错
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public String handleBindException(BindException e) {


        if(e==null){
            return returnInitErrorMsg();
        }

        if(StringUtils.isBlank(e.getBindingResult().getFieldError().getDefaultMessage())){
            return returnInitErrorMsg();
        }
        Map map = ErrorMessageListener.exception;
        if(map == null){
            return returnInitErrorMsg();
        }


        if(!map.containsKey(e.getBindingResult().getFieldError().getDefaultMessage())){
            return returnInitErrorMsg();
        }

        JsonResponser jsonResponser = new JsonResponser();
        return jsonResponser.
                setServiceResponseBody(e.getBindingResult().getFieldError().getDefaultMessage(),(map.get(e.getBindingResult().getFieldError().getDefaultMessage()).toString())).
                process();

    }

    //统一初始化信息
    public String returnInitErrorMsg(){
        JsonResponser jsonResponser = new JsonResponser();
        return jsonResponser.setServiceResponseBody(ExceptionEnum.E000000000.getCode(),ExceptionEnum.E000000000.getMessage()).process();
    }
}
