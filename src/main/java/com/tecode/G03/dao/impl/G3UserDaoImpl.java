package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.G3UserDao;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户数据处理层的具体实现
 * <p>
 * 需要类上添加@Repository注解
 */
@Repository
public class G3UserDaoImpl implements G3UserDao {

    /**
     * @param usernames 用户名的集合
     * @return names 以用户名为key，名字为value的map集合
     * @throws Exception
     */
    @Override
    public Map<String, String> getNameByUserName(List<String> usernames) throws IOException {
        Map<String, String> names = new HashMap<>();
        //获取链接
        Connection conn = HBaseUtils.getConnection();
        //获取表的对象
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        //获取配置文件中表的列族的信息
        String[] hbase_user_tbale_cfs = ConfigUtil.getString("hbase_user_tbale_cf").split(",");
        String family = hbase_user_tbale_cfs[0];
        //遍历传入的用户名集合，依次获得其名字
        for (String username : usernames) {
            //构建Get对象
            Get get = new Get(Bytes.toBytes(username));
            //向Get对象添加列族与列名信息
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes("name"));
            //获得结果
            Result result = table.get(get);
            Cell[] rawCells = result.rawCells();
            //遍历结果，获取名字
            for (Cell rawCell : rawCells) {
                String name = Bytes.toString(CellUtil.cloneValue(rawCell));
                //将用户名与名字分别作为key和value存入map集合
                names.put(username, name);
            }
        }



        //返回map集合
        return names;
    }

    @Override
    public void addUserTasks(String userid, String taskid, int num) throws IOException {
        //获取链接
        Connection conn = HBaseUtils.getConnection();
        //获取表的对象
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        //获取配置文件中的列族名
        String[] hbase_user_tbale_cfs = ConfigUtil.getString("hbase_user_tbale_cf").split(",");
        String tasks = hbase_user_tbale_cfs[1];
        //构建Put对象
        Put put = new Put(Bytes.toBytes(userid));
        put.addColumn(Bytes.toBytes(tasks), Bytes.toBytes(taskid), Bytes.toBytes(num));
        //向表中添加信息
        table.put(put);

    }
}
