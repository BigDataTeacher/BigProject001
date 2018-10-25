package com.tecode.g01.controller;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.enumBean.CommentatorType;
import com.tecode.g01.service.TaskCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CopyController {


    @Autowired
    private TaskCopyService taskService;
    private String userid = null;
    private String taskid = null;


    @ResponseBody
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public Map<String,Object> insert(User user,Task task) throws Exception {


        boolean flag = false;
        userid = user.getUsername();
        taskid = task.getTaskId();

        System.out.println(userid+":"+taskid);

        boolean b = taskService.insertAndAdd(userid, taskid);

        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        //参数合法性判断

        System.out.println("123");
        if(userid != null && taskid != null) {
            if (b != flag) {
                map.put("success", true);
                System.out.println("111");

            }
        }
        return map;

    }











}
