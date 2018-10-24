package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.G03CYUserDao;
import com.tecode.util.hbase.table.ConfigUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2018/10/22.
 */
@Repository
public class G03CYUserDaoImpl implements G03CYUserDao {

    Configuration conf = null;
    Connection conn = null;

    @Override  //重写获得name方法
    public String getNameByUserName(String username) throws IOException {
        //获取系统
        conf = HBaseConfiguration.create();

        conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));

        Get get = new Get(Bytes.toBytes(username));

        //get.addFamily(Bytes.toBytes("tasks"));
        get.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));

        Result result = table.get(get);

        Cell [] cells = result.rawCells();

        String qualifier = null;

        for (Cell cell : cells) {

            qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));

        }

        return qualifier;
    }

    @Override
    public Map<String,Integer> getNumOfTaskMsg(List<String> usernames, String taskId) throws IOException {
        //获取文件系统
        conf = HBaseConfiguration.create();
        conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        Map<String, Integer> numMap = new HashMap<>();
        List<Get> gets = new ArrayList<>();
        String [] family = ConfigUtil.getString("hbase_user_tbale_cf").split(",");
        for (String username : usernames) {
            Get get = new Get(Bytes.toBytes(username));
            get.addColumn(Bytes.toBytes(family[1]), Bytes.toBytes(taskId));
            gets.add(get);
        }
        Result[] results = table.get(gets);
        for (Result result : results) {
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                numMap.put(Bytes.toString(CellUtil.cloneRow(cell)), Bytes.toInt(CellUtil.cloneValue(cell)));
            }
        }
        return numMap;
    }

    @Override
    public void modifyNumOfTaskMsg(Map<String, Integer> map, String taskId) throws IOException {
        //获取文件系统
        conf = HBaseConfiguration.create();
        conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        List<Put> putNum = new ArrayList<>();
        String[] family = ConfigUtil.getString("hbase_user_tbale_cf").split(",");
        Set<String> usernames = map.keySet();
        for (String username : usernames) {
            Put putNames = new Put(Bytes.toBytes(username));
            putNames.addColumn(Bytes.toBytes(family[1]), Bytes.toBytes(taskId), Bytes.toBytes(map.get(username)));
            putNum.add(putNames);
        }
        table.put(putNum);
    }
}
