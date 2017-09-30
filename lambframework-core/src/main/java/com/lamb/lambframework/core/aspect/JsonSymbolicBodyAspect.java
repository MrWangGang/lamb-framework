package com.lamb.lambframework.core.aspect;

import com.lamb.lambframework.core.adapter.response.JsonResponser;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by WangGang on 2017/7/5 0005.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
//描述切面类
@Aspect
@Component
public class JsonSymbolicBodyAspect {

    private Logger logger = Logger.getLogger(JsonSymbolicBodyAspect.class);

    @Autowired
    private JsonResponser jsonResponser;

    /*
        * 定义一个切入点
        */
    @Around(value = "execution(public String *(..)) && @annotation(com.lamb.lambframework.core.annotation.JsonSymbolicBody)")
    public String processJsonReturnValue(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if(result!=null){
            //String str = JsonToolKit.objToJson(result.toString());
            jsonResponser.setBusinessData(result.toString());
        }
        String jsonResult = jsonResponser.process();
        logger.info(jsonResult);
        return jsonResult;
    }


}
