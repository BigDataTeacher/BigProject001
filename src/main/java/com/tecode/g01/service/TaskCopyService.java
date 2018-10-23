package com.tecode.g01.service;

/**
 * 检查输入参数是否正确
 */
public interface TaskCopyService {

    //在任务表的成员ID插入相关成员
    boolean insertTaskMember(String taskId,String memberId) throws Exception;



    //在用户表的任务字段添加该任务
    boolean addTask(String username,String column,String taskId) throws Exception;
}
