package com.tecode.G03.dao;

import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.bean.TaskLog;
import com.tecode.bean.User;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G3TaskDao {

    /**
     * 添加任务
     *
     * @param task 任务对象
     * @throws Exception
     */
    void addTask(Task task) throws IOException;

    /**
     * 添加评论
     *
     * @param coments 评论集合
     * @throws Exception
     */

    void addComment(String taskID, Set<TaskComment> coments) throws IOException;

    /**
     * 添加log
     *
     * @param logs log集合
     * @throws Exception
     */
    void addLog(String taskID, Set<TaskLog> logs) throws IOException;
}
