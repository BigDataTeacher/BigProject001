package com.tecode.G06.controller;

import com.tecode.G06.service.G06AssignTaskService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Administrator on 2018/10/23.
 */
@Controller
public class G06AssignTaskController {
    @Autowired
    private G06AssignTaskService taskService;
    private static Map<String,Object> map = new HashedMap();
    static {
        map.put("success",false);
        map.put("msg",null);}
    @ResponseBody
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    /**
     *
     *taskId://任务id
     *cusId://⽤户id
     *handlerId://下⼀个办理⼈id
     *isAssign://是否是交办，true-交办|false-转办
     */
    public Map<String,Object> assign(String taskId,String cusId,String handlerId,boolean isAssign){
        if(isAssign==true){

        }
        return null;
    }

}
