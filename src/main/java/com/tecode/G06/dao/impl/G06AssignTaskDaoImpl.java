package com.tecode.G06.dao.impl;


import com.tecode.G06.dao.G06AssignTaskDao;
import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

@Repository
public class G06AssignTaskDaoImpl implements G06AssignTaskDao{
    private Task task=new Task();
    private User user=new User();
    private String tablenamet="oa:task";
    private String tablename="oa:user";
    String key=null;
    String value=null;
    @Override
    public void putTaskbyUsername(String userName, String task) throws Exception {
        //添加任务ID在用户表中
        Connection con= HBaseUtils.getConnection();

        Table table=con.getTable(TableName.valueOf(tablename));
        Put put=new Put(Bytes.toBytes(userName));
        put.addColumn(Bytes.toBytes("tasks"),Bytes.toBytes(task),Bytes.toBytes("1"));
        table.put(put);

    }
//在用户表中查询出用户姓名
    @Override
    public User getUserbyUserId(String username) throws Exception {
        Connection con=HBaseUtils.getConnection();
        String tablename="oa:user";
        Table table = con.getTable(TableName.valueOf(tablename));
        Get get=new Get(Bytes.toBytes(username));
        Result result=table.get(get);
        Cell[] rawCells=result.rawCells();
        for (Cell cell : rawCells) {
            key=Bytes.toString(CellUtil.cloneQualifier(cell));
            value=Bytes.toString(CellUtil.cloneValue(cell));
            if(key.equals("name")){
                user.setName(value);
            }if(key.equals("username")){
                user.setUsername(value);
            }
        }
        return user;
    }
//通过任务ID查询出  nowHandler 当前办理人 handlerStack 办理人栈 memberIds 任务成员ID

    @Override
    public Task getTaskbyTaskId(String taskId) throws Exception {
        Connection con=HBaseUtils.getConnection();
        Table table=con.getTable(TableName.valueOf(tablenamet));
        Get get = new Get(Bytes.toBytes(taskId));
        Result result=table.get(get);
        Cell[] rawCells=result.rawCells();
        for (Cell cell : rawCells ) {
            key=Bytes.toString(CellUtil.cloneQualifier(cell));
            value=Bytes.toString(CellUtil.cloneValue(cell));
            if(key.equals("nowHandler")){
                task.setNowHandler(value);
            }if(key.equals("handlerStack")){
                task.setHandlerStack(value);
            }if(key.equals("memberIds")){
                task.setMemberIds(value);
            }
        }
        return task;
    }


    /**
     *
     * @param taskId 任务ID
     * @param nowHandler 当前办理人姓名
     * @param handlerStack 办理人栈
     * @param memberIds 任务成员ID
     * 将当前办理人覆盖为新的
     * 在办理人栈中添加新办理人
     * 在任务成员中添加新成员
     * @throws Exception
     */
    @Override
    public void inTaskbyTaskId(String taskId, String nowHandler, String handlerStack, String memberIds) throws Exception {
        Connection con = HBaseUtils.getConnection();
        Table table=con.getTable(TableName.valueOf(tablenamet));
        task=getTaskbyTaskId(taskId);
        Put put=new Put(Bytes.toBytes(taskId));
        String stack=task.getHandlerStack()+handlerStack;
        String member=task.getMemberIds()+memberIds;
        put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("nowHandler"),Bytes.toBytes(nowHandler));
        put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("handlerStack"),Bytes.toBytes(stack));
        put.addColumn(Bytes.toBytes("info"),Bytes.toBytes("memberIds"),Bytes.toBytes(member));

    }
}
