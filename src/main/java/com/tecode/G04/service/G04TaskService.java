package com.tecode.G04.service;

/**
 * Created by Administrator on 2018/10/18.
 */

import com.tecode.bean.Task;
import com.tecode.bean.User;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface G04TaskService {


    /**
     *根据任务查询用户对象
     */
    Boolean modifyTaskState(String  taskId ,Task task,String cusId) throws Exception;


}