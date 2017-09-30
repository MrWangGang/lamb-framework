package com.lamb.lambframework.core.adapter;

import com.lamb.lambframework.core.adapter.response.JsonResponser;
import com.lamb.lambframework.core.exception.BusinessException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@RestController
@ControllerAdvice
public class GlobalExceptionAdapter {

    @Autowired
    private JsonResponser jsonResponser;

    private Logger logger = Logger.getLogger(GlobalExceptionAdapter.class);
    /**
     * 接口调用成功,业务出错
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public String handleException(BusinessException e) {
        String result = null;
        if(e==null){
            result =  jsonResponser.process();
        }else{
            result = jsonResponser.setBusinessResponseBody(e.getCode(),e.getMessage()).process();
        }
        log(result);
        return result;
    }

    public void log(String result) {

        logger.error("result:" + result);
    }

}
