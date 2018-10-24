package com.tecode.G06.dao.impl;

import com.tecode.G06.dao.G06UserDao;
import com.tecode.bean.User;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;


/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G06UserDaoImpl implements G06UserDao {

    User user=null;
    String name=null;
    String value=null;
    @Override
    public User getUserByUserName(String username) throws Exception {
        Connection con =HBaseUtils.getConnection();
        String tablename="oa:user";
        Table table = con.getTable(TableName.valueOf(tablename));
        Get get=new Get(Bytes.toBytes(username));
        user=new User();
        Result result=table.get(get);
        Cell[] rawCells=result.rawCells();

        for (Cell cell : rawCells) {
            name=Bytes.toString(CellUtil.cloneQualifier(cell));
            value=Bytes.toString(CellUtil.cloneValue(cell));



        if(name.equals("username")) {
            user.setUsername(value);
        }if(name.equals("password")){
            user.setPassword(value);
        }if(name.equals("name")){
            user.setName(value);
        }if(name.equals("department")){
            user.setDepartment(value);
            }
        }
        return user;
    }



}
