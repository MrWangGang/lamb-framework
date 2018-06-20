package org.lamb.lambframework.core.util;


import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.EventException;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BeanPlasticityUtill {

    public static <T> T copy(Class<T> t, Object orig) {
        try {
            T result = t.newInstance();
            org.apache.commons.beanutils.BeanUtils.copyProperties(result, orig);
            return result;
        } catch (IllegalAccessException e) {
            throw new EventException(ExceptionEnum.ES00000022);
        } catch (InstantiationException e) {
            throw new EventException(ExceptionEnum.ES00000022);
        } catch (InvocationTargetException e) {
            throw new EventException(ExceptionEnum.ES00000022);
        }
    }

    static {
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                if (value == null) {
                    return null;
                }
                if (!(value instanceof String)) {
                    throw new EventException(ExceptionEnum.ES00000022.getCode(),"类型不匹配String");
                }
                if (StringUtils.isBlank((String) value)) {
                    throw new EventException(ExceptionEnum.ES00000022.getCode(),"要转成date的value不能为空");
                }

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return df.parse((String) value);
                } catch (ParseException e) {
                    throw new EventException(ExceptionEnum.ES00000022.getCode(),"日期转化失败");
                }
            }
        }, java.util.Date.class);
    }
}


