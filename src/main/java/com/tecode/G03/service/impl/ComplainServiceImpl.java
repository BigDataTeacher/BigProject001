package com.tecode.G03.service.impl;

import com.tecode.G03.dao.TaskDao;
import com.tecode.G03.dao.G03CYUserDao;
import com.tecode.G03.service.ComplainService;
import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.bean.TaskLog;
import com.tecode.enumBean.CommentatorType;
import com.tecode.enumBean.TaskCommentType;
import com.tecode.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 被Controller层调用的方法所在类上添加@Service
 * <p>
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class ComplainServiceImpl implements ComplainService {

    /**
     * 根据用户id查询
     */
    @Autowired
    private G03CYUserDao userDao;
    @Autowired
    private TaskDao taskDao;


    /**
     * @param username  当前操作人员ID
     * @param taskId    task任务id
     * @param handlerId 下一个办理人id
     * @return
     * @throws BaseException
     */
    @Override
    public void complainTask(String username, String taskId, String handlerId) throws BaseException {

        //创建task对象
        Task task = new Task();
        String handlerName = null;
        try {
            //通过任务id查找该任务所有信息，并封装添加进task对象
            task = taskDao.getTaskByTaskId(taskId);
            if(task.getTaskId() == null){
                throw new BaseException("任务不存在。。。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(task);
        //获得办理人ID集
        String[] handlers = task.getHandlerStack().split(",");
        if (handlers.length < 2) {
            throw new BaseException("任务不能转办...");
        }
        //获得当前办理人id
        String nowHandlerId = handlers[handlers.length - 1];
        //判断当前办理人和操作该任务是否是同一个人
        if (!nowHandlerId.equals(username)) {
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
        handlers[handlers.length - 1] = handlerId;
        StringBuffer sb = new StringBuffer();
        //拼接字符串
        for (String handler : handlers) {
            sb.append(handler + ",");
        }
        String str = sb.toString();
        //去掉字符串最后一个的逗号字符
        String newStack = str.substring(0, str.length() - 1);
        //封装进办理人ID栈
        task.setHandlerStack(newStack);
        //取出任务成员ID栈内的ID
        String memberIds = task.getMemberIds();
        //将下一个办理人添加进任务成员ID
        memberIds = memberIds + "," + handlerId;
        //封装进task
        task.setMemberIds(memberIds);
        //创建评论集合对象
        Set<TaskComment> taskComments = createTaskComment(username, handlerId);
        //创建日志集合对象
        Set<TaskLog> taskLogs = createLogs(username, handlerId);
        //调用修改task对象方法
        try {
            taskDao.updateTask(task);
            //调用添加评论方法
            taskDao.addComment(taskId, taskComments);
            //调用添加日志方法
            taskDao.addLog(taskId, taskLogs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取任务成员ID集
        String[] memberIds1 = task.getMemberIds().split(",");
        //获取未读消息数量
        Map<String, Integer> numOfTaskMsg = null;
        try {
            numOfTaskMsg = userDao.getNumOfTaskMsg(Arrays.asList(memberIds1), taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<String, Integer>> entries = numOfTaskMsg.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            //将新增加个任务数量添加进去（如果之前消息为空则赋值为1，不为空则在原数量上加1）
            numOfTaskMsg.put(entry.getKey(), (entry.getValue() == null || entry.getValue().equals("")) ? 1 : entry.getValue() + 1);
        }
        try {
            userDao.modifyNumOfTaskMsg(numOfTaskMsg, taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法用于创建评论
     *
     * @param nowHandler  当前办理人
     * @param nextHandler 下一个办理人
     * @return 评论集合
     */
    private Set<TaskComment> createTaskComment(String nowHandler, String nextHandler) {
        Set<TaskComment> setComment = new HashSet<>();
        TaskComment taskComment = new TaskComment();
        taskComment.setTaskCommentTime(new Date());
        taskComment.setTaskComment(nowHandler + "将任务转办给了" + nextHandler);
        taskComment.setCommentatorType(CommentatorType.SYSTEM.getType());
        taskComment.setRealName("系统消息");
        taskComment.setCommentType(TaskCommentType.TEXT.getType());
        setComment.add(taskComment);
        return setComment;
    }

    /**
     * 方法用于创建日志
     *
     * @param nowHandler  当前办理人
     * @param nextHandler 下一个办理人
     * @return 日志集合
     */
    private Set<TaskLog> createLogs(String nowHandler, String nextHandler) {
        Set<TaskLog> setLogs = new HashSet<>();
        TaskLog taskLog = new TaskLog();
        taskLog.setCommentatorType(CommentatorType.SYSTEM);
        taskLog.setContent("转办:" + nowHandler + ":" + nextHandler);
        taskLog.setLogTime(new Date());
        setLogs.add(taskLog);
        return setLogs;
    }

}