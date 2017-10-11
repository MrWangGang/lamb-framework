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

        if(ErrorMessageListener.exception == null){
            return returnInitErrorMsg();
        }

        if(e==null){
            return returnInitErrorMsg();
        }

        if(!ErrorMessageListener.exception.containsKey(e.getCode())){
            return returnInitErrorMsg();
        }

        if(StringUtils.isBlank(e.getCode())){
            return returnInitErrorMsg();
        }

        JsonResponser jsonResponser = new JsonResponser();

        return jsonResponser.setBusinessResponseBody(e.getCode(),cast(e.getCode())).process();

    }


    /**
     * bean validation 接口校验出错
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public String handleBindException(BindException e) {
        if(ErrorMessageListener.exception == null){
            return returnInitErrorMsg();
        }

        if(e==null){
            return returnInitErrorMsg();
        }

        if(e.getBindingResult() == null){
            return returnInitErrorMsg();
        }

        if(e.getBindingResult().getFieldError() == null){
            return returnInitErrorMsg();
        }

        if(StringUtils.isBlank(e.getBindingResult().getFieldError().getDefaultMessage())){
            return returnInitErrorMsg();
        }

        if(!ErrorMessageListener.exception.containsKey(e.getBindingResult().getFieldError().getDefaultMessage())){
            return returnInitErrorMsg();
        }

        JsonResponser jsonResponser = new JsonResponser();
        return jsonResponser.
                setServiceResponseBody(e.getBindingResult().getFieldError().getDefaultMessage(),cast(e.getBindingResult().getFieldError().getDefaultMessage())).
                process();

    }

    //统一初始化信息
    public String returnInitErrorMsg(){

        JsonResponser jsonResponser = new JsonResponser();

        String code = ExceptionEnum.E000000000.getCode();;

        if(StringUtils.isBlank(code)){
             code = "E000000000";
        }

        return jsonResponser.setServiceResponseBody(code,cast(code)).process();

    }

    //转换空的msg 设置为初始值
    public String cast(String code){

        String msg = null;

        if(ErrorMessageListener.exception == null){
            msg = "未知错误";
            return msg;
        }

        if (ErrorMessageListener.exception.get(code) == null){
            msg = "未知错误";
            return msg;
        }

        if(StringUtils.isBlank(ErrorMessageListener.exception.get(code).toString())){
            msg = "未知错误";
            return msg;
        }

        return ErrorMessageListener.exception.get(code).toString();
    }


}
