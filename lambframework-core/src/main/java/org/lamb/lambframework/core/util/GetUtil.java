package org.lamb.lambframework.core.util;

import org.lamb.lambframework.core.exception.EventException;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class GetUtil {
    public static <T>T get(Map map, String value){
        if(StringUtils.isBlank(value)){
            throw new EventException(ExceptionEnum.ES00000002);
        }

        if(map == null){
            throw new EventException(ExceptionEnum.ES00000001);
        }

        if(map.isEmpty()){
            throw new EventException(ExceptionEnum.ES00000001);
        }

        Object obj = map.get(value);
        if(obj == null){
            throw new EventException(ExceptionEnum.ES00000003);
        }
        T t = (T)obj ;
        if(t == null){
            throw new EventException(ExceptionEnum.ES00000003);
        }

        return t;
    }
}
