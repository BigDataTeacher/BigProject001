package com.tecode.G04.Dao;

import com.tecode.bean.User;
import com.tecode.util.hbase.table.HBaseUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface G04UserDao {
    /**
     * 根据ID查到评论者姓名
     * @param userid
     * @return
     * @throws IOException
     */
    String getName(String userid) throws IOException;

    /**
     * 根据任务成员ID查出每个成员消息未读数量,并修改
     * @param username
     * @return
     */
    void modifymsgnumber(String username) throws IOException;

}
