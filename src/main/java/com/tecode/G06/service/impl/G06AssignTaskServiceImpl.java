package com.tecode.G06.service.impl;

import com.tecode.G06.service.G06AssignTaskService;
import com.tecode.bean.Task;
import com.tecode.bean.User;

/**
 * Created by Administrator on 2018/10/23.
 */
public class G06AssignTaskServiceImpl implements G06AssignTaskService{
    @Override
    public Task getTaskbyTaskId(String taskId) throws Exception {
        return null;
    }

    @Override
    public User getUserbyUserId(String userId) throws Exception {
        return null;
    }

    @Override
    public boolean handler(String taskId, String userId) throws Exception {
        return false;
    }

    @Override
    public Task inHandlerId(Task task) throws Exception {
        return null;
    }

    @Override
    public Task inTask(Task task) throws Exception {
        return null;
    }
}
