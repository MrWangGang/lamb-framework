package org.lamb.framework.core.supper;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @description: redis操作基类
 * @author: Mr.WangGang
 * @create: 2018-10-15 下午 12:08
 **/
@Component
public abstract class LambOperation<KEY,T> {

    @Resource
    private RedisTemplate<KEY,T> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public void set(KEY key,T t,Long timeout){
        redisTemplate.opsForValue().set(key,t,timeout,TimeUnit.MILLISECONDS);
    }

    public T getObject(KEY key){
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(KEY key){
        redisTemplate.delete(key);
    }

    public String getString(KEY key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
