package org.lamb.framework.statemachine.container;

import lombok.Data;
import org.lamb.framework.common.exception.basic.GlobalException;

import java.lang.reflect.Method;

/**
 * @description: 状态机扭转容器
 * @author: Mr.WangGang
 * @create: 2018-11-21 下午 1:12
 **/
@Data
public class LambStateMachineContainer {
    private String source;

    private String target;

    private String event;

    private GlobalException globalException;

    private Method method;

    private Object listener;

    private boolean initial = false;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(this == obj) return true;
        if(obj instanceof LambStateMachineContainer){
            LambStateMachineContainer lambStateMachineContainer =(LambStateMachineContainer)obj;
          if(lambStateMachineContainer.event == lambStateMachineContainer.event) return true; // 只比较event
        }
        return false;
    }

    @Override
    public int hashCode() {
        return event.hashCode();
    }
}
