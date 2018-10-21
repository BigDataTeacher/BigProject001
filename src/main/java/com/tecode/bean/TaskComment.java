package com.tecode.bean;

import java.util.Date;

/**
 * 评论实体类
 */
public class TaskComment implements Comparable<TaskComment> {
    //评论数据
    private Date taskCommentTime;
    //评论内容
    private String taskComment;


    public Date getTaskCommentTime() {
        return taskCommentTime;
    }

    public void setTaskCommentTime(Date taskCommentTime) {
        this.taskCommentTime = taskCommentTime;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }

    @Override
    public int compareTo(TaskComment o) {
        int i = (int)(this.taskCommentTime.getTime() - o.getTaskCommentTime().getTime());

        if(i ==0)
                i = this.taskComment.compareTo(o.getTaskComment());

        return i;
    }
}
