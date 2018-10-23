package com.tecode.g05.service.impl;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import com.tecode.exception.BaseException;
import com.tecode.g05.bean.G05TaskBean;
import com.tecode.g05.bean.RequestTaskListBean;
import com.tecode.g05.bean.UpdateTaskBean;
import com.tecode.g05.dao.G05TaskDao;
import com.tecode.g05.dao.G05UserDao;
import com.tecode.g05.service.G05TaskService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
@Service
public class G05TaskServiceImpl implements G05TaskService {
    /**
     * 每页的数量
     */
    private static final int PAGE_NUMBER = 10;

    @Autowired
    private G05UserDao userDao;
    @Autowired
    private G05TaskDao taskDao;

    /**
     * 根据传入的条件返回查询结果列表
     *
     * @param rtb
     * @return
     */
    @Override
    public Map<String, Object> getTaskList(RequestTaskListBean rtb) {
        // 根据用户ID查询出当前用户的所有任务列表
        User user = userDao.getUserByUserId(rtb.getCusId());
        List<G05TaskBean> tasks = null;
        try {
            // 获取满足条件的任务详细信息
            tasks = taskDao.getTasksByRequest(rtb, user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tasks != null && tasks.size() > 0) {
            // 总页数
            int totalPage = tasks.size() / PAGE_NUMBER + (tasks.size() % PAGE_NUMBER > 0 ? 1 : 0);
            // 页码超标
            if (totalPage < rtb.getP()) {
                return null;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            map.put("data", tasks.subList(PAGE_NUMBER * (rtb.getP() - 1), Math.min(PAGE_NUMBER * rtb.getP(), tasks.size())));
            map.put("totalPage", totalPage);
            return map;
        }

        return null;
    }

    @Override
    public Map<String, Object> updateTask(UpdateTaskBean utb) {
        Map<String, Object> map = new HashMap();

        try {
            if (taskDao.updateTask(utb)) {
                map.put("success", true);
                map.put("data", true);
                return map;
            }
        } catch (BaseException e) {
            e.printStackTrace();
        }
        map.put("success", false);
        map.put("msg", "参数错误");
        return map;
    }
}
