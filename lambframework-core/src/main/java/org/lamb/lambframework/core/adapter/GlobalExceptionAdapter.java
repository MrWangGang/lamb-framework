package org.lamb.lambframework.core.adapter;

import org.apache.log4j.Logger;
import org.lamb.lambframework.core.templete.ResponseTemplete;
import org.lamb.lambframework.core.exception.EventException;
import org.lamb.lambframework.core.exception.basic.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@RestControllerAdvice
public class GlobalExceptionAdapter {

    private Logger logger = Logger.getLogger(GlobalExceptionAdapter.class);


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResponseTemplete handleTransferException(EventException e) {

        ResponseTemplete responseTemplete = new ResponseTemplete();
        responseTemplete.setService_code(e.getCode());
        responseTemplete.setService_message(e.getMessage());

        return responseTemplete;

    }
}
