package com.tecode.G04.dao;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface G04UserDao {


    /**
     * 根据任务成员ID查出每个成员消息未读数量,并修改
     * @param username
     * @return
     */
    void modifymsgnumber(String username, int count) throws IOException;

    /**
     * 根据username查询出任务未读数量
     * @param username
     * @return
     */
    Integer getmsgcount(String username) throws IOException;

    String getName(String userid) throws IOException;
}
