package com.tecode.controller;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.service.TaskService;
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
 * Created by Administrator on 2018/10/18.
 */
@Controller
@RequestMapping(value = "interface/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    /**
     * 发布任务


     * @param task
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/issueTak",method = RequestMethod.POST)
    public Map<String,Boolean> issueTask(@RequestBody Task task, HttpSession session){

            //获得当前登录人对象
            User user =(User)session.getAttribute("user");
        //新增协同
        boolean success = taskService.issueTask(user, task);

        //封装返回值
        Map<String,Boolean> map = new HashMap<>();
        map.put("success",success);
        map.put("data",success);


        return map;
    }
}
