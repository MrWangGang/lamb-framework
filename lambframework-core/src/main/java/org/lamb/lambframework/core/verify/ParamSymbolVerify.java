package org.lamb.lambframework.core.verify;

import org.lamb.lambframework.core.annotation.ParamVerify;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.lamb.lambframework.core.exception.BusinessException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by WangGang on 2017/7/10 0010.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public abstract class ParamSymbolVerify {

    public Map<String,Object> superMap(){
        LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
        Class<?> classType = this.getClass();
        for(; classType != Object.class ; classType = classType.getSuperclass()) {
            process(classType, new ProcessFoundation() {
                @Override
                public void process(ParamVerify annotation, Object value) {
                    map.put(annotation.value(),value);
                }
            });
        }
        return map;
    }

    public Map<String,Object> extendsMap(){
        LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
        process(this.getClass(), new ProcessFoundation() {
            @Override
            public void process(ParamVerify annotation, Object value) {
                map.put(annotation.value(),value);
            }
        });
        return map;
    }



    public void verify(){
        process(this.getClass(), new ProcessFoundation() {
            @Override
            public void process(ParamVerify annotation, Object value) {
                isIgnore(annotation.value(),annotation.required(),value);
            }
        });
    }


    private void process(Class<?> classType,ProcessFoundation pf){
        //取父类中的成员变量信息
        Field[] fields = classType.getDeclaredFields();
        //定义计数器，用来判断是否为开头元素
        for (Field field : fields) {

            //是否有此注解的成员变量
            boolean flag = field.isAnnotationPresent(ParamVerify.class);
            if(flag){

                ParamVerify annotation = field.getDeclaredAnnotation(ParamVerify.class);
                Object obj = null;
                try {
                    field.setAccessible(true);
                    obj = field.get(this);
                } catch (IllegalAccessException e) {
                    throw new BusinessException(ExceptionEnum.ILLEGAL_ACCESS);
                }finally {
                    field.setAccessible(false);
                }

                pf.process(annotation,obj);
            }
        }

    }

    private void isIgnore(String value,boolean required,Object obj){

        if(StringUtils.isBlank(value)){
            //如果名称为空
            throw new BusinessException(ExceptionEnum.PARAM_NAME_NOT_NULL);
        }
        if(required){//如果是必须填写的
            //判断值是否为空
            if(obj == null){
                throw new BusinessException(ExceptionEnum.PARAM_NOT_NULL);
            }
        }
    }
}
