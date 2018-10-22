package com.tecode.g05.dao;

import com.tecode.bean.User;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public interface G05UserDao {
    /**
     * 根据用户名获取用户详细信息
     * @param userId    用户ID
     * @return
     */
    User getUserByUserId(String userId) ;
}
