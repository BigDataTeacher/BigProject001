package com.tecode.G04.service.impl;

import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.G04.service.G04TaskService;
import com.tecode.bean.Task;
import com.tecode.exception.BaseException;
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
    public Boolean modifyTaskState(String  taskId,Task task,String cusId) throws Exception {


        //得到发起人
        String sponsor = task.getSponsor();
        //得到ID栈
        List<String> idStack = g04TaskIdDao.getIdStack(taskId);
        //分割
        String[] strings = idStack.toString().split(",");

        boolean  flag =false;
        //判断ID栈是否最后一个和当前用户ID和发起人ID 是否一致
        if(strings.length==1&&cusId.equals(g04TaskIdDao.getSponsorId(taskId))){
            g04TaskIdDao.modifyFinishState(taskId,cusId);
            flag=true;
        }
        //调用完成任务时间方法
      try {
            g04TaskIdDao.taskFinishTime(taskId);
      }catch (Exception e){
          System.out.println("任务不存在");
        }
        //调用添加评论方法
        try {
            g04TaskIdDao.addComment(taskId);
        }catch (Exception e){
            System.out.println("任务不存在");
        }

        //调用添加日志方法
        try {
            g04TaskIdDao.addLog(taskId,sponsor);
        }catch (Exception b){
            System.out.println("任务不存在");
        }

        return flag;
    }
}
