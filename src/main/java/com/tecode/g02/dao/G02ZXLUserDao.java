package com.tecode.g02.dao;

import com.tecode.bean.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G02ZXLUserDao {
    /**
     * 通过传入的姓名查找用户
     * @param  name 传入用户的姓名
     * @return  返回一个封装了user对象的集合
     * @throws IOException
     */
    List<User> findUserByName(String name) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
