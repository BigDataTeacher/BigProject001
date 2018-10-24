package com.tecode.g01.controller;

import com.tecode.g01.service.CopyTaskService;
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
    private CopyTaskService taskService;

    /**
     *
     * @param taskId        r任务ID
     * @param userName      登录人
     * @param memberId      要转送的人的ID
     * @return
     * @throws Exception
     */

    @ResponseBody
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    public Map<String,Object> insert(String taskId,String userName,String memberId) throws Exception {
        Map<String,Object> map=new HashMap<>();
        if (taskId!=null&&userName!=null&&memberId!=null){
            boolean handler = taskService.isHandler(taskId, userName);
            if(handler){
                boolean b = taskService.toNext(taskId, memberId, userName);
                if(b){
                    map.put("success",true);
                    map.put("msg","抄送成功");
                }else{
                    map.put("success",false);
                    map.put("msg","抄送失败");
                }
                return map;
            }
            map.put("success",false);
            map.put("msg","不是当前处理人，不能处理");
            return map;
        }
        map.put("success",false);
        map.put("msg","信息不能为空");

        return map;

    }











}
