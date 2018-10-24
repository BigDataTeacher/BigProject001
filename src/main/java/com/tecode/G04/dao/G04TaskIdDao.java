package com.tecode.G04.dao;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.exception.BaseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/18.
 */
public interface G04TaskIdDao {

    /**
     * 通过任务ID查询任务状态
     * @param taskId
     * @return
     * @throws Exception
     */
    void modifyFinishState(String taskId, String cusId) throws IOException;

    /**
     * 获得栈ID
     * @param taskId
     * @return
     * @throws Exception
     */
    String getIdStack(String taskId)throws IOException;


    /**
     * 获取发起人ID
     * @param taskId
     * @return
     * @throws Exception
     */
    String getSponsorId(String taskId)throws IOException;

    /**
     * 任务完成时间
     * @param taskId
     * @throws Exception
     */
    void  taskFinishTime(String taskId)throws IOException;

    /**
     * 添加系统评论
     * @param taskId
     * @throws Exception
     */
    void addComment(String taskId)throws  IOException;

    /**
     * 添加日志信息
     * @param taskId
     * @throws Exception
     */
    void addLog(String taskId, String sponsor)throws IOException;


    String getSponsor(String taskId)throws IOException;


}
