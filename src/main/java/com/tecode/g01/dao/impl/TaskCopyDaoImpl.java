package com.tecode.g01.dao.impl;

import com.tecode.bean.Task;
import com.tecode.enumBean.CommentatorType;
import com.tecode.g01.dao.TaskCopyDao;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *memberId字段加入新员工
 */
@Repository
public class TaskCopyDaoImpl implements TaskCopyDao{



    /**
     *在任务表的memberIds修改并加入memberid字段
     * @param taskId    任务ID
     * @param memberId  需要加入的成员ID
     * @return          true/false
     * @throws Exception
     */

    @Override
    public boolean insertTaskMember(String taskId,String memberId) throws Exception {



        Set<String> set = new HashSet<>();


        Connection conn = HBaseUtils.getConnection();
        //获得表
        Table taskTable = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //获得taskid  （rwokey）的所有信息
        Get get = new Get(Bytes.toBytes("taskId"));
        //获得指定memberIds字段
        get.addColumn(Bytes.toBytes(getCf(0)),Bytes.toBytes("memberIds"));
        //获得结果集
        Result result = taskTable.get(get);
        Cell[] cells = result.rawCells();
        String s = null;
        String newMember =null;


        List<Put> list = new ArrayList<>();

        for (Cell cell:cells) {
            s = Bytes.toString(CellUtil.cloneValue(cell));
//            newMember = s + "," + memberId;
//            set.add(s);
            Put put = new Put(Bytes.toBytes("taskId"));
            put.addColumn(Bytes.toBytes(getCf(0)), Bytes.toBytes("memberIds"), Bytes.toBytes(s));
            list.add(put);
        }
        set.add(memberId);


        //put对象提交




        taskTable.put(list);
        return true;


    }

    /**
     * 添加操作日志
     * @param taskId
     * @throws IOException
     */
    @Override
    public void addLog(String taskId,String memberId) throws IOException {
        Connection conn = HBaseUtils.getConnection();
        Table taskTable = conn.getTable(TableName.valueOf("hbase_task_table_name"));

        Put put = new Put(Bytes.toBytes("taskId"));
        //获得
        CommentatorType systype = CommentatorType.SYSTEM;

        //发送者,当前办理人
        Task task = new Task();
        String beAssignId = task.getBeAssignId();


        //接受者
        System.out.println(systype+":"+beAssignId+":"+memberId);




    }




    /**
     * 获取列族
     */
    @Override
    public String getCf(int n) {
        String cf = ConfigUtil.getString("hbase_task_tbale_cf");
        String[] split = cf.split(",");
        return split[n];
    }






}
