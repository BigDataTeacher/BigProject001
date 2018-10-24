package com.tecode.G03.service.impl;

import com.tecode.G03.dao.TaskDao;
import com.tecode.G03.dao.UserDao;
import com.tecode.G03.service.ComplainService;
import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private TaskDao taskDao;

    @Override
    public String complainTask(String username, String taskId, String handlerId) throws BaseException {
//          String nowHandler = null;
////        String name = null;
////        String nextHandler = null;
//        List<String> nameList = new ArrayList<>();
//        nameList.add(username);
//        nameList.add(handlerId);
//        try {
//                //name = userDao.getNameByUserName(username);
//                nowHandler = taskDao.getNowHandlerByTaskId(taskId);
//                //nextHandler = userDao.getNameByUserName(handlerId);
//
//            if (name.equals(nowHandler)) {
//                userDao.addTask(handlerId);
//                taskDao.modifyComment(taskId,);
//                taskDao.addLog(taskId, );
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //创建task对象
        Task task = new Task();


        try {
            //通过任务id查找该任务所有信息，并封装进task对象
            task = taskDao.getTaskByTaskId(taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;


    }
}