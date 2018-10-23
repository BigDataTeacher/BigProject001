package com.tecode.G03.service;

/**
 * Created by Administrator on 2018/10/18.
 */

import com.tecode.bean.User;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface ComplainService {

    /**
     *根据用户名id查询用户对象
     */
    String getUserByUserName(String username) throws Exception;




}
