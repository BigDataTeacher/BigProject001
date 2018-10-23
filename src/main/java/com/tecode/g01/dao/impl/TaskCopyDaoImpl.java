package com.tecode.g01.dao.impl;

import com.tecode.bean.Task;
import com.tecode.g01.dao.TaskCopyDao;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.HashSet;
import java.util.Set;

/**
 *memberId字段加入新员工
 */
public class TaskCopyDaoImpl implements TaskCopyDao{


    /**
     *
     * @param taskId    任务ID
     * @param memberId  需要加入的成员ID
     * @return          true/false
     * @throws Exception
     */

    @Override
    public boolean insertTaskMember(String taskId,String memberId) throws Exception {

        //Set<String> set = new HashSet<>();
        Connection conn = HBaseUtils.getConnection();
        //获得表
        Table taskTable = conn.getTable(TableName.valueOf("task"));
        //获得taskid  （rwokey）的所有信息
        Get get = new Get(Bytes.toBytes("taskId"));
        //获得指定memberIds字段
        get.addColumn(Bytes.toBytes("info"),Bytes.toBytes("memberIds"));
        //获得结果集
        Result result = taskTable.get(get);
        Cell[] cells = result.rawCells();
        String s = null;
        String newMember = null;

        for (Cell cell:cells) {
            s = Bytes.toString(CellUtil.cloneValue(cell));
            newMember = s + "," + memberId;

        }
        //put对象提交
        Put put = new Put(Bytes.toBytes("taskId"));

        put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("memberIds"), Bytes.toBytes( newMember));

        taskTable.put(put);
        return true;


    }



}
