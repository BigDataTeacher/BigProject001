package com.tecode.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 评论实体类
 */
public class TaskComment implements Comparable<TaskComment> {
    //评论数据
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date taskCommentTime;
    //评论内容
    private String taskComment;

    //评论者类型
    private String commentatorType;

    //评论内容类型
    private String   commentType;
    //评论人名
    private String realName;

    public String getCommentatorType() {
        return commentatorType;
    }

    public void setCommentatorType(String commentatorType) {
        this.commentatorType = commentatorType;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

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

        if(i ==0) {
            i = this.taskComment.compareTo(o.getTaskComment());
        }
        return i;
    }
}
