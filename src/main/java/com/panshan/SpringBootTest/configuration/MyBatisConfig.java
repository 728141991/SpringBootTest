package com.panshan.SpringBootTest.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.oracle.webservices.internal.api.message.PropertySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


/**
 * Created by panshan on 2017/6/22.
 */
@Configuration
public class MyBatisConfig {

    /**
     * 如果直接用DataSourceProperties 来创建DataSourceBUilder是用的内嵌数据库。
     */
    @Autowired
    DataSource dataSource;

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() throws Exception{
        return DataSourceBuilder.create().
                type(DruidDataSource.class).build();
    }
    //用@Resource来获取secondaryDataSource
    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().
            type(DruidDataSource.class).build();
    }

    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate getPrimaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate getSecondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }


}
