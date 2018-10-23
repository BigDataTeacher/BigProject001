package com.tecode.G03.dao;

import com.tecode.bean.User;

import java.io.IOException;

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
    String getUserByUserName(String username) throws Exception;

    void addTask(String username) throws IOException;

    String modifyNumOfTaskMsg(String username);
}
