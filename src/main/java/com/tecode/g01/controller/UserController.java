package com.tecode.g01.controller;

import com.tecode.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * 1.在接收html请求的java文件的类上添加@Controller
 */
@Controller
public class UserController {
    /**
     *需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
   // @Autowired
    //private G04CommentDao commentdao;


    @ResponseBody
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Map<String,Object> login(User user, HttpSession session){
        /**
         *1.验证参数的合法性
         * 2.调用业务逻辑层处理业务，并获得返回值
         * 3.判断是否登录成功
         *          如果登录成功 调用SessionUtil.setLoginUser(session,user);
         * 4.讲返回结果封装成map集合
         * 5.返回map
         *
         *
         *
         */


        return null;
    }

}
