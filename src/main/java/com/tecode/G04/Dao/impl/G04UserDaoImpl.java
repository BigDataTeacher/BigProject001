package com.tecode.G04.Dao.impl;

import com.tecode.G04.Dao.G04UserDao;
import com.tecode.bean.User;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
class G04UserDaoImpl implements G04UserDao {


    /**
     *根据评论者id获得评论者姓名
     * @param userid 评论者id
     * @return
     */
    @Override
    public String getName(String userid) throws IOException {
        //获取hbase链接
        Connection conn = HBaseUtils.getConnection();
        //获得表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        //构建表描述器
        Scan sc = new Scan();
        //得到get对象
        Get get = new Get(Bytes.toBytes(userid));
        //设置获得哪一个列祖下的哪一列
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("hbase_user_tbale_cf")),Bytes.toBytes("name"));
        Result Name = table.get(get);
        return Name.toString();
    }

    @Override
    public void modifymsgnumber(String username) throws IOException {
        //获取hbase链接
        Connection conn = HBaseUtils.getConnection();
        //获得表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        //得到get对象
        Get get = new Get(Bytes.toBytes(username));
        //设置获得哪一个列祖下的哪一列
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("hbase_user_tbale_cf")),Bytes.toBytes("name"));
    }
}
