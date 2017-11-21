package com.panshan.SpringBootTest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by panshan on 2017/7/11.
 */
public class RedisTemplateUtil {
    private static RedisTemplate<String, Object> redisTemplate ;

    public  void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisTemplateUtil.redisTemplate = redisTemplate;
    }
    /*------------string-------------*/
    public static Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public static void set(String key, String object){
        if (object!=null){
            redisTemplate.opsForValue().set(key, object);
        }
        return;
    }
    /**
     *
     * @param key
     * @param value
     * @param time 设置过期时间（单位秒）
     */
    public static void setTime( String key, Object value, int time){
        if (value!=null&&time>0)
            redisTemplate.opsForValue().set(key, value, time);
    }

}
