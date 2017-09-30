package org.lamb.lambframework.core.exception;

import org.lamb.lambframework.core.exception.basic.GlobalBasicException;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;

/**
 * Created by WangGang on 2017/7/6 0006.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class ServiceException extends GlobalBasicException {
    public ServiceException(ExceptionEnum error) {
        super(error);
    }
}
