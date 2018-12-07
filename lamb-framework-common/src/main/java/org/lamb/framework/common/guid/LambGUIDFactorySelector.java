package org.lamb.framework.common.guid;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @description: GUID生成
 * @author: Mr.WangGang
 * @create: 2018-12-03 下午 3:44
 **/
@Data
public class LambGUIDFactorySelector {
    @Value("${lamb.datacenterId}")
    private Long datacenterId;
    @Value("${lamb.machineId}")
    private Long machineId;

    @Bean
    public LambGUIDFactory set(){
        return new LambGUIDFactory(datacenterId,machineId);
    }
}
