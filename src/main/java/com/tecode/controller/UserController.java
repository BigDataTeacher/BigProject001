package com.tecode.controller;

import com.tecode.bean.User;
import com.tecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * 1.在接收html请求的java文件的类上添加@Controller
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录方法
     *
     * 1.其中 @RequestMapping(value = "/userLogin", method = RequestMethod.POST) 表示html页面请求到该方法的URL地址的映射
     *      value = "/userLogin" ： html的请求地址
     *      method = RequestMethod.POST：表示请求的方式
     * 2.@RequestBody:表示接收josn类型的参数
     *          注意User对象中的属性名称，必须和html页面传递的参数的名称完全相同，包括大小写。
     *
     * 3.@ResponseBody:：表示把返回的值封装成json进行返回
     */
    @ResponseBody
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody User user, HttpSession session){
        //调用登录的业务处理方法
        User loginUser = userService.getUseLogin(user);


        Map<String,Object> map = new HashMap<>();
        //添加标记状态，默认为登录失败
        map.put("success",false);

        if(loginUser != null){
            //添加状态
            map.put("success",true);
            //添加查询成功的用户信息返回
            map.put("data",loginUser);


            //把登录成功的用户存放起来 已被后面使用。
            session.setAttribute("user",loginUser);
        }
        //返回封装后的返回结果。
        return map;
    }

}
