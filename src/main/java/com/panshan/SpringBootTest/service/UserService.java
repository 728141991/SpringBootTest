package com.panshan.SpringBootTest.service;

import com.panshan.SpringBootTest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by panshan on 2017/7/3.
 */
//写业务逻辑，一些增删查改
@Service
public class UserService {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    public int insertUser(User user){
        String sql = "insert into user (userName, password, qq) values (?,?,?)";
        int count = jdbcTemplate.update(sql, new Object[]{user.getUsername(),
                user.getPassword(),user.getQq()});
        System.out.println("insertUser : " + count);
        return count;
    }

    public int[] batchInsertUser(){
        String sql = "insert into user (userName, password, qq) values (?,?,?)";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        batchArgs.add(new Object[]{"user01","11","11"});
        batchArgs.add(new Object[]{"user02","11","11"});
        batchArgs.add(new Object[]{"user03","11","11"});
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public void updateUser(){
        String sql = "update user set userName=?,password=?, qq=? where id=?";
        jdbcTemplate.update(sql, new Object[]{"user03","11","11"});
    }

    public void deleteUserById(int id){
        String sql = "delete from user where id=?";
        int result = jdbcTemplate.update(sql,id);
        System.out.println("deleteUserById the result is :" + result);
    }
    //1、使用BeanProperytRowMapper要求sql数据查询出来的列和实体属性需要一一对应。
    // 如果数据中列明和属性名不一致，在sql语句中需要用as重新取一个别名
    //2、使用JdbcTemplate对象不能获取关联对象
    public  List<User> findAll(){
        String sql = "select id,userName as username,password,qq from user ";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        List<User> userList  = jdbcTemplate.query(sql, rowMapper);
        for (User user :userList){
            System.out.println(user);
        }
        return userList;
    }

    public User findUserById(int id){
        String sql = "select id, userName as username, password, qq from user" +
                "where id = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        User user = jdbcTemplate.queryForObject(sql, rowMapper);
        System.out.println(user);
        return user;
    }
}

