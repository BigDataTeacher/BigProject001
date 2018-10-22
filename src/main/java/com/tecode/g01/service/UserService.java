package com.tecode.g01.service;

/**
 * Created by Administrator on 2018/10/18.
 */

import com.tecode.bean.User;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface UserService {

    /**
     * 用户登录的业务处理方法
     * @param user
     * @return
     */
    User getUseLogin(User user);

    /**
     *根据用户名id查询用户对象
     */
    User getUserByUserName(String username) throws Exception;
}
