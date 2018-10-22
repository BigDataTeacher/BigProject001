package com.tecode.g01.dao;

/**
 * 增加用户的任务字段的任务Id
 */
public interface UserCopyDao {

    boolean addTask(String username,String column,String taskId) throws Exception;
}
