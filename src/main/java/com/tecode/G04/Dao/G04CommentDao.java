package com.tecode.G04.Dao;

import com.tecode.bean.TaskComment;

import java.io.IOException;
import java.util.Date;
import java.util.List;


public interface G04CommentDao {
    /**
     *
     * @param taskid 任务ID
     * @param realName 用户姓名
     * @param commentType 内容类型
     * @param comment  评论内容
     * @return
     */
    Boolean addcomment(String taskid,String realName,String commentType,String comment,String commentatorType,Date taskCommentTime) throws IOException;

    /**
     * 获取当前任务的成员ID，为逗号隔开的字符串
     * @param taskid
     * @return
     * @throws IOException
     */
   String  getmerberID(String taskid) throws IOException;

}
