package org.lamb.framework.statemachine.container;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * @description: 状态机过度器
 * @author: Mr.WangGang
 * @create: 2018-11-22 下午 3:28
 **/
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LambStateMachineTransition<T> {

    private String source;

    private String target;

    private String event;

    private Optional<T> data;
}
