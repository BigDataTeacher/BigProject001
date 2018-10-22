package com.tecode.g02.service.impl;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import com.tecode.g02.dao.G02ReplyDao;
import com.tecode.g02.dao.impl.G02ReplyDaoImpl;
import com.tecode.g02.service.G02ZzReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 被Controller层调用的方法所在类上添加@Service
 *
 * 这是处理用户请求的业务逻辑实现层。
 */
@Service
public class G02ZzReplyServiceImpl implements G02ZzReplyService {


    @Override
    public boolean isReplySuccess(String taskId,String cusId) {
        boolean bl = isHandler(cusId,"");
        if(!bl){
            return false;
        }
        G02ReplyDao replyDao = new G02ReplyDaoImpl();
        Task task = null;
        try {
            task = replyDao.selectTaskByID(taskId);
            replyDao.addReplyLog("");
            replyDao.addSystemComment("");
            replyDao.removeIDFromStack();
            replyDao.changeHandler("");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

    @Override
    public boolean isHandler(String cusId, String handlerId) {
        return false;
    }

}
