package com.tecode.G03.service.impl;

import com.tecode.G03.dao.UserDao;
import com.tecode.G03.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 被Controller层调用的方法所在类上添加@Service
 *
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class ComplainServiceImpl implements ComplainService {

    /**
     * 根据用户id查询
     */
    @Autowired
    private UserDao userDao;



    @Override
    public String getUserByUserName(String username) throws Exception {

       String name = userDao.getUserByUserName(username);

        return name;
    }

}
