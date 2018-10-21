package com.tecode.dao.impl;

import com.tecode.bean.Task;
import com.tecode.bean.TaskLog;
import com.tecode.dao.TaskDao;
import com.tecode.util.hbase.table.HBaseUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/19.
 */
@Repository
public class TaskDaoImpl implements TaskDao {
    @Override
    public boolean addTask(Task task) {
        Map<String,String> map = new HashMap<>();
/*      taskId	     任务ID  *
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
        memberIds	任务成员ID*/
        try {
            map.put("taskId",task.getTaskId());
            map.put("taskTag",task.getTaskTag());
            map.put("taskTitle",task.getTaskTitle());
            map.put("taskDesc",task.getTaskDesc());
            map.put("taskState",task.getTaskState().getTypeName());
            map.put("sponsor",task.getSponsor());
            map.put("sponsorId",task.getSponsorId());
            map.put("nowHandler",task.getNowHandler());
            map.put("handlerStack",task.getHandlerStack());
            map.put("createTime",task.getCreateTime());
            map.put("timeLimit",task.getTimeLimit());//计划完成时间
            map.put("finishTime","");//新发布的任务没有时间完成时间。
            map.put("memberIds","");//不知道值是什么

            //新增任务
            HBaseUtils.put("oa:task",task.getTaskId(),"info",map);
        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            Set<TaskLog> taskLogs = task.getTaskLog();
            for (TaskLog taskLog :taskLogs) {
                map = new HashMap<>();
                map.put(taskLog.getLogTime().getTime()+"",taskLog.getContent());
                //新增操作日志
                HBaseUtils.put("oa:task", task.getTaskId(), "log", map);
                System.out.println(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }

        return true;
    }


}
