package com.tecode.g01.service;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/24.
 */
public interface CopyTaskService {
    boolean toNext(String taskId, String memberId, String username) throws IOException;
    boolean isHandler(String taskId,String username)throws IOException;
}
