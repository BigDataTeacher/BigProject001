package com.tecode.g01.dao;

import java.io.IOException;
import java.util.Set;

public interface CopyDaoTask {

    /**
     * 任务表里传入任务ID和要插入的用户ID
     */
    Integer putIntoMenment(String taskId, String memberId) throws IOException;

    /**
     * 用户表里传入任务Id和要插入的员工id
     */
    Integer putIdintoUserTask(String taskId, String memberId) throws IOException;

    /**
     * 判断当前用户是否时办理人
     */

    Set<String> getHandler(String taskId) throws IOException;
    /**
     * 向log中添加记录
     */
    Integer addLog(String taskId, String username, String menberId) throws IOException;

    /**
     * 向评论中添加记录
     */
    Integer addComment(String taskId, String username, String menberId) throws IOException;
}
