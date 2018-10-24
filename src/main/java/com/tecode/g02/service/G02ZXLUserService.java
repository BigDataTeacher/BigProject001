package com.tecode.g02.service;

/**
 * Created by Administrator on 2018/10/18.
 */


import com.tecode.bean.User;

import java.io.IOException;
import java.util.List;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface G02ZXLUserService {
    /**
     * 根据传入的用户姓名获取用户的服务
     * @param name 用户的姓名
     * @return 封装了用户对象的集合
     * @throws IOException
     */
    List<User> findUser(String name) throws IOException;
}
