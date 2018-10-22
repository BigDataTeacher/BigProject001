package com.tecode.G04.service;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface G04CommentService {
       Boolean CommentResult(String taskid, String userid, String commentType, String comment) throws IOException;
}
