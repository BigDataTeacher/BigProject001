package com.tecode.G06.dao.impl;

import com.tecode.G06.dao.G06UserDao;
import com.tecode.bean.User;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.Configuration;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G06UserDaoImpl implements G06UserDao {
    HBaseUtils hbu= new HBaseUtils();

    @Override
    public User getUserByUserName(String username) throws Exception {


        return null;
    }
}
