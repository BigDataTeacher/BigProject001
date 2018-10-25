package com.tecode.G03.dao;

import com.tecode.bean.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G03CYUserDao {

    /**
     * 通过员工编号查询员工对象
     * @param username
     * @return
     * @throws Exception
     */
    String getNameByUserName(String username) throws IOException;

//    void addTask(String username,int num) throws IOException;

    Map<String,Integer> getNumOfTaskMsg(List<String> usernames, String taskId) throws IOException;

    void modifyNumOfTaskMsg(Map<String, Integer> map, String taskId) throws IOException;
}
