package com.tecode.G06.dao;

/**
 * Created by Administrator on 2018/10/23.
 */
public interface G06AssignTaskDao {
    boolean assign(String taskId,String cusId,String handlerId);
}
