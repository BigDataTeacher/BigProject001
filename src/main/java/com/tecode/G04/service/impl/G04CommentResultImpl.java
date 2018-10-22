package com.tecode.G04.service.impl;

import com.tecode.G04.service.G04CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public class G04CommentResultImpl implements G04CommentService {
    @Autowired
    private com.tecode.G04.Dao.G04CommentDao commentdao;
    @Override
    public Boolean CommentResult(String taskid, String userid, String commentType, String comment) throws IOException {
        Boolean addcomment = commentdao.addcomment(taskid, userid, commentType, comment);

        return addcomment;
    }
}
