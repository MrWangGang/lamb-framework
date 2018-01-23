package org.lamb.lambframework.core.aspect;

import org.lamb.lambframework.core.annotation.AnalysisFoundation;
import org.lamb.lambframework.core.exception.EventException;
import org.lamb.lambframework.core.annotation.Analysis;
import org.lamb.lambframework.core.enumeration.ExceptionEnum;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
//描述切面类
@Aspect
@Component
public class AnalysisAspect {

    private Logger logger = Logger.getLogger(AnalysisAspect.class);

    /*
     * 定义一个切入点
     */
    @Around(value = "execution(public String *(..)) && @annotation(org.lamb.lambframework.core.annotation.Analysis)")
    public String processAnalysis(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result == null) {
            throw new EventException(ExceptionEnum.EC00000001);
        }
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        boolean flag = targetMethod.isAnnotationPresent(Analysis.class);
        if (flag) {
            Analysis analysis = targetMethod.getAnnotation(Analysis.class);
            Class<? extends AnalysisFoundation> foundation = analysis.clazz();
            if (foundation == null) {
                throw new EventException(ExceptionEnum.EC00000000);
            }
            AnalysisFoundation analysisObj = foundation.newInstance();
            result = analysisObj.analysis(result.toString());
        }
        return result.toString();
    }
}
