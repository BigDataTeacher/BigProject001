package com.tecode.g05.bean;

/**
 * 封装请求修改任务的参数
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class UpdateTaskBean {
    private String taskId;
    private String sponsorId;
    private String cusId;
    private String taskTitle;
    private String taskDesc;
    private String taskEndTime;


    /**
     * 任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 当前操作用户ID
     */
    public String getCusId() {
        return cusId;
    }

    /**
     * 当前操作用户ID
     */
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    /**
     * 任务标题
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * 任务标题
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /**
     * 任务描述
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * 任务描述
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * 任务结束时间--只有发起人能改
     */
    public String getTaskEndTime() {
        return taskEndTime;
    }

    /**
     * 任务结束时间--只有发起人能改
     */
    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    /**
     * 任务发起人ID
     */
    public String getSponsorId() {
        return sponsorId;
    }

    /**
     * 任务发起人ID
     */
    public void setSponsorId(String sponsorId) {
        this.sponsorId = sponsorId;
    }

    @Override
    public String toString() {
        return "UpdateTaskBean{" +
                "taskId='" + taskId + '\'' +
                ", sponsorId='" + sponsorId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDesc='" + taskDesc + '\'' +
                ", taskEndTime='" + taskEndTime + '\'' +
                '}';
    }
}
