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
     * 用户登录的业务处理方法
     * @param taskId
     * @return
     */
    Task getUseLogin(Task  taskId);

    /**
     *根据用户名id查询用户对象
     */
    Task getUserByUserName(String taskId) throws Exception;
}
