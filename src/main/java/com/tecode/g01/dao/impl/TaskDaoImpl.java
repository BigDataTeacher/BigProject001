package com.tecode.g01.dao.impl;

import com.tecode.bean.Task;
import com.tecode.enumBean.TaskState;
import com.tecode.g01.dao.TaskDao;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 *
 *   taskid  //任务id
 * Created by Administrator on 2018/10/22.
 */
public class TaskDaoImpl implements TaskDao{
    /**
     * 通过传入的任务id查询出相关的数据
     * @param taskid   //任务id
     * @return 返回一个cell集合储存数据
     * @throws IOException
     */
    Task task = new Task();
    //创建一个map集合，key 为列名，value 为列明对应的值
    Map<String,String> map = new HashMap<String,String>();
    @Override
    public Task getTaskBytaskId(String taskid,String username) throws IOException {
        Connection connection = HBaseUtils.getConnection();
        Table table = connection.getTable(TableName.valueOf("task"));
        //根据主键taskid查询
        Get get = new Get(Bytes.toBytes(taskid));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell :cells ) {
            map.put(CellUtil.cloneQualifier(cell).toString(),CellUtil.cloneValue(cell).toString());
        }

        task.setTaskId(taskid);         //任务Id
        task.setTaskTag(map.get("taskTag"));        //任务分类
        task.setTaskTitle(map.get("taskTitle"));        //任务标题
        task.setTaskDesc(map.get("taskDesc"));      //任务描述
        task.setSponsor(map.get("sponsor"));        //任务发起人
        task.setSponsorId(map.get("sponsorId"));         //任务发起人id
        task.setNowHandler(map.get("nowHandler"));       //当前办理人姓名
        //获取当前办理人id栈
        String handlerStack = map.get("handlerStack");
        task.setBeAssignId(handlerStack.substring(handlerStack.lastIndexOf(",")+1,handlerStack.length()));    //当前办理人ID
        task.setTimeLimit(map.get("timeLimit"));        //任务结束时间
        task.setTaskState(TaskState.fromHandleState(map.get("taskState")));        //任务状态
        task.setTaskTag(map.get("taskTag"));        //任务分类
        isAllowFinish(username,false);


        return task;
    }


    /**
     * 判断当前操作人是否是当前办理人,并且办理人id栈有且仅有一个
     * 默认flag为false，如果达成上述条件，flag改为true
     * 可以点击完成任务
     */
    private void isAllowFinish(String username,boolean flag) {

        String handlerStack = map.get("handlerStack");
        String substring = handlerStack.substring(handlerStack.lastIndexOf(","));
        String s = handlerStack.substring(handlerStack.lastIndexOf(",") + 1, handlerStack.length());
        flag = false;
        if(username.equals(s) && substring.length() == 1){
            flag = true;
        }else{
            return;

        }
    }


}