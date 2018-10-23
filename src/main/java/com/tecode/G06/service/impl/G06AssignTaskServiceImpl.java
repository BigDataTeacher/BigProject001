package com.tecode.G06.service.impl;

import com.tecode.G06.dao.G06AssignTaskDao;
import com.tecode.G06.service.G06AssignTaskService;
import com.tecode.bean.Task;
import com.tecode.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/10/23.
 */
@Service
public class G06AssignTaskServiceImpl implements G06AssignTaskService{
//    @Override
//    public Task getTaskbyTaskId(String taskId) throws Exception {
//        return null;
//    }
    @Autowired
    private G06AssignTaskDao taskDao;

    private String nowhandler=null;

//获取姓名
    @Override
    public User getUserbyUserId(String username) throws Exception {
        return taskDao.getUserbyUserId(username);
    }

    /**
     * 判断用户ID是否是任务查询中查出的当前办理人
     * 是才能继续操作
     * 不是则跳出
     */
    @Override
    public boolean handler(String taskId, String userId) throws Exception {
        nowhandler=taskDao.getTaskbyTaskId(taskId).getNowHandler();
        if(nowhandler.equals(userId)){
            return true;
        }
        return false;
    }
    /**
     * 将当前办理人ID存入班里人ID栈handlerStack
     * 将办理人名字存入当前办理人nowHandler
     * 将办理人存入任务成员IDmemberlds
     * handlerId 下一个办理人
     */
    @Override
    public Task inHandlerId(String taskId,String handlerId) throws Exception {
        //获取下一个办理人姓名
        String name=taskDao.getUserbyUserId(handlerId).getName();
        taskDao.inTaskbyTaskId(taskId,name,handlerId,handlerId);

        return null;
    }
    /**
     * 将任务ID加入下一个办理人用户任务IDtasks
     *
     */
    @Override
    public Task inTask(Task task) throws Exception {
        return null;
    }
}
