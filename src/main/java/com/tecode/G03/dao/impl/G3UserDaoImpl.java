package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.G3UserDao;
import com.tecode.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G3UserDaoImpl implements G3UserDao {


    @Override
    public List<String> getNameByUserName(List<String> usernames) throws Exception {

        return null;
    }

    @Override
    public void addUserTasks() throws Exception {

    }
}
