package com.panshan.SpringBootTest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;

/**
 * Created by panshan on 2017/6/21.
 */
//相当于@Request和@Controller
@RestController
//Spring boot建议只有一个带有该注解的类
@EnableAutoConfiguration
//启动@Schedule注解的job
@EnableScheduling
@ComponentScan(basePackages = "com.panshan.SpringBootTest")
//相当于@Configuration,@EnableAutoConfiguration,@ComponentScan.
@SpringBootApplication
@MapperScan(basePackages = "com.panshan.SpringBootTest.mapper")
public class Application extends WebMvcConfigurerAdapter{

    @RequestMapping("/home")
    String home(){
        return "HELLO world";
    }

    @RequestMapping("/hehe")
    String hehe(){
        return "现在时间"+(new Date()).toString();
    }

    public static void main(String args[]){
        //启动Spring Boot项目
        SpringApplication.run(Application.class, args);
    }

}
