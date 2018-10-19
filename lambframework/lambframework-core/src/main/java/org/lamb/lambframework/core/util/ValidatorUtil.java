package org.lamb.lambframework.core.util;

import org.lamb.lambframework.core.exception.EventException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.lamb.lambframework.core.enumeration.ExceptionEnum.EI00000000;

/**
 * @description: 校验工具类
 * @author: Mr.WangGang
 * @create: 2018-10-19 下午 6:15
 **/
public class ValidatorUtil {
    public static  <T>void validate(T t){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        validator.validate(t);
        Set set = validator.validate(t);
        if(!set.isEmpty()){
            throw new EventException(EI00000000);
        }

    }


    public static  <T>void validate(T t,Class...clazz){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        validator.validate(t);
        Set set = validator.validate(t,clazz);
        if(!set.isEmpty()){
            throw new EventException(EI00000000);
        }
    }
}
