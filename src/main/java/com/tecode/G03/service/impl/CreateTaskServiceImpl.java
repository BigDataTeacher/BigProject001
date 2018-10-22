package com.tecode.G03.service.impl;

import com.tecode.G03.dao.G3UserDao;
import com.tecode.G03.dao.G3TaskDao;
import com.tecode.G03.service.CreateTaskService;
import com.tecode.bean.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 被Controller层调用的方法所在类上添加@Service
 * <p>
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class CreateTaskServiceImpl implements CreateTaskService {

    /**
     * 需要调用Dao层的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    private G3UserDao userDao;
    @Autowired
    private G3TaskDao taskDao;


    @Override
    public Boolean createTask(Task task) throws Exception {
        List<String> usernames = new ArrayList<>();
        //从task对象中取出sponsorID和beAssignID，并存入List集合中
        String sponsorID = task.getSponsorId();
        String beAssignID = task.getBeAssignId();
        usernames.add(sponsorID);
        usernames.add(beAssignID);
    /*
      调用userDao的getNameByUserName方法来获得其对应的用户ID所对应的的用户名字的集合
      */
        List<String> names = userDao.getNameByUserName(usernames);
        // 将取得的用户名字封装如task对象中
        //封装任务发起人名字
        task.setSponsor(names.get(0));
        //封装任务办理人名字
        task.setBeAssignId(names.get(1));
        //
        //
        //
        //
        //
        //
        //

        return null;
    }
}
