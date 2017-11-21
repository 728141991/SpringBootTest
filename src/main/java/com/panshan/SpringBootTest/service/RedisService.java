package com.panshan.SpringBootTest.service;

import com.google.common.base.Preconditions;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by panshan on 2017/7/11.
 */
@Service
public class RedisService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void setKey(String key) {
        try {
            Preconditions.checkNotNull(key);
            redisTemplate.opsForValue().set(key, 1, 120, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isKeyExist(String key) {
        Object result = null;
        if (key != null) {
            try {
                result = redisTemplate.opsForValue().get(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result==null){
            return false;
        }else {
            return true;
        }
    }

}
