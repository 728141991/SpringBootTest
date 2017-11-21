package com.panshan.SpringBootTest.mapper;

import com.panshan.SpringBootTest.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by panshan on 2017/6/22.
 * 这是用的mybatis。
 */
public interface UserMapper {
    /**
     *  insert into user by user entity
     * @param user
     * @return
     */
    @Insert({
            "insert into user ( userName, password, qq)" +
                    "values (#{user.username},#{user.password},#{user.qq})"
    })
    @Options(useGeneratedKeys = true)
    int insert(User user);

    /**
     *
     * @param username
     * @return
     */
    @Results(id = "userResult", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "userName", id = true),
            @Result(property = "password", column = "password", id = true),
            @Result(property = "qq", column = "qq", id = true)
    })
    @Select({
            "select id,userName,password,qq from user where userName=#{username}"
    })
    User selectByUserName(String username);

    @ResultMap("userResult")
    @Select({
            "select id,userName,password,qq from user where id=#{userId}"
    })
    User selectByUserId(int userId);

    @ResultMap("userResult")
    @Select({
            "select id,userName,password,qq from user"
    })
    List<User> selectAll();
}
