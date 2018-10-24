package com.tecode.g01.service;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/24.
 */
public interface CopyTaskService {
    //是否办理人
    boolean isHandler(String taskId,String username)throws IOException;
    //c抄送
    boolean toNext(String taskId, String memberId, String username) throws IOException;

}
