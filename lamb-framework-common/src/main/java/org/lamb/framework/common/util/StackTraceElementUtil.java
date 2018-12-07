package org.lamb.framework.common.util;

import org.lamb.framework.common.exception.basic.GlobalException;

import java.util.Arrays;

/**
 * @description: 异常栈
 * @author: Mr.WangGang
 * @create: 2018-11-22 下午 5:45
 **/
public class StackTraceElementUtil {
    public static Boolean checkGlobalExcetionOStackTrace(StackTraceElement[] es){
        return Arrays.stream(es).anyMatch(e -> GlobalException.class.getName().equals(e.getClassName()));
    }
}
