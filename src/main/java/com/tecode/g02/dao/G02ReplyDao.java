package com.tecode.g02.dao;

import com.tecode.bean.Task;
import com.tecode.bean.User;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G02ReplyDao {


    /**
     * 通过任务id查找到TaskBean对象
     */
    Task selectTaskByID(String taskId) throws IOException;
    /**
     * 在log中添加一个新列，列名为当前时间，值一条回复的记录
     */
    void addReplyLog(String replyLog);
    /**
     * 在comment列族中添加一列，列名为当前时间，值为系统的评论
     */
    void addSystemComment(String systemComm);
    /**
     * 将id栈中的栈顶元素移除
     */
    void removeIDFromStack();
    /**
     * 更改当前办理人的id
     */
    void changeHandler(String userId);
}
