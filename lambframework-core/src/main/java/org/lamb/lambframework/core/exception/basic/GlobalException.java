package org.lamb.lambframework.core.exception.basic;

import org.lamb.lambframework.core.enumeration.ExceptionEnum;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public abstract class GlobalException extends RuntimeException{

    private String code;

    private String message;

    public GlobalException(ExceptionEnum error){
            this.code = error.getCode();
            this.message = error.getMessage();
    }

    public GlobalException(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
