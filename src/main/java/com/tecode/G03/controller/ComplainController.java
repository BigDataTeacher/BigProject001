package com.tecode.G03.controller;

import com.tecode.G03.service.ComplainService;
import com.tecode.exception.BaseException;
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
public class ComplainController {
    /**
     * 需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */

    @Autowired
    private ComplainService complainService;

    /**
     * 用户登录方法
     * <p>
     * <p>
     * <p>
     * 1.其中 @RequestMapping(value = "/userLogin", method = RequestMethod.POST) 表示html页面请求到该方法的URL地址的映射
     * value = "/userLogin" ： html的请求地址
     * method = RequestMethod.POST：表示请求的方式
     * 2.@RequestBody:表示接收josn类型的参数
     * 注意User对象中的属性名称，必须和html页面传递的参数的名称完全相同，包括大小写。
     * <p>
     * 3.@ResponseBody:：表示把返回的值封装成json进行返回
     */


    @ResponseBody
    @RequestMapping(value = "/assign-task", method = RequestMethod.POST)
    public Map<String, Object> login(String taskId, String cusId, String handlerId, boolean isAssign, HttpSession session) {
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
         *
         */
        Map<String, Object> map = new HashMap<>();
        if (cusId == null) {
            map.put("success", false);
            map.put("msg", "输入用户名不能为空...");
        } else if (taskId == null) {
            map.put("success", false);
            map.put("msg", "输入任务Id不能为空...");
        } else if (handlerId == null) {
            map.put("success", false);
            map.put("msg", "输入接办任务人员Id不能为空...");
        } else if (isAssign) {
            map.put("success", false);
            map.put("msg", "请选择转办业务项...");
        } else if(cusId.equals(handlerId)){
            map.put("success", false);
            map.put("msg", "任务不能转办给自己...");
        }
        else {
            try {
                //调用转办任务方法
                complainService.complainTask(cusId, taskId, handlerId);
                map.put("success", true);
                map.put("data", true);
            } catch (BaseException e) {
                map.put("success", false);
                map.put("msg", e.getMessage());
            }
        }
        return map;
    }

}
