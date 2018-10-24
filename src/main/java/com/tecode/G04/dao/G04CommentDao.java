package com.tecode.G04.dao;

import java.io.IOException;
import java.util.Date;


public interface G04CommentDao {
    /**
     *
     * @param taskid 任务ID
     * @param realName 用户姓名
     * @param commentType 内容类型
     * @param comment  评论内容
     * @return
     */
    Boolean addcomment(String taskid, String commentatorId, String commentType, String comment) throws IOException;

    /**
     * 获取当前任务的成员ID，为逗号隔开的字符串
     * @param taskid
     * @return
     * @throws IOException
     */
   String  getmerberID(String taskid) throws IOException;

}
