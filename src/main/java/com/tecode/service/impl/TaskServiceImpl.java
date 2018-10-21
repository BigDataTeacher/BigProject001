package com.tecode.service.impl;

import com.tecode.bean.Task;
import com.tecode.bean.TaskLog;
import com.tecode.bean.User;
import com.tecode.dao.TaskDao;
import com.tecode.enumBean.CommentatorType;
import com.tecode.enumBean.TaskState;
import com.tecode.service.TaskService;
import com.tecode.service.UserService;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.TreeSet;

/**
 * 协调任务的业务处理类
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskDao taskDao;
    /**
     *发布任务
     * * 传入的参数有
     taskTitle:// 任务标题
     taskDesc:// 任务描述
     taskEndTime:// 任务结束时间 ( 格式为： yyyy-MM-dd HH:mm:ss)-- 可为空  计划完成时间
     beAssignId:// 办理理人 id
     taskTag:"通⽤用"// 任务标签



     新增到hbase的task表中需要的数据有

     info	     taskId	     任务ID  *
                 taskTag	任务分类
                 taskTitle	任务标题
                 taskDesc	任务描述
                 taskState	任务状态
                 sponsor	任务发起人
                 sponsorId	任务发起人ID
                 nowHandler	当前办理人姓名
                 handlerStack	办理人ID栈
                 createTime	任务发起时间
                 timeLimit	任务完成时限
                 finishTime	任务完成时间
                 memberIds	任务成员ID
     comment	时间戳	评论人:评论内容
     log	    时间戳	流转动作:流转对象
     * @param user  登录人对象
     * @param task  发布任务对象
     * @return
     */
    @Override
    public boolean issueTask(User user, Task task) {
        Date createTime = new Date();
        //1.设置任务状态   任务处理中
        task.setTaskState(TaskState.HANDLE);
        //2.设置任务发起人
        task.setSponsor(user.getName());

        //3.设置任务发起人ID
        task.setSponsorId(user.getUsername());

        //4.设置当前任务 进过的办理人的id集
        task.setHandlerStack(task.getBeAssignId());

        //5.设置任务发起时间
        task.setCreateTime(DateUtil.dateToString(new Date()));


        //6.根据任务办理理人id，查询办理人姓名
        User beAssignUser = null;
        try {
            beAssignUser = userService.getUserByUserName(task.getBeAssignId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //7.设置办理人姓名
        task.setNowHandler(beAssignUser.getName());

        TreeSet<TaskLog> taskLogs = new TreeSet<>();

        //8.设置任务的日志
        //8.1 创建任务日志

        taskLogs.add(new TaskLog(createTime,user.getName() + "创建的协同任务", CommentatorType.SYSTEM));

        //8.2 任务流转操作            数据样式    -》流转动作:流转对象
        taskLogs.add(new TaskLog(new Date(),TaskState.HANDLE.getTypeName()+":"+beAssignUser.getName(),CommentatorType.SYSTEM));

        //8.3 添加日志
        task.setTaskLog(taskLogs);

        //9.生成任务的rowkey
        task.setTaskId(genRowkey(user.getUsername(),createTime.getTime()+""));


        return taskDao.addTask(task);

    }

    /**
     * 生成分区编号
     * 使用 任务发布人后三位+任务时间的时间戳后5位
     * @return
     */
    public String genRowkeyCode(String username,String timespace){
        String str1 = username.substring(username.length()-3);
        String str2 = timespace.substring(timespace.length()-5);

        //获得分区总数
        int regions = ConfigUtil.getInt("hbase.regions");


        //计算分区号
        int regionCode = (str1.hashCode() ^ str2.hashCode())  %   regions;
        //定义返回的个数
        DecimalFormat sf = new DecimalFormat("00");
        return  sf.format(regionCode);
    }

    /**
     * regionCode_任务发布人_任务时间的时间戳
     * @return
     */
    public String genRowkey(String username,String timespace){

            return genRowkeyCode(username,timespace) +"_" + username +"_" + timespace;
    }
}
