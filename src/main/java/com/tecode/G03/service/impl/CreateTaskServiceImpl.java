package com.tecode.G03.service.impl;

import com.tecode.G03.dao.G3TaskDao;
import com.tecode.G03.dao.G3UserDao;
import com.tecode.G03.service.CreateTaskService;
import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.bean.TaskLog;
import com.tecode.enumBean.CommentatorType;
import com.tecode.enumBean.TaskCommentType;
import com.tecode.enumBean.TaskState;
import com.tecode.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
        Set<TaskComment> commentSet = new TreeSet<>();
        Set<TaskLog> logSet = new TreeSet<>();

        //设置任务ID
        String taskid = ;
        //从task对象中取出sponsorID和beAssignID，并存入List集合中
        String sponsorID = task.getSponsorId();
        String beAssignID = task.getBeAssignId();
        usernames.add(sponsorID);
        usernames.add(beAssignID);
    /*
      调用userDao的getNameByUserName方法来获得其对应的用户ID所对应的的用户名字的集合
      */
        Map<String,String> names = userDao.getNameByUserName(usernames);
        if(names.size() != 2){
            throw new BaseException("用户信息不存在");
        }
        //封装taskID
        task.setTaskId(taskid);
        // 将取得的用户名字封装入task对象中
        //封装任务发起人名字
        task.setSponsor(names.get(sponsorID));
        //封装任务办理人名字
        task.setBeAssignId(names.get(beAssignID));
        //封装任务状态
        task.setTaskState(TaskState.HANDLE);
        //封装办理人ID栈
        task.setHandlerStack(sponsorID + "," + beAssignID);
        //封装任务发起时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        task.setCreateTime(createTime);
        //封装任务成员ID
        task.setMemberIds(sponsorID + "," + beAssignID);
        //封装Comment信息

        //创建任务评论
        TaskComment createComment = new TaskComment();
        //评论时间
        createComment.setTaskCommentTime(new Date());
        //评论内容
        createComment.setTaskComment(names.get(sponsorID) + "发起了任务");
        //评论者类型
        createComment.setCommentatorType(CommentatorType.SYSTEM.getType());
        //评论内容类型
        createComment.setCommentType(TaskCommentType.TEXT.getType());
        //评论人名
        createComment.setRealName("系统消息");
        commentSet.add(createComment);

        //交办任务评论
        TaskComment assignedComment = new TaskComment();
        //评论时间
        assignedComment.setTaskCommentTime(new Date());
        //评论内容
        assignedComment.setTaskComment(names.get(sponsorID) + "交办任务给" + names.get(beAssignID));
        //评论者类型
        assignedComment.setCommentatorType(CommentatorType.SYSTEM.getType());
        //评论内容类型
        assignedComment.setCommentType(TaskCommentType.TEXT.getType());
        //评论人名
        assignedComment.setRealName("系统消息");
        commentSet.add(assignedComment);

        task.setTaskComments(commentSet);

        //封装Log信息
        TaskLog log = new TaskLog();

        //日志生成时间
        log.setLogTime(new Date());
        //日志类型   如用户还是系统
        log.setCommentatorType(CommentatorType.SYSTEM);
        //日志内容
        log.setContent("交办：" + names.get(sponsorID) + ":" + names.get(beAssignID));
        logSet.add(log);
        task.setTaskLog(logSet);


        //向Task表中添加task记录
        taskDao.addTask(task);

        //向User表中任务发起人添加对应user的tasks信息
        userDao.addUserTasks(sponsorID, taskid, 0);
        //向User表中任务办理人添加对应user的tasks信息
        userDao.addUserTasks(beAssignID, taskid, 2);
        return true;
    }
}
