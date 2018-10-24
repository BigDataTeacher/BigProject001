package com.tecode.G06.dao;

import com.tecode.bean.Task;
import com.tecode.bean.User;

/**
 * Created by Administrator on 2018/10/23.
 */
public interface G06AssignTaskDao {
    boolean assign(String taskId,String cusId,String handlerId);
    void putTaskbyUsername(String userName, String task) throws Exception;
    User getUserbyUserId(String username) throws Exception;
    Task getTaskbyTaskId(String taskId) throws Exception;
    void inTaskbyTaskId(String taskId, String nowHandler, String handlerStack, String memberIds) throws Exception;

}
