package com.tecode.G04.Dao;

import com.tecode.bean.User;
import com.tecode.util.hbase.table.HBaseUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/22.
 */
public interface G04UserDao {
    String getName(String userid) throws IOException;

}
