package com.tecode.g01.service.impl;

import com.tecode.g01.dao.TaskCopyDao;
import com.tecode.g01.dao.UserCopyDao;
import com.tecode.g01.service.TaskCopyService;

/**
 * Created by Administrator on 2018/10/22.
 */
public class TaskCopyServiceImpl implements TaskCopyService{

    private TaskCopyDao taskDao;
    private UserCopyDao userDao;

    @Override
    public boolean insertTaskMember(String taskId, String memberId) throws Exception {

        /**
         * 在任务表的memberids字段追加一个员工
         * 参数：String taskId：任务ID
         *       String memberId：成员Id
         *
         *       调用Dao的方法来实现，返回是否成功
         */

        boolean b = taskDao.insertTaskMember(taskId, memberId);








        return b;
    }

    @Override
    public boolean addTask(String username, String column, String taskId) throws Exception {
        /**
         * 在用户表的taskId字段追加一个任务
         */
        boolean b = userDao.addTask(username, column, taskId);

        System.out.println("信息抄送成功");
        return b;

    }
}
