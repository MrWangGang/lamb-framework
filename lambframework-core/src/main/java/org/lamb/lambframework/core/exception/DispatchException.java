package org.lamb.lambframework.core.exception;

import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.basic.GlobalException;

public class DispatchException extends GlobalException {

    public DispatchException(ExceptionEnum error) {
        super(error);
    }

    public DispatchException(String code, String message) {
        super(code,message);
    }
}
