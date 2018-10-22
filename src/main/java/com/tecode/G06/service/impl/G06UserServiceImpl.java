package com.tecode.G06.service.impl;

import com.tecode.G06.service.G06UserService;
import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 被Controller层调用的方法所在类上添加@Service
 *
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class G06UserServiceImpl implements G06UserService {

    /**
     * 需要调用Dao层的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    private UserDao userDao;


    /**
     * 正在的登录业务处理方法
     * @param user
     * @return
     */
    @Override
    public User getUseLogin(User user) {
      return  null;
    }
    /**
     * 根据用户id查询
     */
    @Override
    public User getUserByUserName(String username) throws Exception {
        return null;
    }


}
