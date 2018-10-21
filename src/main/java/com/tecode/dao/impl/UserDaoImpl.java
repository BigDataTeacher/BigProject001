package com.tecode.dao.impl;

import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import com.tecode.util.hbase.table.HBaseUtils;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/10/18.
 */
@Repository
public class UserDaoImpl implements UserDao {


    @Override
    public User getUserByUserName(String username) throws Exception {

        User user = HBaseUtils.queryRowKey("oa:user", "info",  username);

        return user;
    }
}
