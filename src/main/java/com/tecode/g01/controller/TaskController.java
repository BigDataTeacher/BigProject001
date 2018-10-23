package com.tecode.g01.controller;

import com.tecode.bean.Task;
import com.tecode.g01.service.TaskService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 1.在接收html请求的java文件的类上添加@Controller
 */
@Controller
public class TaskController {

    /**
     *需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    private TaskService taskService;

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
    @RequestMapping(value = "/taskcomment-detil", method = RequestMethod.POST)
    public Map<String,Object> task_detil(String taskid,String username){
        /**
         *1.验证参数的合法性
         * 2.调用业务逻辑层处理业务，并获得返回值*
         * 4.讲返回结果封装成map集合
         * 5.返回map
         */

        Map<String,Object> map = new HashedMap();
        if(taskid ==null && username == null){
            map.put("success",false);
            map.put("msg","任务id或用户名为空");
        }else {
            Task taskdetail = taskService.getTaskdetail(taskid,username);
            if(taskdetail != null){
                map.put("success",true);
                map.put("data",taskdetail);
            }else{
                map.put("success",false);
                map.put("msg","任务id或用户名不存在");
            }

        }
        return map;
    }

}
