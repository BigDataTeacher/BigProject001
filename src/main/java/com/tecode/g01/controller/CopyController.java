package com.tecode.g01.controller;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.g01.service.TaskCopyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
public class CopyController {
    private TaskCopyService taskService;



    @ResponseBody
    @RequestMapping(value = "/TaskCopy", method = RequestMethod.POST)
    public Map<String,Object> insert(User user,Task task) throws Exception {

        boolean flag = false;
        String userid = user.getUsername();
        String taskid = task.getTaskId();

        boolean b = taskService.insertAndAdd(userid, taskid);

        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        //参数合法性判断

        if(b != flag){
            map.put("success",true);
        }
        return map;
    }











}
