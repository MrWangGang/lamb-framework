package org.lamb.framework.statemachine.handler;

import org.lamb.framework.statemachine.function.LambStateMachineCurrentStatus;

/**
 * @description: 暴露接口
 * @author: Mr.WangGang
 * @create: 2018-12-07 上午 11:59
 **/
public interface LambStateMachineExecute {
    public <T>Object execute(LambStateMachineCurrentStatus statusProcess, String event, T data);
}
