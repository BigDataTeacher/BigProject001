package com.tecode.G03.service.impl;

import com.tecode.G03.dao.TaskDao;
import com.tecode.G03.dao.UserDao;
import com.tecode.G03.service.ComplainService;
import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.bean.TaskLog;
import com.tecode.exception.BaseException;
import org.apache.hadoop.hbase.client.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

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


    /**
     *
     * @param username      当前操作人员ID
     * @param taskId        task任务id
     * @param handlerId     下一个办理人id
     * @return
     * @throws BaseException
     */
    @Override
    public void complainTask(String username, String taskId, String handlerId) throws BaseException, IOException {

        //创建task对象
        Task task = new Task();
        String handlerName = null;
        try {
            //通过任务id查找该任务所有信息，并添加进task对象
            task = taskDao.getTaskByTaskId(taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String [] handlers = task.getHandlerStack().split(",");
        if(handlers.length<2){
            throw new BaseException("任务不能转办...");
        }
        String nowHandlerId = handlers[handlers.length-1];
        if(!nowHandlerId.equals(username)){
            throw new BaseException("当前操作人不是该任务当前办理人，没有权利操作此转办操作...");
        }
        try {
            //获得下一个办理人姓名
            handlerName = userDao.getNameByUserName(handlerId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //更改当前办理人姓名
        task.setNowHandler(handlerName);
        //替换掉当前办理人ID
        handlers[handlers.length-1] = handlerId;
        StringBuffer sb = new StringBuffer();
        //拼接字符串
        for (String handler : handlers) {
            sb.append(handler+",");
        }
        String str = sb.toString();
        String newStack = str.substring(0,str.length()-1);
        //封装
        task.setHandlerStack(newStack);
        //添加任务成员ID
        String memberIds = task.getMemberIds();
        memberIds = memberIds+","+ handlerId;
        task.setMemberIds(memberIds);
        //创建评论集合对象
        Set<TaskComment> taskComments = new HashSet<>();
        //创建日志集合对象
        Set<TaskLog> taskLogs = new HashSet<>();
        //调用修改task对象方法
        taskDao.updateTask(task);
        //调用添加评论方法
        taskDao.addComment(taskId,taskComments);
        //调用添加日志方法
        taskDao.addLog(taskId,taskLogs);

        //
        String[] memberIds1 = task.getMemberIds().split(",");
        Map<String, Integer> numOfTaskMsg = userDao.getNumOfTaskMsg(Arrays.asList(memberIds1), taskId);
        Set<Map.Entry<String, Integer>> entries = numOfTaskMsg.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            numOfTaskMsg.put(entry.getKey(),entry.getValue() == null?1:entry.getValue()+1);
        }
        userDao.modifyNumOfTaskMsg(numOfTaskMsg,taskId);
    }

}