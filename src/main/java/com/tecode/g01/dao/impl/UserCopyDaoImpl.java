package com.tecode.g01.dao.impl;

import com.tecode.g01.dao.UserCopyDao;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Repository
public class UserCopyDaoImpl implements UserCopyDao {
    @Override
    public boolean addTask(String taskId) throws Exception {
        //建立存放任务的集合
        Set<String> task = new HashSet<>();
        //获取链接
        Table userTable = HBaseUtils.getConnection().getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        //获得use表的username字段s
        Get get = new Get(Bytes.toBytes("username"));
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
