package com.tecode.g01.dao;

import com.tecode.bean.Task;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface TaskDao {
    /**
     * 通过任务Id获取需要返回的任务详情
     */

    Task getTaskBytaskId (String taskid,String username) throws IOException;


}
