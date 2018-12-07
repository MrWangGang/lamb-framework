package org.lamb.framework.statemachine.annotation;


import org.lamb.framework.statemachine.handler.LambStateMachineSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(LambStateMachineSelector.class)
public @interface EnableLambStateMachine {

}
