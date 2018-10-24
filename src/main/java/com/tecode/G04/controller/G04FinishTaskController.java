package com.tecode.G04.controller;

import com.tecode.G04.service.G04TaskService;
import com.tecode.bean.Task;
import com.tecode.exception.BaseException;
import com.tecode.util.hbase.table.SessionUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 1.在接收html请求的java文件的类上添加@Controller
 */
@Controller
public class G04FinishTaskController {
    /**
     * 需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    private G04TaskService g04TaskService;

    /**
     * 用户登录方法
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
    @RequestMapping(value = "/close-task", method = RequestMethod.POST)
    public Map<String, Object> finishTask(String taskId, String username,HttpSession session) {
        /**
         *1.验证参数的合法性
         * 2.调用业务逻辑层处理业务，并获得返回值
         * 3.判断是否登录成功
         *          如果登录成功 调用SessionUtil.setLoginUser(session,user);
         * 4.讲返回结果封装成map集合
         * 5.返回map
         *
         *
         */

        //得到任务ID
       // String taskId = task.getTaskId();

       // System.out.println(task);
        //创建集合来返回
        Map<String, Object> taskMap = new HashMap<String, Object>();

        if (taskId == null) {
            taskMap.put("success", false);
            taskMap.put("msg", "没有任务");
            return taskMap;
        }
        try {
            Boolean b = g04TaskService.modifyTaskState(taskId, username);

            if (b) {
                taskMap.put("success", true);
                taskMap.put("data", true);
            } else {
                taskMap.put("success", false);
                taskMap.put("msg", "任务失败");
            }
        } catch (BaseException e) {
            taskMap.put("success", false);
            taskMap.put("msg",  e.getMessage());

        }


        return taskMap;
    }

}
