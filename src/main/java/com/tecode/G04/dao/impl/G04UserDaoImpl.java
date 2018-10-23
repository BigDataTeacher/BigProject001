package com.tecode.G04.dao.impl;

import com.tecode.G04.dao.G04UserDao;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
@Repository
class G04UserDaoImpl implements G04UserDao {




    /**
     *评论成功后给每个任务成员ID的user表的消息未读数量加一条
     * @param username
     * @throws IOException
     */
    @Override
    public void modifymsgnumber(String username,int count) throws IOException {
        //获取hbase链接
        Connection conn = HBaseUtils.getConnection();
        //获得表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        //得到get对象

        Put put = new Put(Bytes.toBytes("taskid"));

        put.addColumn(Bytes.toBytes(ConfigUtil.getString("hbase_user_tbale_cf").split(",")[1]),Bytes.toBytes("taskid"),Bytes.toBytes(count));

    }

    /**
     * 根据username查询出消息未读数量
     * @param username
     * @return
     */
    @Override
    public Integer getmsgcount(String username) throws IOException {
        //获取hbase链接
        Connection conn = HBaseUtils.getConnection();
        //获得表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));

        //得到get对象
        Get get = new Get(Bytes.toBytes(username));
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("hbase_user_tbale_cf").split(",")[1]),Bytes.toBytes("taskid"));

        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        Integer count = 0;
        for (Cell cell : cells) {
            count = Bytes.toInt(CellUtil.cloneValue(cell));
        }
        return count;
    }

    /**
     *
     * @param userid
     * @return
     */
    @Override
    public String getName(String userid) throws IOException {
        //获取hbase链接
        Connection conn = HBaseUtils.getConnection();
        //获得表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        Get get = new Get(Bytes.toBytes(userid));
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("hbase_user_tbale_cf").split(",")[0]),Bytes.toBytes("name"));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        String Name = null;
        for (Cell cell : cells) {
            Name = Bytes.toString(CellUtil.cloneValue(cell));
        }
        return Name;

    }
}
