package org.lamb.lambframework.core.adapter;

import org.lamb.lambframework.core.adapter.response.JsonResponser;
import org.lamb.lambframework.core.exception.DispatchException;
import org.lamb.lambframework.core.exception.EventException;
import org.apache.log4j.Logger;
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

    private Logger logger = Logger.getLogger(GlobalExceptionAdapter.class);

    /**
     * 接口请求错误
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DispatchException.class)
    @ResponseBody
    public String handleRequestException(DispatchException e) {

        JsonResponser jsonResponser = new JsonResponser();

        String result = null;
        if(e==null){
            result =  jsonResponser.process();
        }else{
            result = jsonResponser.setServiceResponseBody(e.getCode(),e.getMessage()).process();
        }
        log(result);
        return result;
    }

    /**
     * 接口调用成功,业务方面出错
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EventException.class)
    @ResponseBody
    public String handleTransferException(EventException e) {

        JsonResponser jsonResponser = new JsonResponser();

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
