package com.tecode.G03.controller;

import com.tecode.G03.service.CreateTaskService;
import com.tecode.bean.Task;
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
public class CreateTaskController {
    /**
     *需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    private CreateTaskService createTaskService;

    /**
     * 创建任务方法
     *
     * 1.其中 @RequestMapping(value = "/userLogin", method = RequestMethod.POST) 表示html页面请求到该方法的URL地址的映射
     *      value = "/create-task" ： html的请求地址
     *      method = RequestMethod.POST：表示请求的方式
     * 2.@RequestBody:表示接收josn类型的参数
     *          注意User对象中的属性名称，必须和html页面传递的参数的名称完全相同，包括大小写。
     *
     * 3.@ResponseBody:：表示把返回的值封装成json进行返回
     */
    @ResponseBody
    @RequestMapping(value = "/create-task", method = RequestMethod.POST)
    public Map<String,Object> login(Task task, HttpSession session){
        /**
         *
         * taskAuthor://任务发起者
         taskTitle://任务标题
         taskDesc://任务描述
         taskEndTime://任务结束时间(格式为：yyyy-MM-dd	HH:mm:ss)--可为空
         beAssignId://办理⼈id
         "taskTag":"通⽤"//任务标签


         *1.验证参数的合法性
         * 2.调用业务逻辑层处理业务，并获得返回值
         * 3.判断是否创建成功
         *          如果创建成功
         * 4.讲返回结果封装成map集合
         * 5.返回map
         *
         *
         *
         */

        try {
            Boolean success = createTaskService.createTask(task);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
