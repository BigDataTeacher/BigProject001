package com.tecode.dao;

import com.tecode.bean.Task;

/**
 * 协同任务的数据处理层
 */
public interface TaskDao {

    /**
     * 新增协同任务
     */
    boolean addTask(Task task);
}
