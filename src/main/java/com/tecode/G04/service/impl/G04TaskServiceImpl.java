package com.tecode.G04.service.impl;

import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.G04.dao.impl.G04TaskIdDaoImpl;
import com.tecode.G04.service.G04TaskService;
import com.tecode.bean.Task;
import com.tecode.exception.BaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    @Test
    public Boolean modifyTaskState(String  taskId,String cusId) throws BaseException {


        //得到发起人
        String sponsor = null;
        try {
            sponsor = g04TaskIdDao.getSponsor(taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //得到ID栈
        String idStack = null;
        try {
            idStack = g04TaskIdDao.getIdStack(taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //分割
        String[] strings = idStack.split(",");

        try {
            String sponsorId = g04TaskIdDao.getSponsorId(taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //判断ID栈是否最后一个和当前用户ID和发起人ID 是否一致
        try {
            if(strings.length==1&&cusId.equals(g04TaskIdDao.getSponsorId(taskId))){
                g04TaskIdDao.modifyFinishState(taskId,cusId);

            }else return  null ;
            //调用完成任务时间方法
            g04TaskIdDao.taskFinishTime(taskId);

            //调用添加评论方法
            g04TaskIdDao.addComment(taskId);

            //调用添加日志方法
            g04TaskIdDao.addLog(taskId, sponsor);

        } catch (Exception e) {

            e.printStackTrace();
        }


        return true;
    }

}
