package com.tecode.G06.controller;

import com.tecode.G06.service.G06AssignTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/23.
 */
@Controller
public class G06AssignTaskController {
    @Autowired
    private G06AssignTaskService taskService;


    /**
     *
     *taskId://任务id
     *cusId://⽤用户id
     *handlerId:// 下一个办理用户id
     *isAssign://是否是交办，true-交办|false-转办
     */
    @ResponseBody
    @RequestMapping(value = "/assign-task", method = RequestMethod.POST)
    public Map<String,Object> assign(String taskId,String cusId,String handlerId,String isAssign){
        Map<String, Object> map = new HashMap<>();

        if(isAssign.equals("false")) {
            map.put("success", false);
            map.put("msg" , "请提交给转办");
            return map;
        }
        map = taskService.assignTask(taskId, cusId, handlerId);
        return map;
    }

}
