package com.tecode.g01.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import com.tecode.bean.Task;

/**
 *
 */
public interface TaskCopyDao {
    /**
     * 在任务表的memberId字段里添加新员工
     */
    boolean insertTaskMember(String taskId,String memberId) throws Exception;

    /**
     * 添加日志
     */
    void addLog(String taskId,String memberId) throws IOException;

    String getCf(int n );
}

