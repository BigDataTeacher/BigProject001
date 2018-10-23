package com.tecode.G06.service.impl;

import com.tecode.G06.dao.G06AssignTaskDao;
import com.tecode.G06.service.G06AssignTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 版本：2018/10/23 V1.0
 * Created by Administrator on 2018/10/23.
 */
public class G06AssignTaskServiceImpl implements G06AssignTaskService {
    @Autowired
    private G06AssignTaskDao dao;
    @Override
    public Map<String, Object> assignTask(String taskId, String cusId, String handlerId) {
        Map<String, Object> map = new HashMap();
        if(dao.assign(taskId, cusId, handlerId)) {
            map.put("success", true);
            map.put("data" , true);
            return map;
        }
        map.put("success", false);
        map.put("msg" , "请求失败");
        return map;
    }
}
