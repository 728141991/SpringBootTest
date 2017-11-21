package com.panshan.SpringBootTest.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by panshan on 2017/7/3.
 */
@Component
public class SampleJob {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    //每20s调度一次。
    @Scheduled(cron = "*/20 * * * * ?")
    public void refresh(){
        System.out.println("success do sampleJob at" + System.currentTimeMillis());
    }
}
