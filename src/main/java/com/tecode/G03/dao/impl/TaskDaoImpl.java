package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.TaskDao;
import com.tecode.G03.dao.UserDao;
import com.tecode.bean.Task;

import com.tecode.util.hbase.table.ConfigUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class TaskDaoImpl implements TaskDao {

    Configuration conf = null;

    Connection conn = null;

    @Autowired
    private UserDao userDao;


    @Override
    public Task updateTask(String taskId) throws Exception {
        Task task = new Task();

        //获得当前办理人姓名
        String nowHandler = getNowHandlerByTaskId(taskId);
        //获得接办人的name
        String handlerName = userDao.getUserByUserName(taskId);
        //获得评论
        String comment = modifyComment(taskId);
        //获得流转对象
        String log = addLog(taskId);





        return null;
    }

    @Override  //重写获得当前办理人姓名nowHandler方法
    public String getNowHandlerByTaskId(String taskId) throws IOException {
        //获取系统
        conf = HBaseConfiguration.create();

        conn = ConnectionFactory.createConnection(conf);
        //创建表对象，并且获得表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //创建Get对象
        Get get = new Get(Bytes.toBytes(taskId));

        //get.addFamily(Bytes.toBytes("info"));
        get.addColumn(Bytes.toBytes("info"),Bytes.toBytes("nowHandler"));

        Result result = table.get(get);

        Cell[] cells = result.rawCells();

        String nowHandler = null;

        for (Cell cell : cells) {

            nowHandler = Bytes.toString(CellUtil.cloneQualifier(cell));

        }

        return nowHandler;
    }

    @Override
    public String modifyComment(String taskId) throws IOException {


        return null;
    }

    @Override
    public String addLog(String taskId) throws IOException {


        return null;

    }
}
