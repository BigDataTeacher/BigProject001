package com.tecode.g02.dao;

import com.tecode.bean.Task;
import java.io.IOException;

/**
 * Created by Administrator on 2018/10/18.
 * @author zhangzhou
 */
public interface G02ReplyDao {

    /**
     * 通过任务id查找到TaskBean对象
     * @param taskId:任务编号
     * @return 返回一个集成好的TaskBean对象
     * @throws IOException：IO异常
     */
    Task selectTaskByID(String taskId) throws IOException;

    /**
     * 在log中添加一个新列，列名为当前时间，值一条回复的记录
     * @param taskId:任务编号
     * @param cusId：当前用户Id
     * @param bl:判断回复任务是否成功
     * @throws IOException：IO异常
     */
    void addReplyLog(String taskId,boolean bl,String cusId) throws IOException;

    /**
     *  在comment列族中添加一列，列名为当前时间，值为系统的评论
     * @param taskId:任务Id
     * @param bl：判断回复任务是否成功
     * @param cusId：当前用户Id
     * @throws IOException：IO异常
     */
    void addSystemComment(String taskId,boolean bl,String cusId) throws IOException;

    /**
     * 将id栈中的栈顶元素移除
     * @param taskId：任务Id
     * @throws IOException：IO异常
     */
    void removeIDFromStack(String taskId) throws IOException;

    /**
     * 更改当前办理人的姓名
     * @param taskId:任务Id
     * @return:返回更改后的办理人姓名
     * @throws IOException：IO异常
     */
    String changeHandler(String taskId) throws IOException;
}
