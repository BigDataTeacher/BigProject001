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
public interface G03CYTaskDao {

      void updateTask(Task task) throws IOException;
//
//    String getNowHandlerByTaskId(String taskId) throws IOException;
//
      void addComment(String taskId, Set<TaskComment> coments) throws IOException;
//
      void addLog(String taskId, Set<TaskLog> logs) throws IOException;

      Task getTaskByTaskId(String taskId) throws IOException;
}
