package com.lamb.lambframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by WangGang on 2017/7/5 0005.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonSymbolicBody {
}
