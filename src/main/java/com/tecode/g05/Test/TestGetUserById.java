package com.tecode.g05.Test;

import com.tecode.g05.dao.G05UserDao;
import com.tecode.g05.dao.impl.G05UserDaoImpl;

/**
 * 测试是否能通过userid获取到User
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class TestGetUserById {
    public static void main(String[] args) {
        G05UserDao udao = new G05UserDaoImpl();
        udao.getUserByUserId("04102276");
    }
}
