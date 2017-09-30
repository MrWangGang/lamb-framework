package org.lamb.lambframework.core.util;

import org.lamb.lambframework.core.exception.BusinessException;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class GetUtil {
    public static <T>T get(Map map, String value){
        if(StringUtils.isBlank(value)){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_TARGET_NAME_NULL);
        }

        if(map == null){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_MAP_NULL_POINT);
        }

        if(map.isEmpty()){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_MAP_NULL_POINT);
        }

        Object obj = map.get(value);
        if(obj == null){
            throw new BusinessException(ExceptionEnum.JXL_API_INVOKE_ERROR);
        }
        T t = (T)obj ;
        if(t == null){
            throw new BusinessException(ExceptionEnum.JXL_API_INVOKE_ERROR);
        }

        return t;
    }
}
