package org.lamb.framework.statemachine.function;

/**
 * @description: 执行流程
 * @author: Mr.WangGang
 * @create: 2018-11-21 下午 3:09
 **/
public interface LambStateMachineProcess<T> {
    public <T>T process(String targetState);
}
