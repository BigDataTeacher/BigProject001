package com.tecode.service.impl;

import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import com.tecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 被Controller层调用的方法所在类上添加@Service
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 正在的登录业务处理方法
     * @param user
     * @return
     */
    @Override
    public User getUseLogin(User user) {
        User u = null;
        try {
            //把用户名传入Dao层，进行查询该用户对象
             u = userDao.getUserByUserName(user.getUsername());
             //判断根据username参数查询到的User对象的password是否和传入的password相同。 如果不相同说明登录失败
             if(!u.getPassword().equals(user.getPassword())) return  null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
    /**
     * 根据用户id查询
     */
    @Override
    public User getUserByUserName(String username) throws Exception {
        return userDao.getUserByUserName(username);
    }


}
