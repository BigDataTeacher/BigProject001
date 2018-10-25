package com.tecode.bean;

import com.tecode.enumBean.CommentatorType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日志实体类
 */
public class TaskLog implements Comparable<TaskLog>{
    //日期格式化
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    //日志生成时间
    private Date logTime;

    //日志内容
    private String content;
    //日志类型   如用户还是系统
    private CommentatorType commentatorType;

    public TaskLog(Date logTime, String content, CommentatorType commentatorType) {
        this.logTime = logTime;
        this.content = content;
        this.commentatorType = commentatorType;
    }

    public TaskLog() {

    }

    public CommentatorType getCommentatorType() {
        return commentatorType;
    }

    public void setCommentatorType(CommentatorType commentatorType) {
        this.commentatorType = commentatorType;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        TaskLog taskLog = (TaskLog) o;

        if (!logTime.equals(taskLog.logTime)){
            return false;
        }
        return content != null ? content.equals(taskLog.content) : taskLog.content == null;
    }

    @Override
    public int hashCode() {
        int result = logTime.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
    // 排序规则,
    @Override
    public int compareTo(TaskLog o) {
        int i = (int)(this.logTime.getTime() - o.getLogTime().getTime());
        if(i==0){
            i = this.content.compareTo(o.content);
        }

        return  i;
    }
}
