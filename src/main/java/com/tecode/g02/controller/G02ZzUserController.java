package com.tecode.g02.controller;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.g02.service.G02ZzReplyService;
import com.tecode.g02.service.impl.G02ZzReplyServiceImpl;
import com.tecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class G02ZzUserController {
    /**
     *需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
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
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    public Map<String,Object> login(Task task, String cusId, HttpSession session){
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

        String taskId = task.getTaskId();
        String idStack = task.getHandlerStack();
        String[] split = idStack.split(",");
        String handlerId = split[split.length-1];


        Map<String,Object> map = new HashMap<>();
        if(taskId!=null || handlerId.equals(cusId)){
            G02ZzReplyService replyService = new G02ZzReplyServiceImpl();

            map.put("success",true);
            map.put("msg","回复成功！");
        }else{
            map.put("success",false);
            map.put("msg","");
        }

        return map;
    }


}
