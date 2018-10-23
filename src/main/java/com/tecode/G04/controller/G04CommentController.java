package com.tecode.G04.controller;

import com.tecode.G04.service.G04CommentService;
import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.util.hbase.table.SessionUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 1.在接收html请求的java文件的类上添加@Controller
 */
@Controller
public class G04CommentController {
    /**
     *需要调用业务层（Services)的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
     */
    @Autowired
   private G04CommentService commentServer;

    //,String realName,String commentType,String taskComment,String commentatorType,String taskCommentTime
    @ResponseBody
    @RequestMapping(value = "/comment-task", method = RequestMethod.POST)
    public Map<String,Object> finishTask( String taskId,String commentatorId,String commentType,String taskComment,HttpSession session)  {
        Map<String,Object> map = new HashMap<String,Object>();
        //String Name = SessionUtil.getLogingUser(session).getName();
      /*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long date = 0;
        try {
            Date parse = sdf.parse(taskCommentTime);
            date = parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/



       /* //获取评论时间
        Date taskCommentTime = comment.getTaskCommentTime();
        //获取任务ID
        String taskId = task.getTaskId();
        //获取评论者姓名
        String realName = comment.getRealName();
        //获取评论的类型
        String commentType = comment.getCommentType();
        //获取评论内容
        String taskComment = comment.getTaskComment();
        //获取评论者的类型
        String commentatorType = comment.getCommentatorType();
        System.out.println(taskCommentTime);*/
        if (taskComment == null) {
            map.put("success", false);
            map.put("msg", "评论内容不能为空...");
            return null;
        }
        try {
            Boolean result = commentServer.CommentResult(taskId, commentatorId, commentType, taskComment);
            if(result == true){
                map.put("success",true);
                map.put("data",true);
            }else {
                map.put("success",false);
                map.put("msg","任务ID输入有误...");
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return map;
    }

}
