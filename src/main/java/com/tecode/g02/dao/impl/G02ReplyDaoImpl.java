package com.tecode.g02.dao.impl;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.g02.dao.G02ReplyDao;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G02ReplyDaoImpl implements G02ReplyDao {




    @Override
    public Task selectTaskByID(String userId) throws IOException {
        Task task = new Task();
        Connection conn =HBaseUtils.getConnection();
        Table table =  conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //创建一个Get对象
        Get get = new Get(Bytes.toBytes(userId));
        String info = getFamilyName("hbase_task_tbale_cf");
        //在get对象中添加列族和列的信息，指定查找对应的列族的列，这次查找的是info列族的handlerStack列
        get.addColumn(Bytes.toBytes(info),Bytes.toBytes("handlerStack"));
        //通过get对象得到一个result对象
        Result result1 = table.get(get);
        //通过result对象得到cells集合
        Cell[] cells1 = result1.rawCells();
        //遍历cells集合
        for (Cell cell:cells1) {
            byte[] bytes = CellUtil.cloneValue(cell);
            task.setHandlerStack(Bytes.toString(bytes));
        }
        //在get对象中添加当前办理人列：beAssignId
        get.addColumn(Bytes.toBytes(info),Bytes.toBytes("beAssignId"));
        Result result2 = table.get(get);
        Cell[] cells2 = result2.rawCells();
        for (Cell cell:cells2
             ) {
            task.setBeAssignId(Bytes.toString(CellUtil.cloneValue(cell)));
        }
        //
        return task;
    }

    /**
     * 通过列族地址得到info列族
     * @param str:穿入的properties文件中列族的地址
     * @return：返回info列族
     */
    private String getFamilyName(String str){
        String family=null;
        String s = ConfigUtil.getString(str);
        String[] split = s.split(",");
        family=split[0];
        return family;

    }


    @Override
    public void addReplyLog(String replyLog) {

    }

    @Override
    public void addSystemComment(String systemComm) {

    }

    @Override
    public void removeIDFromStack() {

    }

    @Override
    public void changeHandler(String userId) {

    }
}
