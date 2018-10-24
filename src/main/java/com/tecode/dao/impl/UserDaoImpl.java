package com.tecode.dao.impl;

import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 *   用户数据处理层的具体实现
 *
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class UserDaoImpl implements UserDao {


    @Override
    public User getUserByUserName(String username) throws Exception {



        return null;
    }
}
