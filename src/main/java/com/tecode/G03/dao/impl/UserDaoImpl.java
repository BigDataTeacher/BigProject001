package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.UserDao;
import com.tecode.bean.User;
import com.tecode.util.hbase.table.ConfigUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2018/10/22.
 */
public class UserDaoImpl implements UserDao {

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
//    @Override
//    public void addTask(String username,int num) throws IOException {
//        //获取文件系统
//        conf = HBaseConfiguration.create();
//
//        conn = ConnectionFactory.createConnection(conf);
//
//        Table tableUser = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
//
//        Put put = new Put(Bytes.toBytes(username));
//
//        Put putTask = put.addColumn(Bytes.toBytes("tasks"), Bytes.toBytes("taskId"),Bytes.toBytes(num));
//
//        tableUser.put(putTask);
//
//        Get get = new Get(Bytes.toBytes(username));
//
//        Result result = tableUser.get(get);
//
//        List<byte[]> list = new ArrayList<>();
//
//        Cell [] cells = result.rawCells();
//
//        for (Cell cell : cells) {
//
//            list.add(CellUtil.cloneValue(cell));
//
//        }
//
//    }
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
