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
    private String nowhandlers=null;

//获取姓名
//    @Override
//    public User getUserbyUserId(String username) throws Exception {
//        return taskDao.getUserbyUserId(username);
//    }

    /**
     * 判断用户ID是否是任务查询中查出的当前办理人
     * 是才能继续操作
     * 不是则跳出
     */
    @Override
    public boolean handler(String taskId, String userId) throws Exception {
        nowhandlers=taskDao.getTaskbyTaskId(taskId).getMemberIds();
        String [] id=nowhandlers.split(",");
        nowhandler=id[id.length-1];


        //  String nexthander=taskDao.getUserbyUserId(userId).getName();
        return nowhandler.equals(userId);


    }
    /**
     * 将当前办理人ID存入班里人ID栈handlerStack
     * 将办理人名字存入当前办理人nowHandler
     * 将办理人存入任务成员IDmemberlds
     * handlerId 下一个办理人
     */
    @Override
    public boolean inHandlerId(String taskId,String handlerId,String userId) throws Exception {
        //获取下一个办理人姓名（userId)
        if (handler(taskId, userId)) {
            String name = taskDao.getUserbyUserId(handlerId).getName();
            String handler=","+handlerId;
            taskDao.inTaskbyTaskId(taskId, name, handler, handler);

            return true;
        } else {
            return false;
        }
        /**
         * 将任务ID加入下一个办理人用户任务IDtasks
         *
         */
    }
    //taskId://任务id
    //handlerId://下⼀个办理⼈id
    @Override
    public boolean inTask(String taskId,String handlerId) throws Exception {
        taskDao.putTaskbyUsername(handlerId,taskId);


        return true;
    }
}
