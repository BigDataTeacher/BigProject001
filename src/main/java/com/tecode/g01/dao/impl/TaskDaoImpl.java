package com.tecode.g01.dao.impl;

import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.enumBean.CommentatorType;
import com.tecode.enumBean.TaskCommentType;
import com.tecode.enumBean.TaskState;
import com.tecode.exception.BaseException;
import com.tecode.g01.dao.TaskDao;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 *
 *   taskid  //任务id
 * Created by Administrator on 2018/10/22.
 */
@Repository
public class TaskDaoImpl implements TaskDao{
    /**
     * 通过传入的任务id查询出相关的数据
     * @param taskid   //任务id
     * @return 返回一个cell集合储存数据
     * @throws IOException
     */
    Task task =null ;

    //comment列族的map集合，key为列明，value为列名对应的值
    Map<String,String> commentMap = null;
    //创建一个存放评论内容的set集合
    TreeSet<TaskComment> set = null;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //字符串转Date型
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Task getTaskBytaskId(String taskid,String username) throws IOException, BaseException {
        Set<TaskComment> commentSet = new TreeSet<>();
        task =  new Task();
        //创建一个map集合，key 为列名，value 为列名对应的值
        Map<String,String> infoMap = new HashMap<String,String>();

        Connection connection = HBaseUtils.getConnection();
        Table tasktable = connection.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //根据主键taskid查询
        Get get = new Get(Bytes.toBytes(taskid));
        //返回一个result结果集
        Result result = tasktable.get(get);
        if(result.isEmpty()){
            throw new BaseException("该任务不存在。。。");
        }
        get.addFamily(Bytes.toBytes(getCf(0)));
        Cell[] Infocells = result.rawCells();
        //遍历cell数组，把列名当做key，对应的列名的值作value存入infoMap集合中
        for (Cell cell :Infocells ) {
            infoMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)),Bytes.toString(CellUtil.cloneValue(cell)));
        }
        task.setTaskId(taskid);         //任务Id
        task.setTaskTag(infoMap.get("taskTag"));        //任务分类
        task.setTaskTitle(infoMap.get("taskTitle"));        //任务标题
        task.setTaskDesc(infoMap.get("taskDesc"));      //任务描述
        task.setSponsor(infoMap.get("sponsor"));        //任务发起人
        task.setSponsorId(infoMap.get("sponsorId"));         //任务发起人id
        task.setNowHandler(infoMap.get("nowHandler"));       //当前办理人姓名
        //获取当前办理人id栈
        task.setHandlerStack(infoMap.get("handlerStack"));
        String handlerStack = infoMap.get("handlerStack");
        String[] handlerStackArr = handlerStack.split(",");
        task.setBeAssignId(handlerStackArr[handlerStackArr.length -1]);    //当前办理人ID


        task.setTimeLimit(infoMap.get("timeLimit"));        //任务结束时间
        task.setTaskState(TaskState.fromHandleState(infoMap.get("taskState")));        //任务状态
        task.setTaskTag(infoMap.get("taskTag"));        //任务分类
        boolean b = isAllowFinish(username,infoMap);
        task.setAllowFinish(b);     //是否可以完成任务

        task.setTaskComments(getTaskCommentBytaskId(taskid));       //任务评论


        task.setCreateTime(infoMap.get("createTime"));      //任务发起时间

        task.setFinishTime(infoMap.get("finishTime"));      //任务完成时间
        task.setMemberIds(infoMap.get("memberIds"));        //任务成员ID

        return task;

    }

    /**
     * 通过任务id查询task表comment列族的信息
     * @param taskid 任务id
     * @return   返回一个set集合，储存评论信息
     * @throws IOException
     */
    @Override
    public  TreeSet<TaskComment> getTaskCommentBytaskId(String taskid) throws IOException {
        //储存评论内容的set集合
        set = new TreeSet<TaskComment>();
        commentMap = new HashMap<String,String>();
        TaskComment comment = null;
        Connection connection = HBaseUtils.getConnection();
        Table tasktable = connection.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //根据主键taskid查询
        Get get = new Get(Bytes.toBytes(taskid));

        get.addFamily(Bytes.toBytes(getCf(2)));


        Result result = tasktable.get(get);

        Cell[] commentCells = result.rawCells();
        for (Cell cell : commentCells) {
            commentMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)),Bytes.toString(CellUtil.cloneValue(cell)));

        }


        //遍历commetMap集合获取一条评论记录
       Set<Map.Entry<String, String>> entrySet = commentMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            comment= new TaskComment();
            String value = entry.getValue();

            if (value == null || value.equals("") || value.split("_").length <4 ){
                continue;
            }

            String[] split = value.split("_");
            String commenter = split[0];        //评论人姓名
            String commentorType = split[1];       //评论人类型
            String commentType = split[2];      //评论类型
            String commentMessage = split[3];       //评论内容
            String s = entry.getKey();

            try {
                //System.out.println("s:" + s);
                Date date = null;
                try {
                    date = new Date(Long.valueOf(s));
                }catch (Exception e){}

                comment.setTaskCommentTime(date);           //评论时间
                comment.setTaskComment(commentMessage);       //评论内容
                String s1 = isUserOrSystem(commentorType);
                comment.setCommentatorType(s1);               //评论者类型
                String commentMessaeType = isCommentType(commentType);
                comment.setCommentType(commentMessaeType);           //评论内容类型
                comment.setRealName(commenter);      //评论人名

                set.add(comment);

            } catch (Exception e) {

               e.printStackTrace();
            }
        }

        return set;
    }

    /**
     * 获取列族
     */
    @Override
    public String getCf(int index) {
        String cf = ConfigUtil.getString("hbase_task_tbale_cf");

        return cf.split(",")[index];
    }




    /**
     * 判断一条评论内容的类型
     */
    private String isCommentType(String comment){
        TaskCommentType taskCommentType = TaskCommentType.fromTaskCommentType(comment);
        if(taskCommentType == null){
            return null;
        }else{
            return taskCommentType.getType();
        }
    }


    /**
     * 判断一条评论内容评论人是系统还是员工
     */
    private String isUserOrSystem(String name){

        CommentatorType type = CommentatorType.fromCommentatorType(name);
        if(type == null){
            return null;
        }else{
            return type.getType();
        }


    }
    /**
     * 判断当前操作人是否是当前办理人,并且办理人id栈有且仅有一个
     * 默认flag为false，如果达成上述条件，flag改为true
     * 可以点击完成任务
     */
    private boolean isAllowFinish(String username,Map<String,String > infoMap) {

        String handlerStack = infoMap.get("handlerStack");
        String[] handlerStackArry = handlerStack.split(",");
        String s = handlerStackArry[handlerStackArry.length-1];

        if(username.equals(s) && handlerStackArry.length == 1){
            return  true;
        }else{
            return  false;
        }
    }



}