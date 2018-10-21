package com.tecode.service;

import com.tecode.bean.Task;
import com.tecode.bean.User;

/**
 * Created by Administrator on 2018/10/19.
 */
public interface TaskService {
    /**
     * 发布任务
     * @param user
     * @param task
     * @return
     */
    boolean issueTask(User user, Task task);
}
