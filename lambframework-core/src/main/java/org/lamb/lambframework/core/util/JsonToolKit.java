package org.lamb.lambframework.core.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.lamb.lambframework.core.exception.BusinessException;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by WangGang on 2017/6/16 0016.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class JsonToolKit {

    private static ObjectMapper mapper = new ObjectMapper();



    public static Map json2Map(String json){
        if (StringUtils.isBlank(json)) {
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_NULL_POINT);
        }
        try {
            Map map = mapper.readValue(json, Map.class);
            if(map == null){
                throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_MAP_NULL_POINT);
            }
            return map;
        } catch (IOException e) {
            throw new BusinessException(ExceptionEnum.IO_ERRO);
        }
    }

    public static String Map2String(Map map){
        if(map==null){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_NULL_POINT);
        }
        if(map.isEmpty()){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_NULL_POINT);
        }
        try {
            return  mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ExceptionEnum.IO_ERRO);
        }
    }

    public static  <T>T  get(Map map,String name){

        if (StringUtils.isBlank(name)) {
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_TARGET_NAME_NULL);
        }
        if(map==null){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_MAP_NULL_POINT);
        }
        if(map.isEmpty()){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_MAP_NULL_POINT);
        }
        T t = (T)map.get(name);
        if(t == null){
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_NULL_POINT);
        }

        if( t instanceof  String){
            if(StringUtils.isBlank(t.toString())){
                throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_NULL_POINT);
            }
            return t;
        }

        if(t instanceof Map){

            if(((Map) t).isEmpty()){
                throw new BusinessException(ExceptionEnum.JSON_CONVERT_DATAPOOR_NULL_POINT);
            }

            return t;
        }

        return t;

    }

    public static String objToJson(Object obj){
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ExceptionEnum.JSON_CONVERT_OBJ_ERROR);
        }
    }
}

