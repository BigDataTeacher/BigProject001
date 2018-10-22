package com.tecode.G04.service.impl;

import com.tecode.G04.dao.impl.G04CommentDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface CommentServer  {

    /**
     * 判断评论是否成功
     */
   Boolean commentresult(String taskid, String userid, String commentType, String comment) throws IOException;


}
