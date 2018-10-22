package com.tecode.G03.dao;

import com.tecode.bean.User;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface UserDao {

    /**
     * 通过员工编号查询员工对象
     * @param username
     * @return
     * @throws Exception
     */
    User getUserByUserName(String username) throws Exception;
}
