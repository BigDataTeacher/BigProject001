package com.tecode.G04.service.impl;

import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.G04.service.G04TaskService;
import com.tecode.bean.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 被Controller层调用的方法所在类上添加@Service
 *
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class G04TaskServiceImpl implements G04TaskService {

    /**
     * 需要调用Dao层的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
    protected G04TaskIdDao g04TaskIdDao;


    @Override
    public Boolean modifyTaskState(String taskId,String cusId) throws Exception {
        List<String> idStack = g04TaskIdDao.getIdStack(taskId);

        String[] strings = idStack.toString().split(",");

        if(strings.length==1){
            g04TaskIdDao.modifyFinishState(taskId,cusId);
        }
        return null;
    }
}
