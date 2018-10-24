package com.tecode.G03.service;

/**
 * Created by Administrator on 2018/10/18.
 */

import com.tecode.bean.TaskComment;
import com.tecode.bean.User;
import com.tecode.exception.BaseException;

import java.util.Set;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface ComplainService {

    /**
     *根据用户名id查询用户对象
     */
    String complainTask(String username,String taskId,String handlerId) throws BaseException;




}
