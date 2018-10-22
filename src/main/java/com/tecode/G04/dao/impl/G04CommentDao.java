package com.tecode.G04.dao.impl;

import com.tecode.bean.TaskComment;


public interface CommentDao {
    /**
     *
     * @param taskid 任务ID
     * @param userid 用户ID
     * @param commentType 内容类型
     * @param comment   
     * @return
     */
   TaskComment addcomment(String taskid,String userid,String commentType,String comment);

   // TaskComment addcomment(TaskComment taskcomment);
}
