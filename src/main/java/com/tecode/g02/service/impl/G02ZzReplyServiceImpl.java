package com.tecode.g02.service.impl;

import com.tecode.bean.Task;
import com.tecode.exception.BaseException;
import com.tecode.g02.dao.G02ReplyDao;
import com.tecode.g02.dao.impl.G02ReplyDaoImpl;
import com.tecode.g02.service.G02ZzReplyService;
import org.springframework.stereotype.Service;
import java.io.IOException;



/**
 * 被Controller层调用的方法所在类上添加@Service
 *
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class G02ZzReplyServiceImpl implements G02ZzReplyService {
    G02ReplyDao replyDao = new G02ReplyDaoImpl();
    Task task = null;

    @Override
    public boolean isReplySuccess(String taskId,String cusId) throws IOException {
        //首先：移除栈顶元素，将当前的用户退出当前任务
        replyDao.removeIDFromStack(taskId);
        //其次：将当前的办理人改为移除栈顶元素后的栈顶元素
        replyDao.changeHandler(taskId);
        //之后：在评论中添加一条系统评论，说明回复成功
        replyDao.addSystemComment(taskId,true);
        //最后：在log中添加一条回复成功的记录
        replyDao.addReplyLog(taskId,true);

        return true;
    }


    @Override
    public boolean isHandler(String taskId, String cusId) throws Exception {
        task = replyDao.selectTaskByID(taskId);
        String handlerStack = getHandlerId(task);

        if(handlerStack.equals(cusId)){
            isReplySuccess(taskId,cusId);
            return true;
        }else {
            //添加一条系统评论，提示回复失败
            replyDao.addSystemComment(taskId,false);
            //在日志中添加一条回复失败的记录
            replyDao.addReplyLog(taskId,false);
            throw new BaseException("当前的用户不是办理人！");
        }
    }
    //获得栈顶元素
    private String getHandlerId(Task task){
        String handlerStack = task.getHandlerStack();
        String[] split = handlerStack.split(",");
        return  split[split.length-1];
    }



}
