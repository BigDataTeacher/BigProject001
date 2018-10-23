package com.tecode.G04.service.impl;

import com.tecode.G04.dao.G04CommentDao;
import com.tecode.G04.dao.G04UserDao;
import com.tecode.G04.service.G04CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2018/10/22.
 */
@Service
public class G04CommentServerImpl implements G04CommentService {
    @Autowired
    private G04CommentDao commentdao;
    @Autowired
    private G04UserDao userdao;

    /**
     * 根据userID查询到用户姓名，在addcomment()方法中添加评论，添加评论
     * 成功后，取出该任务ID下的任务成员ID，遍历ID，在每个ID对应的user表
     * 中未读消息次数加1。
     * @param taskid
     * @param commentatorId
     * @param commentType
     * @param comment
     * @return
     * @throws IOException
     */
    @Override
    public Boolean CommentResult(String taskid, String commentatorId, String commentType, String comment) throws IOException {
        Boolean addcomment = commentdao.addcomment(taskid, commentatorId, commentType, comment);
        if (addcomment == true){
            String[] ids = commentdao.getmerberID(taskid).split(",");
            for (String id : ids) {
                Integer getmsgcount = userdao.getmsgcount(id);
                getmsgcount = getmsgcount+1;
                userdao.modifymsgnumber(id,getmsgcount);
            }
        }
        return addcomment;
    }
}
