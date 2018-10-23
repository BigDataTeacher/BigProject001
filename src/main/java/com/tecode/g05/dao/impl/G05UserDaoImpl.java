package com.tecode.g05.dao.impl;

import com.tecode.bean.User;
import com.tecode.g05.dao.G05UserDao;
import com.tecode.g05.util.G05CreateBean;
import com.tecode.g05.util.G05HBaseTableUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
@Repository
public class G05UserDaoImpl  implements G05UserDao{
    /**
     * 表名
     */
    private static final String  TABLE_NAME = "oa:user";
    @Override
    public User getUserByUserId(String userId) {
        // 查询用户信息
        Cell[] cells = G05HBaseTableUtil.getRow(TABLE_NAME, userId);
        // 生成对应的Bean
        User user =  G05CreateBean.createUser(cells);
        return user;
    }
}
