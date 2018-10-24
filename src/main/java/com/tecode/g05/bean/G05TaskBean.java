package com.tecode.g05.bean;

/**
 * 返回任务列表数据时用于封装单条任务数据的Bean
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class G05TaskBean {
    private String taskId ;
    private String sponsor;
    private String taskTitle;
    private String taskState;
    private String balanceTime;
    private String taskTag;
    private String isHandle;
    private String nowHandle;
    private String unreadMsgCount;
    private String taskFinishTime;


    /**
     * 任务id
     */
    public String getTaskId() {
        return taskId;
    }
    /**
     * 任务id
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 发起人
     */
    public String getSponsor() {
        return sponsor;
    }
    /**
     * 发起人
     */
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
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
     * 任务状态
     */
    public String getTaskState() {
        return taskState;
    }
    /**
     * 任务状态
     */
    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    /**
     * 剩余时间
     */
    public String getBalanceTime() {
        return balanceTime;
    }
    /**
     * 剩余时间
     */
    public void setBalanceTime(String balanceTime) {
        this.balanceTime = balanceTime;
    }

    /**
     * 任务标签
     */
    public String getTaskTag() {
        return taskTag;
    }
    /**
     * 任务标签
     */
    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    /**
     * true-表示当前办理人为当前登录用户，false-表示当前办理人不是当前登录用户
     */
    public String getIsHandle() {
        return isHandle;
    }
    /**
     * true-表示当前办理人为当前登录用户，false-表示当前办理人不是当前登录用户
     */
    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle;
    }

    /**
     * 当前办理人
     */
    public String getNowHandle() {
        return nowHandle;
    }
    /**
     * 当前办理人
     */
    public void setNowHandle(String nowHandle) {
        this.nowHandle = nowHandle;
    }

    /**
     * 未读消息数量
     */
    public String getUnreadMsgCount() {
        return unreadMsgCount;
    }
    /**
     * 未读消息数量
     */
    public void setUnreadMsgCount(String unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    /**
     * 任务完成时间-历史记录才有
     */
    public String getTaskFinishTime() {
        return taskFinishTime;
    }
    /**
     * 任务完成时间-历史记录才有
     */
    public void setTaskFinishTime(String taskFinishTime) {
        this.taskFinishTime = taskFinishTime;
    }

    @Override
    public String toString() {
        return "G05TaskBean{" +
                "taskId='" + taskId + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskState='" + taskState + '\'' +
                ", balanceTime='" + balanceTime + '\'' +
                ", taskTag='" + taskTag + '\'' +
                ", isHandle='" + isHandle + '\'' +
                ", nowHandle='" + nowHandle + '\'' +
                ", unreadMsgCount='" + unreadMsgCount + '\'' +
                ", taskFinishTime='" + taskFinishTime + '\'' +
                '}';
    }
}
