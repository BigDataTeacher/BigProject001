package com.tecode.G04.Dao;

import com.tecode.bean.TaskComment;

import java.io.IOException;
import java.util.List;


public interface G04CommentDao {
    /**
     *
     * @param taskid 任务ID
     * @param userid 用户ID
     * @param commentType 内容类型
     * @param comment  评论内容
     * @return
     */
    Boolean addcomment(String taskid,String userid,String commentType,String comment) throws IOException;

   String  getmerberID(String taskid) throws IOException;

}
