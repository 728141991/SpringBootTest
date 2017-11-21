package com.panshan.SpringBootTest;

import com.panshan.SpringBootTest.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by panshan on 2017/7/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTemplateTest {
    @Autowired
    RedisService redisService;

    @Test
    public void RedisTest(){
        redisService.setKey("abcdefg");
        redisService.setKey("123456");
        boolean r1 = redisService.isKeyExist("abcdefg");
        boolean r2 = redisService.isKeyExist("1234");
        System.out.println("r1 = " + r1);
        assertEquals(r1, true);
        System.out.println("r2 = " + r2);
        assertEquals(r2, false);
    }

}
