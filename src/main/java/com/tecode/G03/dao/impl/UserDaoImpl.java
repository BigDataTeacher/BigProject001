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

/**
 * Created by Administrator on 2018/10/22.
 */
public class UserDaoImpl implements UserDao {

    Configuration conf = null;
    Connection conn = null;

    @Override  //重写获得name方法
    public String getUserByUserName(String username) throws Exception {
        //获取系统
        conf = HBaseConfiguration.create();

        conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));

        Get get = new Get(Bytes.toBytes(username));

        get.addFamily(Bytes.toBytes("tasks"));

        Result result = table.get(get);

        Cell [] cells = result.rawCells();

        String qualifier = null;

        for (Cell cell : cells) {

            qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));

        }

        return qualifier;
    }

    @Override
    public void addTask(String username) {

    }

    @Override
    public String modifyNumOfTaskMsg(String username) {



        return null;
    }
}
