package com.tecode.g05.service.impl;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import com.tecode.g05.bean.G05TaskBean;
import com.tecode.g05.bean.RequestTaskListBean;
import com.tecode.g05.dao.G05TaskDao;
import com.tecode.g05.dao.G05UserDao;
import com.tecode.g05.service.G05TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
@Service
public class G05TaskServiceImpl implements G05TaskService{
    @Autowired
    private G05UserDao userDao;
    @Autowired
    private G05TaskDao taskDao;
    /**
     * 根据传入的条件返回查询结果列表
     * @param rtb
     * @return
     */
    @Override
    public Set<G05TaskBean> getTaskList(RequestTaskListBean rtb) {
        // 根据用户ID查询出当前用户的所有任务列表
        User user = userDao.getUserByUserId(rtb.getCusId());
        System.out.println(user);
        try {
            // 获取满足条件的任务详细信息
            List<Task> tasks = taskDao.getTasksByRequest(rtb, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
