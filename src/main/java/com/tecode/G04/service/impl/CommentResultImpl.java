package com.tecode.G04.service.impl;

import com.tecode.G04.dao.impl.G04CommentDao;
import com.tecode.G04.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public class CommentResultImpl implements CommentService {
    @Autowired
    private G04CommentDao commentdao;
    @Override
    public Boolean CommentResult(String taskid, String userid, String commentType, String comment) throws IOException {
        Boolean addcomment = commentdao.addcomment(taskid, userid, commentType, comment);

        return addcomment;
    }
}
