package com.tecode.g02.dao.impl;

import com.tecode.bean.Task;
import com.tecode.enumBean.CommentatorType;
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
 *   需要类上添加@Repository注解
 * @author zhangzhou
 */
@Repository
public class G02ReplyDaoImpl implements G02ReplyDao {
    private String info = getFamilyName("hbase_task_tbale_cf",0);
    private String comment = getFamilyName("hbase_task_tbale_cf",1);
    private String log = getFamilyName("hbase_task_tbale_cf",2);
    private Connection conn =HBaseUtils.getConnection();

    /**
     * 通过任务id查找到TaskBean对象
     */
    @Override
    public Task selectTaskByID(String taskId) throws IOException {
        Task task = new Task();
        task.setTaskId(taskId);


        Table table =  conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //创建一个Get对象
        Get get = new Get(Bytes.toBytes(taskId));

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
        //在get对象中添加当前办理人id：beAssignId
        get.addColumn(Bytes.toBytes(info),Bytes.toBytes("beAssignId"));
        Result result2 = table.get(get);
        Cell[] cells2 = result2.rawCells();
        for (Cell cell:cells2
                ) {
            task.setBeAssignId(Bytes.toString(CellUtil.cloneValue(cell)));
        }

        //在get对象中添加任务分类taskTag
        get.addColumn(Bytes.toBytes(info),Bytes.toBytes("taskTag"));
        Result result3 = table.get(get);
        Cell[] cells3 = result3.rawCells();
        for (Cell cell:cells3
                ) {
            task.setTaskTag(Bytes.toString(CellUtil.cloneValue(cell)));
        }
        //在get对象中添加当前办理人姓名
        get.addColumn(Bytes.toBytes(info),Bytes.toBytes("nowHandler"));
        Result result4 = table.get(get);
        Cell[] cells4 = result4.rawCells();
        for (Cell cell:cells4) {
            task.setNowHandler(Bytes.toString(CellUtil.cloneValue(cell)));
        }

        //在get对象中任务成员
        get.addColumn(Bytes.toBytes(info),Bytes.toBytes("memberIds"));
        Result result5 = table.get(get);
        Cell[] cells5 = result5.rawCells();
        for (Cell cell:cells5) {
            task.setMemberIds(Bytes.toString(CellUtil.cloneValue(cell)));
        }


        table.close();
        return task;

    }



    /**
     * 通过列族地址得到info列族
     * @param str:穿入的properties文件中列族的地址
     * @return：返回info列族
     */
    private String getFamilyName(String str,int index){
        String family=null;
        String s = ConfigUtil.getString(str);
        String[] split = s.split(",");
        family=split[index];
        return family;

    }

    /**
     * 在log中添加一个新列
     */
    @Override
    synchronized public void addReplyLog(String taskId,boolean bl,String cusId ) throws IOException {
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        String nowHandler = getName(cusId);
        String nextHandler = changeHandler(taskId);
        Put put = getPut(taskId,log,bl,nowHandler,nextHandler);
        table.put(put);
        table.close();
    }
    /**
     * 得到一个put对象
     */
    private Put getPut(String rowKey,String family,boolean bl,String nowHandler,String nextHandler){
        Put put = new Put(Bytes.toBytes(rowKey));
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long currentTime = System.currentTimeMillis();

        if(bl){
            put.addColumn(Bytes.toBytes(family),Bytes.toBytes(currentTime+""),Bytes.toBytes(CommentatorType.SYSTEM+"_system_text_"+nowHandler+" 回复:"+nextHandler+"  操作成功！"));
        }else {
            put.addColumn(Bytes.toBytes(family),Bytes.toBytes(currentTime +""),Bytes.toBytes(CommentatorType.SYSTEM+"_system_text_"+nowHandler+" 回复:"+nextHandler+"  操作失败！"));
        }
        return put;
    }

    /**
     * 在comment列族中添加一列，列名为当前时间，值为系统的评论
     */
    @Override
    synchronized public void addSystemComment(String taskId,boolean bl,String cusId) throws IOException {
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        String nowHandler = getName(cusId);
        String nextHandler = changeHandler(taskId);
        Put put = getPut(taskId,comment,bl,nowHandler,nextHandler);
        table.put(put);
        table.close();
    }
    /**
     * 将id栈中的栈顶元素移除
     */
    @Override
    public void removeIDFromStack(String taskId) throws IOException {
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        Task task = selectTaskByID(taskId);
        String handlerStack = task.getHandlerStack();
        String[] split = handlerStack.split(",");

        String tmp ="";
        for (int i = 0; i < split.length - 1; i++) {
            tmp += split[i]+",";
        }
        String newStack = tmp.substring(0,tmp.length()-1);

        Put put = new Put(Bytes.toBytes(taskId));
        put.addColumn(Bytes.toBytes(info),Bytes.toBytes("handlerStack"),Bytes.toBytes(newStack));
        table.put(put);
        table.close();
    }
    /**
     * 更改当前办理人的姓名
     */
    @Override
    public String changeHandler(String taskId) throws IOException {
        Task task = selectTaskByID(taskId);
        //获得当前的栈顶的对象
        String handlerStack = task.getHandlerStack();
        String[] split = handlerStack.split(",");
        String nowHandler = split[split.length-1];
        String name = getName(nowHandler);
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        Put put = new Put(Bytes.toBytes(taskId));
        put.addColumn(Bytes.toBytes(info),Bytes.toBytes("nowHandler"),Bytes.toBytes(name));
        table.put(put);
        table.close();
        return name;
    }

    /**
     * 在user表中通过用户Id查找到用户姓名
     * @param cusId:用户Id
     * @return：返回通过用户Id得到的用户姓名
     */
    private String getName(String cusId) throws IOException {
        Table user = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));
        Get getName = new Get(Bytes.toBytes(cusId));
        getName.addColumn(Bytes.toBytes(info),Bytes.toBytes("name"));
        Result result = user.get(getName);
        Cell[] cells = result.rawCells();
        String name= null;
        for (Cell cell : cells) {
            name =  Bytes.toString(CellUtil.cloneValue(cell));
        }
        return name;
    }

}
