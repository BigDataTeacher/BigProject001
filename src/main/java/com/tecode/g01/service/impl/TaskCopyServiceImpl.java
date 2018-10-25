package com.tecode.g01.service.impl;

import com.tecode.g01.dao.TaskCopyDao;
import com.tecode.g01.dao.UserCopyDao;
import com.tecode.g01.service.TaskCopyService;
import org.apache.hadoop.mapreduce.v2.api.records.TaskId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service
public class TaskCopyServiceImpl implements TaskCopyService{

    @Autowired
    private TaskCopyDao taskDao;
    @Autowired
    private UserCopyDao userDao;


    /**
     * 调用Dao的方法
     */
    @Override
    public boolean insertAndAdd(String taskId, String memberId) throws Exception {
        boolean flag = false;
        boolean b = taskDao.insertTaskMember(taskId, memberId);
        boolean b1 = userDao.addTask(taskId);

        if(b!=b1 ){
            return flag;

        }


        taskDao.addLog(taskId,memberId);
        return true;
    }


}
