package com.tecode.g01.dao;

import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.exception.BaseException;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface TaskDao {
    /**
     * 通过任务Id获取需要返回的任务详情
     */

    Task getTaskBytaskId(String taskid, String username) throws IOException, BaseException;
    public Set<TaskComment> getTaskCommentBytaskId(String taskid) throws IOException;
    public String getCf(int index);


}
