package com.panshan.SpringBootTest.controller;

import com.panshan.SpringBootTest.entity.User;
import com.panshan.SpringBootTest.mapper.UserMapper;
import com.panshan.SpringBootTest.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by panshan on 2017/6/26.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public UserService userService;

    @Resource(name="primaryJdbcTemplate")
    public JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ModelAndView getAll(){
        Logger.getLogger("UserController").debug("success into getall()");
        ModelAndView result = new ModelAndView("index");
        try{
            List<User> userList = userMapper.selectAll();
            result.addObject("userList",userList);
        }catch (Exception e){
            e.printStackTrace();
            Logger.getLogger("UserController").debug(e.toString());
        }
        return result;
    }

    @RequestMapping(value = "/getAll2", method = RequestMethod.GET)
    public ModelAndView getAll2(){
        Logger.getLogger("UserController").debug("success into getall()");
        ModelAndView result = new ModelAndView("index");
        try{
            List<User> userList = userService.findAll();
            result.addObject("userList",userList);
        }catch (Exception e){
            e.printStackTrace();
            Logger.getLogger("UserController").debug(e.toString());
        }
        return result;
    }

    @RequestMapping(value="/view/{id}",method = RequestMethod.GET)
    public ModelAndView getUserById(@PathVariable int id){

        ModelAndView result = new ModelAndView("view");
        User user = userMapper.selectByUserId(id);
        result.addObject("user", user);
        result.setStatus(HttpStatus.OK);
        return result;
    }

    @RequestMapping(value="/view2/{id}",method = RequestMethod.GET)
    public ModelAndView getUserById2(@PathVariable int id){

        ModelAndView result = new ModelAndView("view");
        User user = userService.findUserById(id);
        result.addObject("user", user);
        result.setStatus(HttpStatus.OK);
        return result;
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ModelAndView save(User user){
        userMapper.insert(user);
        ModelAndView result = new ModelAndView("view");
        String msg = user.getId() == null ? "新增成功!" : "更新成功!";
        result.setStatus(HttpStatus.OK);
        result.addObject("user",user);
        result.addObject("msg",msg);
        return result;
    }
    @RequestMapping(value="/save2", method = RequestMethod.POST)
    public ModelAndView save2(User user){
        int re = userService.insertUser(user);
        ModelAndView result = new ModelAndView("view");
        String msg = user.getId() == null ? "新增成功!" : "更新成功!";
        result.setStatus(HttpStatus.OK);
        result.addObject("user",user);
        result.addObject("msg",msg);
        return result;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
    public ModelAndView delete(@PathVariable int id){
        ModelAndView result = new ModelAndView();
        return result;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        int[] result = userService.batchInsertUser();
        return result.toString();
    }

    @RequestMapping(value = "/testJdbcTemplate", method = RequestMethod.GET)
    public String testJdbcTemplate(){
        List<Map<String,Object>>result = jdbcTemplate.queryForList("select id,userName from `user`");
        return result.toString();
    }

}
