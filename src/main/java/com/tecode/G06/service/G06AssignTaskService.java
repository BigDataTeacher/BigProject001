package com.tecode.G06.service;

import java.util.Map;

/**
 * Created by Administrator on 2018/10/23.
 */
public interface G06AssignTaskService {
    /**
     * 交办
     * @param taskId 任务ID
     * @param cusId 当前用户ID
     * @param handlerId 下一个用户ID
     * @return
     */
    Map<String, Object> assignTask(String taskId,String cusId,String handlerId);
}
