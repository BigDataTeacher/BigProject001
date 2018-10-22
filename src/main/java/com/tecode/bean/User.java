package com.tecode.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/18.
 */
public class User {

    private String username;//登录的用户账户
    private String password;//登录密码
    private String name;//用户的姓名
    private String department;//部门
    //存放用户的所以任务集合    key 为任务id   value 为这个任务的未读消息数量
    private Map<String,Integer> userTask =new HashMap();

    public Map<String, Integer> getUserTask() {
        return userTask;
    }

    public void setUserTask(Map<String, Integer> userTask) {
        this.userTask = userTask;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
