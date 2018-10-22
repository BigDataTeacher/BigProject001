package com.tecode.g01.dao.impl;

import com.tecode.bean.Task;
import com.tecode.g01.dao.TaskDao;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 *
 *   taskid  //任务id
 * Created by Administrator on 2018/10/22.
 */
public class TaskDaoImpl implements TaskDao{
    /**
     * 通过传入的任务id查询出相关的数据
     * @param taskid   //任务id
     * @return 返回一个cell集合储存数据
     * @throws IOException
     */

    @Override
    public Task getTaskBytaskId(String taskid) throws IOException {
        Connection connection = HBaseUtils.getConnection();
        Table table = connection.getTable(TableName.valueOf("task"));
        //根据主键taskid查询
        Get get = new Get(Bytes.toBytes(taskid));
        Result result = table.get(get);





        return null;
    }


}