package org.lamb.framework.statemachine.handler;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.lamb.framework.common.exception.EventException;
import org.lamb.framework.common.exception.basic.GlobalException;
import org.lamb.framework.statemachine.annotation.LambStateMachineListener;
import org.lamb.framework.statemachine.annotation.LambStateMachineOnTransition;
import org.lamb.framework.statemachine.container.LambStateMachineContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.lamb.framework.common.enumeration.ExceptionEnum.ES00000030;
import static org.lamb.framework.common.enumeration.ExceptionEnum.ES00000034;

/**
 * @description: 获取LambStateMachineListener注解的bean
 * @author: Mr.WangGang
 * @create: 2018-11-22 下午 1:30
 **/
public class LambStateMachineSelector implements BeanPostProcessor {

    @Resource
    private LambStateMachine lambStateMachine;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Optional<LambStateMachineListener> lambStateMachineListener = Optional.ofNullable(bean.getClass().getDeclaredAnnotation(LambStateMachineListener.class));
        if(lambStateMachineListener.isPresent()){
            Method[] method = bean.getClass().getDeclaredMethods();
            Set<LambStateMachineContainer> containers = Sets.newHashSet();
            Arrays.stream(method).filter(e->Optional.ofNullable(e.getDeclaredAnnotation(LambStateMachineOnTransition.class)).isPresent())
                    .forEach((e)->{
                        Optional<LambStateMachineOnTransition> lambStateMachineTransition = Optional.ofNullable(e.getDeclaredAnnotation(LambStateMachineOnTransition.class));
                        if(lambStateMachineTransition.isPresent()){
                            LambStateMachineOnTransition annotation = lambStateMachineTransition.get();
                            String initial = annotation.initial();
                            String source = annotation.source();
                            String target = annotation.target();
                            String event = annotation.event();
                            String error = annotation.error();
                            if(StringUtils.isNotBlank(initial)){
                                source = initial;
                                target = initial;
                            }
                            if(StringUtils.isBlank(source)) throw new EventException(ES00000030);
                            if(StringUtils.isBlank(target)) throw new EventException(ES00000030);
                            if(StringUtils.isBlank(event))  throw new EventException(ES00000030);
                            LambStateMachineContainer container = new LambStateMachineContainer();
                            container.setSource(source);
                            container.setTarget(target);
                            container.setEvent(event);
                            container.setGlobalException(StringUtils.isBlank(error)?new EventException(ES00000034):new GlobalException(ES00000034.getCode(),error));
                            container.setMethod(e);
                            container.setListener(bean);
                            if(StringUtils.isNotBlank(initial)){
                                container.setInitial(true);
                            }
                            containers.add(container);
                            }
                    });
            if(containers.size()!=0){
                lambStateMachine.load(containers);
            }
        }
        return bean;
    }
}
