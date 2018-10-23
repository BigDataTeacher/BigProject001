package com.tecode.G03.service;

/**
 * Created by Administrator on 2018/10/18.
 */

import com.tecode.bean.Task;
import com.tecode.exception.BaseException;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface CreateTaskService {

    /**
     * 创建任务的业务处理方法
     * @param  task
     * @return true: 创建成功  false:创建失败
     */

    Boolean createTask(Task task) throws BaseException;
}
