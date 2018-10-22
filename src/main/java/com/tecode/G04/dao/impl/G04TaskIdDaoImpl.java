package com.tecode.G04.dao.impl;

import com.tecode.G04.dao.G04TaskIdDao;
import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.stereotype.Repository;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G04TaskIdDaoImpl implements G04TaskIdDao {


    @Override
    public Task modifyFinishState(String taskId,String cusId) throws Exception {

        //获得Hbase链接
        Connection conn = HBaseUtils.getConnection();
        //得到表
        Table table = conn.getTable(TableName.valueOf("task"));
        //g构建扫描器
       Scan scan = new Scan();



        Admin admin = conn.getAdmin();

        HTableDescriptor descriptor = admin.getTableDescriptor(TableName.valueOf("task"));




        Task task= new Task();
        //获得任务状态
        task.getTaskState();



        return null;
    }
}
