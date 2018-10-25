package com.tecode.G04.dao.impl;

import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.enumBean.CommentatorType;
import com.tecode.enumBean.TaskCommentType;
import com.tecode.enumBean.TaskState;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现具体的任务方法
 * @author  liJun
 */


@Repository
public class G04TaskIdDaoImpl implements G04TaskIdDao {


    @Override
    public void modifyFinishState(String taskId,String cusId) throws IOException {


        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //构建Put对象
        Put put =new Put(Bytes.toBytes(taskId));
        //修改那一列的值
        put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("taskState"),Bytes.toBytes(TaskState.FINISH.getType()));

        table.put(put);
    }

    /**
     *获取栈ID
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public String getIdStack(String taskId) throws IOException {
            String str = null;

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //得到GET对象
        Get get =new Get(Bytes.toBytes(taskId));

        //得到info列族下的handlerStack  办理人ID栈
       get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("handlerStack"));


        Result resultHandlerStack  = table.get(get);

        Cell[] cellsHandlerStack = resultHandlerStack.rawCells();

        for (Cell cell : cellsHandlerStack) {
           str=Bytes.toString(CellUtil.cloneValue(cell));

        }

        return str;
    }

    /**
     *  得到发起人和发起人ID
     * @param taskId
     * @return
     * @throws IOException
     */
    @Override
    public List<String> getSponsorIdAndSponsor(String taskId) throws IOException {
        //声明一个集合用户返回
        List<String> list=new ArrayList<>();

        //存放发起人ID
        String strSponsorId=null;
        //存放发起人
        String strSponsor=null;

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));


        //Get对象
        Get get =new Get(Bytes.toBytes(taskId));

        //得到info列族下的sponsor  任务发起人
        get.addColumn(Bytes.toBytes("info"),Bytes.toBytes("sponsor"));

        Result result = table.get(get);

        Cell[] cells = result.rawCells();

        for (Cell cell : cells) {
            strSponsor= Bytes.toString(CellUtil.cloneValue(cell));
        }


        //得到info列族下的sponsorID  任务发起人id
        get.addColumn(Bytes.toBytes("info"),Bytes.toBytes("sponsorId"));

        Result result1SponsorId = table.get(get);

        Cell[] cellsSponsorId  = result1SponsorId.rawCells();

        for (Cell cell : cellsSponsorId) {
            strSponsorId=(Bytes.toString(CellUtil.cloneValue(cell)));
        }

        //添加发起人
        list.add(strSponsor);
        //添加发起人ID
        list.add(strSponsorId);

        return list;
    }

    /**
     * 任务完成时间
     * @param taskId
     * @throws Exception
     */
    @Override
    public void taskFinishTime(String taskId) throws IOException {

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //Put对象
        Put put =new Put(Bytes.toBytes(taskId));

        //得到info列族下的finishTime  完成时间
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date =sdf.format(System.currentTimeMillis());

        put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("finishTime"),Bytes.toBytes(date));

        table.put(put);

    }

    /**
     *添加评论对象
     * @param taskId
     * @throws Exception
     */
    @Override
    public void addComment(String taskId) throws IOException {
        String commentString ="系统_"+CommentatorType.SYSTEM.getTypeName()+"_"+ TaskCommentType.TEXT.getTypeName() +"_"+TaskState.FINISH.getTypeName();

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //Put对象
         Put put =new Put(Bytes.toBytes(taskId));

        //添加info列族下的comment  评论

        put.addColumn(Bytes.toBytes("comment"),Bytes.toBytes(Long.toString(System.currentTimeMillis())),Bytes.toBytes(commentString));
        System.out.println(commentString);
        table.put(put);

    }

    /**
     * 添加日志消息
     * @param taskId
     * @param sponsor
     * @throws IOException
     */

    @Override
    public void addLog(String taskId,String sponsor) throws IOException {

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));


        //Put对象
        Put put =new Put(Bytes.toBytes(taskId));

        //添加log列族下的时间戳
        long l = System.currentTimeMillis();
        String s = Long.toString(l);

        put.addColumn(Bytes.toBytes("log"),Bytes.toBytes(s),Bytes.toBytes("完成"+":"+sponsor));

        table.put(put);

    }



}



