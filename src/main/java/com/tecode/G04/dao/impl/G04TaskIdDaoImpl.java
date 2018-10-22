package com.tecode.G04.dao.impl;

import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import com.tecode.enumBean.TaskState;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.ByteStringer;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import javax.sound.midi.MidiDevice;
import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.awt.SystemColor.info;
import static java.io.FileDescriptor.in;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G04TaskIdDaoImpl implements G04TaskIdDao {


    @Override
    public void modifyFinishState(String taskId,String cusId) throws Exception {

        Task task= new Task();
        //获得任务状态
        task.getTaskState();

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //g构建扫描器
       Scan scan = new Scan();
        //构建Put对象
        Put put =new Put(Bytes.toBytes(taskId));
        //修改那一列的值
        put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("taskState"),Bytes.toBytes(TaskState.FINISH.getType()));


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
        //g构建扫描器
        Scan scan = new Scan();
        //得到GET对象
        Get get =new Get(Bytes.toBytes(taskId));

        //得到info列族下的handlerStack  办理人ID栈
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("info")),Bytes.toBytes("handlerStack"));

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
        Task task= new Task();
        //获得任务状态
        task.getTaskState();

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
        }

        return list;
    }
}
