package com.tecode.G03.dao;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G3UserDao {

    /**
     * 通过员工编号查询员工名字
     *
     * @param usernames 员工编号集合
     * @return  以员工编号为key，名字为value的map
     * @throws Exception
     */
    Map<String, String> getNameByUserName(List<String> usernames) throws Exception;

    /**
     * 通过指定的用户ID给其添加任务信息
     *
     * @param userid 用户ID
     * @param taskid 任务ID
     * @param num    未读消息数量
     * @throws Exception
     */
    void addUserTasks(String userid, String taskid, int num) throws Exception;
}
