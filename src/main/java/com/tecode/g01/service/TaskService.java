package com.tecode.g01.service;

/**
 * Created by Administrator on 2018/10/18.
 */

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.exception.BaseException;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface TaskService {

    /**
     * 用户通过taskid获取任务详情
     * @param
     * @return
     */
    Task getTaskdetail(String taskid, String username) throws BaseException;


}
