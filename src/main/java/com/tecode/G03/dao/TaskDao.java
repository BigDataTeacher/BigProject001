package com.tecode.G03.dao;

import com.tecode.bean.Task;
import com.tecode.bean.User;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface TaskDao {

    Task updateTask(String taskId) throws Exception;

    String getNowHandlerByTaskId(String taskId) throws IOException;

    String modifyComment(String taskId) throws IOException;

    String addLog(String taskId) throws IOException;
}
