package com.tecode.g02.dao.impl;

import com.tecode.bean.User;
import com.tecode.g02.dao.G02UserDao;
import org.springframework.stereotype.Repository;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G02UserDaoImpl implements G02UserDao {


    @Override
    public User getUserByUserName(String username) throws Exception {



        return null;
    }
}
