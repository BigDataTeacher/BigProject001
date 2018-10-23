package com.tecode.g01.service;

import com.tecode.g01.dao.UserCopyDao;
import org.apache.hadoop.mapreduce.v2.api.records.TaskId;

/**
 * 检查输入参数是否正确
 */
public interface TaskCopyService {

    //在任务表的成员ID插入相关成员
    boolean insertAndAdd(String taskId, String memberId) throws Exception;




}
