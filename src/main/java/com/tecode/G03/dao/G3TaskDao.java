package com.tecode.G03.dao;

import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.bean.TaskLog;
import com.tecode.bean.User;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G3TaskDao {

    /**
     * 添加任务
     * @param task
     * @return
     * @throws Exception
     */
    void addTask(Task task) throws Exception;

    void addComment(TaskComment coment) throws Exception;
    void addLog(TaskLog log) throws Exception;
}
