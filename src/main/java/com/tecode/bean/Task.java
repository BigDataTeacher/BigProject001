package com.tecode.bean;

import com.tecode.enumBean.TaskState;

import java.util.Set;
import java.util.TreeSet;

/**
 *任务实体类
 */
public class Task {
    private String taskId;              //任务ID

    private String taskTag;              //任务分类  taskTag
    private String taskTitle;              //任务标题
    private String taskDesc;              //任务描述
    private TaskState taskState;          //任务状态

    private String sponsor;              //任务发起人
    private String sponsorId;              //任务发起人ID


    private String beAssignId;              //当前办理人id
    private String nowHandler;              //当前办理人姓名
    private String handlerStack;            //办理人ID栈

    private String createTime;              //任务发起时间
    private String timeLimit;              //任务完成时限  建议完成时间
    private String finishTime;             //任务完成时间   实际完成时间

    private String memberIds;              //任务成员ID

    //任务的日志对象
    private Set<TaskLog> taskLog = new TreeSet<>();
    //任务的评论
    private Set<TaskComment> taskComments = new TreeSet<>();



    public Set<TaskLog> getTaskLog() {
        return taskLog;
    }

    public void setTaskLog(Set<TaskLog> taskLog) {
        this.taskLog = taskLog;
    }

    public Set<TaskComment> getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(Set<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }


    public String getBeAssignId() {
        return beAssignId;
    }

    public void setBeAssignId(String beAssignId) {
        this.beAssignId = beAssignId;
    }



    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskTag='" + taskTag + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                ", taskState='" + taskState + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", sponsorId='" + sponsorId + '\'' +
                ", nowHandler='" + nowHandler + '\'' +
                ", handlerStack='" + handlerStack + '\'' +
                ", createTime='" + createTime + '\'' +
                ", timeLimit='" + timeLimit + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", memberIds='" + memberIds + '\'' +
                '}';
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getNowHandler() {
        return nowHandler;
    }

    public void setNowHandler(String nowHandler) {
        this.nowHandler = nowHandler;
    }

    public String getHandlerStack() {
        return handlerStack;
    }

    public void setHandlerStack(String handlerStack) {
        this.handlerStack = handlerStack;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(String memberIds) {
        this.memberIds = memberIds;
    }
}
