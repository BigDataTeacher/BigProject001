package com.tecode.G06.dao;

import com.tecode.bean.Task;
import com.tecode.bean.User;

/**
 * Created by Administrator on 2018/10/23.
 */
public interface G06AssignTaskDao {
    /**
     * 通过用户名将新任务添加到用户任务中
     */
    void putTaskbyUsername(String userName, String tasks) throws Exception;
    /**
     * 通过用户名查询出姓名
     */
    User getUserbyUserId(String username) throws Exception;


    /**
     * 通过rowkey TaskId将任务查询出来
     */
    Task getTaskbyTaskId(String taskId) throws Exception;


    /**
     * 通过TaskId将新任务办理人添加办理人栈、当前办理人、任务成员
     */
    void inTaskbyTaskId(String taskId, String nowHandler, String handlerStack, String memberIds) throws Exception;
}