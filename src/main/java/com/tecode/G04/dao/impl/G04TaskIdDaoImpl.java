package com.tecode.G04.dao.impl;

import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.bean.Task;
import com.tecode.enumBean.CommentatorType;
import com.tecode.enumBean.TaskState;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G04TaskIdDaoImpl implements G04TaskIdDao {


    @Override
    public void modifyFinishState(String taskId,String cusId) throws Exception {


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
    public List<String> getIdStack(String taskId) throws Exception {
        List<String> list= new ArrayList<String>();
        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //得到GET对象
        Get get =new Get(Bytes.toBytes(taskId));

        //得到info列族下的handlerStack  办理人ID栈
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("info")),Bytes.toBytes("handlerStack\n"));

        Result resultHandlerStack  = table.get(get);

        Cell[] cellsHandlerStack = resultHandlerStack.rawCells();

        for (Cell cell : cellsHandlerStack) {
            list.add(Bytes.toString(CellUtil.cloneQualifier(cell)));

        }

        return list;
    }

    /**
     * 获取发起人ID
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public List<String> getSponsorId(String taskId) throws Exception {
        List<String> list= new ArrayList<String>();

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //Get对象
        Get get =new Get(Bytes.toBytes(taskId));

        //得到info列族下的sponsor  任务发起人
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("info")),Bytes.toBytes("sponsor"));

        Result result1SponsorId = table.get(get);

        Cell[] cellsSponsorId  = result1SponsorId.rawCells();

        for (Cell cell : cellsSponsorId) {
           list.add(Bytes.toString(CellUtil.cloneQualifier(cell)));
           System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
        }

        return list;
    }

    /**
     * 任务完成时间
     * @param taskId
     * @throws Exception
     */
    @Override
    public void taskFinishTime(String taskId) throws Exception {

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
     *
     * @param taskId
     * @throws Exception
     */
    @Override
    public void addComment(String taskId) throws Exception {
        String commentString ="系统_"+CommentatorType.SYSTEM.getType()+"_"+taskId+"_"+TaskState.FINISH.getType();

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //Pet对象
         Put put =new Put(Bytes.toBytes(taskId));

        //得到info列族下的comment  评论

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date =sdf.format(System.currentTimeMillis());
        put.addColumn(Bytes.toBytes("comment"),Bytes.toBytes(date),Bytes.toBytes(commentString));

        table.put(put);

    }

    @Override
    public void addLog(String taskId, String sponsor) throws Exception {

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //Pet对象
        Put put =new Put(Bytes.toBytes(taskId));

        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date =sdf.format(System.currentTimeMillis());
        put.addColumn(Bytes.toBytes("log"),Bytes.toBytes(date),Bytes.toBytes("完成"+":"+sponsor));

        table.put(put);

    }

    }



