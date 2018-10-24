package com.tecode.g01.service.impl;

import com.tecode.bean.Task;
import com.tecode.exception.BaseException;
import com.tecode.g01.dao.TaskDao;
import com.tecode.g01.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 被Controller层调用的方法所在类上添加@Service
 *
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao task ;

    /**
     * 实现service层的通过前端传入参数raskid查询到某个任务详情
     * @param taskid  任务id
     * @return
     */
    @Override
    public Task getTaskdetail(String taskid,String username) throws BaseException {
        try {
            try {
                return task.getTaskBytaskId(taskid,username);
            } catch (BaseException e) {
               throw new BaseException(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
