package com.tecode.g02.service.impl;

import com.tecode.bean.User;
import com.tecode.g02.dao.G02ZXLUserDao;
import com.tecode.g02.service.G02ZXLUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 被Controller层调用的方法所在类上添加@Service
 *
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class G02ZXLUserServiceImpl implements G02ZXLUserService {

    /**
     * 需要调用Dao层的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    private G02ZXLUserDao userDao;

    /**
     * Service层接口的实现方式
     * @param name 用户的姓名
     * @return
     * @throws IOException
     */
    @Override
    public List<User> findUser(String name) throws IOException {
        //调用Dao层的接口获得返回值
        return userDao.findUserByName(name);
    }
}
