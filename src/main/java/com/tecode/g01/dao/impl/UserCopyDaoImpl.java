package com.tecode.g01.dao.impl;

import com.tecode.g01.dao.UserCopyDao;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/22.
 */
public class UserCopyDaoImpl implements UserCopyDao {
    @Override
    public boolean addTask(String username, String column,String taskId) throws Exception {
        //建立存放任务的集合
        List<String> task = new ArrayList<>();
        //获取链接
        Connection conn = HBaseUtils.getConnection();
        Table userTable = conn.getTable(TableName.valueOf("user"));
        //获得use表的username字段
        Get get = new Get(Bytes.toBytes(username));
        get.addColumn(Bytes.toBytes("tasks"),Bytes.toBytes(taskId));

        Result result = userTable.get(get);
        Cell[] cells = result.rawCells();

        for (Cell cell : cells) {
            String s = Bytes.toString(CellUtil.cloneValue(cell));
            //先把遍历出来的任务放入集合
            task.add(s);
        }
        //最后把要加入的任务ID加入集合
        task.add(taskId);

        return true;
    }
}
