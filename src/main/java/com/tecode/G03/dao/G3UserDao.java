package com.tecode.G03.dao;


import java.util.List;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G3UserDao {

    /**
     * 通过员工编号查询员工名字
     * @param usernames
     * @return
     * @throws Exception
     */
    List<String> getNameByUserName(List<String> usernames) throws Exception;
    void addUserTasks(String userid,String taskid,int num) throws Exception;
}
