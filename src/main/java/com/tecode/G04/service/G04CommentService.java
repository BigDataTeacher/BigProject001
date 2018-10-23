package com.tecode.G04.service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface G04CommentService {
       Boolean CommentResult(String taskid, String realName, String commentType, String comment,String commentatorType,Date taskCommentTime) throws IOException;
}
