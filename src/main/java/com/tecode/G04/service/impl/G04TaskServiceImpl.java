package com.tecode.G04.service.impl;

import com.tecode.G04.dao.G04CommentDao;
import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.G04.dao.G04UserDao;
import com.tecode.G04.service.G04TaskService;
import com.tecode.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    private G04TaskIdDao g04TaskIdDao;
    @Autowired
    private G04CommentDao g04CommentDao;
    @Autowired
    private G04UserDao g04UserDao;

    @Override
    public void modifyTaskState(String  taskId,String cusId) throws BaseException {


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
        String[] strings = idStack.split(","); //

        String sponsorId = null;
        try {
             sponsorId = g04TaskIdDao.getSponsorId(taskId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判断ID栈是否最后一个和当前用户ID和发起人ID 是否一致
        try {
            if(strings.length==1&&cusId.equals(sponsorId)){
                g04TaskIdDao.modifyFinishState(taskId,cusId);

            }else {
                throw  new BaseException("当前用户ID和发起人ID 不一致");
            };
            //调用完成任务时间方法
            g04TaskIdDao.taskFinishTime(taskId);

            //调用添加评论方法
            g04TaskIdDao.addComment(taskId);

            //调用添加日志方法
            g04TaskIdDao.addLog(taskId, sponsor);;

            //获取当前任务成员ID
            String[] ids = g04CommentDao.getmerberID(taskId).split(",");

            for (String id : ids) {
                //获得未读
                Integer getmsgcount = g04UserDao.getmsgcount(id);
                getmsgcount=getmsgcount+1;

                g04UserDao.modifymsgnumber(id,getmsgcount);
            }


        } catch (IOException e) {

            e.printStackTrace();
        }


    }

}
