package com.tecode.g05.Test;

import com.tecode.bean.Task;
import com.tecode.enumBean.TaskState;
import com.tecode.g05.util.G05HBaseTableUtil;
import com.tecode.util.hbase.table.HBaseUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成测试数据
 * 版本：2018/10/23 V1.0
 * 小组：D01.G05
 * 成员：李晋、建晨飞
 * Created by Administrator on 2018/10/23.
 */
public class TestData {
    // 拼装rowkey用的userId
    private static String[] userId = {"04102276", "04102269", "04102268", "04102262"};
    // 标题、描述
    private static String[] tables = {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg"};
    // 状态数组
    private static TaskState[] starts = {TaskState.FINISH,TaskState.HANDLE};
    // 格式化日期
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 起始时间
    private static String startDate = "2017-01-01 01:01:01";
    // 结束时间
    private static String endDate = "2018-10-20 23:59:59";
    // 时间差
    private static long timeLength = 0L;

    static {
        try {
            timeLength = sdf.parse(endDate).getTime() - sdf.parse(startDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i< 100 ; i++ ) {
            Task task = getTestTask();
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "taskId", task.getTaskId());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "taskTag", task.getTaskTag());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "taskTitle", task.getTaskTitle());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "taskDesc", task.getTaskDesc());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "taskState", task.getTaskState().getType());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "sponsor", task.getSponsor());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "sponsorId", task.getSponsorId());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "nowHandler", task.getNowHandler());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "handlerStack", task.getHandlerStack());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "createTime", task.getCreateTime());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "timeLimit", task.getTimeLimit());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "finishTime", task.getFinishTime());
            G05HBaseTableUtil.insertData("oa:task", task.getTaskId(), "info", "memberIds", task.getMemberIds());

            G05HBaseTableUtil.insertData("oa:user", task.getSponsorId(), "tasks", task.getTaskId(), "1");
        }
    }

    public static Task getTestTask() {
        Task task = new Task();
        // taskId 任务ID
        String taskId = getRowKey();
        task.setTaskId(taskId);
        //taskTag 任务分类
        String taskTag = "测试数据";
        task.setTaskTag(taskTag);
        // taskTitle 任务标题
        String taskTitle = tables[(int) (Math.random() * 7)];
        task.setTaskTitle(taskTitle);
        // taskDesc 任务描述
        String taskDesc = tables[(int) (Math.random() * 7)];
        task.setTaskDesc(taskDesc);
        // taskState 任务状态
        TaskState taskState = starts[(int) (Math.random() * 2)];
        task.setTaskState(taskState);
        // sponsor 任务发起人
        String sponsor = "测试";
        task.setSponsor(sponsor);
        // sponsorId 任务发起人ID
        String sponsorId = taskId.split("_")[1];
        task.setSponsorId(sponsorId);
        // nowHandler 当前办理人姓名
        String nowHandler = "测试";
        task.setNowHandler(nowHandler);
        // handlerStack 办理人ID栈
        String handlerStack = sponsorId;
        task.setHandlerStack(handlerStack);
        // createTime 任务发起时间
        String createTime = "2018-10-01 12:12:12";
        task.setCreateTime(createTime);
        // timeLimit 任务完成时限
        String timeLimit = "2018-10-31 12:12:12";
        task.setTimeLimit(timeLimit);
        // finishTime 任务完成时间
        String finishTime = "2018-10-21 12:12:12";
        task.setFinishTime(finishTime);
        //  memberIds 任务成员ID
        String memberIds = sponsorId;
        task.setMemberIds(memberIds);
        return  task;
    }

    /**
     * 随机生成rowkey 格式：(01-04)-userId-时间戳
     */
    private static String getRowKey() {
        // 生成1-4的随机数字，用于分区
        int num1 = (int) (Math.random() * 4) + 1;
        // 生成0-3的随机数字，用于userID
        int num2 = (int) (Math.random() * 4);
        // 用户计算时间的随机数
        long num3 = (long) (Math.random() * timeLength);
        String date = startDate;
        try {
            date = new Date(sdf.parse(startDate).getTime() + num3).getTime() + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String rowKey = "0" + num1 + "_" + userId[num2] + "_" + date;
        return rowKey;
    }


}
