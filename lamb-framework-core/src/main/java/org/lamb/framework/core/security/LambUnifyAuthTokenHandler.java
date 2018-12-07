package org.lamb.framework.core.security;

import org.apache.commons.lang3.StringUtils;
import org.lamb.framework.common.exception.EventException;
import org.lamb.framework.common.util.MD5Util;
import org.lamb.framework.core.contract.Contract;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.lamb.framework.common.enumeration.ExceptionEnum.EA00000002;
import static org.lamb.framework.common.enumeration.ExceptionEnum.EA00000005;
import static org.lamb.framework.common.enumeration.ExceptionEnum.EA00000006;
import static org.lamb.framework.core.contract.Contract.LAMB_AUTH_TOKEN_SALT;

/**
 * @description: 统一用户
 * @author: Mr.WangGang
 * @create: 2018-11-16 下午 3:49
 **/
@Component
public class LambUnifyAuthTokenHandler {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean hasKey(String key){
        Boolean flag = stringRedisTemplate.hasKey(key);
        if(flag == null){
            return false;
        }
        return flag;
    }

    public String getPrincipalByToken(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public String setPrincipalByToken(String principalSign,String principal,String principalModel){
        if(StringUtils.isBlank(principalSign)){
            throw new EventException(EA00000005);
        }
        if(StringUtils.isBlank(principal)){
            throw new EventException(EA00000002);
        }
        if(principalModel == null){
            throw new EventException(EA00000006);
        }
        if(StringUtils.isBlank(principalModel)){
            throw new EventException(EA00000006);
        }

        String key = Contract.LAMB_TOKEN_KEY+principalModel+"."+ MD5Util.hash(principalSign+"_"+principalModel+"_"+LAMB_AUTH_TOKEN_SALT);
        stringRedisTemplate.opsForValue().set(key,principal,Contract.LAMB_TOKEN_TIME.longValue(), TimeUnit.MILLISECONDS);;
        return key;
    }
}
