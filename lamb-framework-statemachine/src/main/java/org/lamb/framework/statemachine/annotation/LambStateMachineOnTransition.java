package org.lamb.framework.statemachine.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LambStateMachineOnTransition {
    String source() default "";

    String target() default "";

    String event();

    String error();

    String initial() default "";

}
