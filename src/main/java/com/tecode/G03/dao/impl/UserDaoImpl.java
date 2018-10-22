package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.UserDao;
import com.tecode.bean.User;
import org.springframework.stereotype.Repository;

/**
 *   用户数据处理层的具体实现
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
