package com.tecode.g01.dao.impl;

import com.tecode.enumBean.CommentatorType;
import com.tecode.g01.dao.CopyDaoTask;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CopyDaoTaskImpl implements CopyDaoTask {
    private Connection conn = HBaseUtils.getConnection();
    //獲得task表和user表的列族
    private final String taskInfo = getCf("hbase_task_tbale_cf",0);
    private final String taskLog = getCf("hbase_task_tbale_cf",1);
    private final String taskComment = getCf("hbase_task_tbale_cf",2);
    private final String userTasks = getCf("hbase_user_tbale_cf",1);
    //活的表
    private final String taskTableName = ConfigUtil.getString("hbase_task_table_name");
    private final String userTableName = ConfigUtil.getString("hbase_user_tbale_name");



    //返回是否为0，来判断成功   添加成员是否成功
    //添加后的集合和前面的集合相减   >0则成功
    @Override
    public Integer putIntoMenment(String taskId, String memberId) throws IOException {
        //获取表
        Table taskTable = conn.getTable(TableName.valueOf(taskTableName));
        //获取表里的成员
        Set<String> before = getMembers(taskId, taskTable);
       // System.out.println("before"+before.size());

        addMember(before,memberId);




        //添加信息成员进入表


        Set<String> after = addMember(before, memberId);
       // System.out.println("after"+after.size());



        //

        taskTable.close();


        return after.size()-before.size();
    }

    /**
     * 用户表加入任务数量
     * @param taskId
     * @param memberId
     * @return
     * @throws IOException
     */
    @Override
    public Integer putIdintoUserTask(String taskId, String memberId) throws IOException {
        //System.out.println("putIdintoUserTask");
        Table userTable = conn.getTable(TableName.valueOf(userTableName));
        //获得tasks下的列
        Set<String> setbefore = getTasks(memberId, userTable);
        //插入列
        putTask(taskId,memberId,userTable);

        Set<String> setafter = getTasks(memberId, userTable);

        return setafter.size()-setbefore.size();
    }


    /**
     *添加日志
     */
    @Override
    public Integer addLog(String taskId, String username, String menberId) throws IOException {
        Table taskTable = conn.getTable(TableName.valueOf(taskTableName));

        int before =getLog(taskId,taskTable);
        Put put = new Put(Bytes.toBytes(taskId));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time =sdf.format(System.currentTimeMillis());
        String mess = username + "copy"+ taskId + "to" + menberId;
        put.addColumn(Bytes.toBytes(taskLog),Bytes.toBytes(System.currentTimeMillis()+""),Bytes.toBytes(mess));

        taskTable.put(put);

        int after =getLog(taskId,taskTable);
        taskTable.close();

        return after-before;
    }

    /**
     * 添加评论
     */
    @Override
    public Integer addComment(String taskId, String username, String menberId,boolean b) throws IOException {

        Table taskTable = conn.getTable(TableName.valueOf(taskTableName));
        int before = getComment(taskId,taskTable);

        Put put = new Put(Bytes.toBytes(taskId));
        //获得时间戳

        String mess1 = CommentatorType.SYSTEM+"_system_text_抄送成功";
        String mess2 = CommentatorType.SYSTEM+"_system_text_抄送失败";

        if(b){
             put.addColumn(Bytes.toBytes(taskComment),Bytes.toBytes(System.currentTimeMillis()+""),Bytes.toBytes(mess1));
        }
        put.addColumn(Bytes.toBytes(taskComment),Bytes.toBytes(System.currentTimeMillis()+""),Bytes.toBytes(mess2));
        taskTable.put(put);
        int after = getComment(taskId,taskTable);

        //System.out.println(after - before+"============");

        return after - before;

    }

    /**
     * 获取日志数量
     */
    public Integer getLog(String taskId,Table taskTable) throws IOException {
//创建一个集合存放查找到的日志
        Set<String> set =new HashSet<>();
        //创建一个get对象
        Get get=new Get(Bytes.toBytes(taskId));
        //设置要get哪一个列族下的内容
        get.addFamily(Bytes.toBytes(taskLog));
        //得到查询结果
        Result result = taskTable.get(get);
        //遍历结果
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            //将查询到的结果添加到set集合中
            set.add(Bytes.toString(CellUtil.cloneQualifier(cell)));
        }
        //返回集合的长度
        return set.size();
    }

    /**
     * 获取评论数量
     */
    public Integer getComment(String taskId,Table taskTable) throws IOException {
//创建一个集合存放查找到的日志
        Set<String> set =new HashSet<>();
        //创建一个get对象
        Get get=new Get(Bytes.toBytes(taskId));
        //设置要get哪一个列族下的内容
        get.addFamily(Bytes.toBytes("comment"));
        //得到查询结果
        Result result = taskTable.get(get);
        //遍历结果
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            //将查询到的结果添加到set集合中
            set.add(Bytes.toString(CellUtil.cloneQualifier(cell)));
        }
        //返回集合的长度
        return set.size();
    }


    /**
     * 查出已有成員，插入到一個集合(表，列族)
     */
    public Set<String> getMembers(String taskId, Table taskTable) throws IOException {
        //創建集合來存放所有的成員
        Set<String> set = new HashSet<>();
        //獲取Get對象
        Get get = new Get(Bytes.toBytes(taskId));
        get.addColumn(Bytes.toBytes(taskInfo),Bytes.toBytes("memberIds"));
        Result result = taskTable.get(get);
        Cell[] cells = result.rawCells();
        for (Cell c:cells) {
            String[] split = Bytes.toString(CellUtil.cloneValue(c)).split(",");
            for(String s:split){
                set.add(s);
            }
        }


        return set;
    }

    /**
     * 添加成員進入成員列表(元有的Set,表，任務id)
     */
    public void addMember(Set<String> set,Table taskTable,String taskId) throws IOException {
        List<Put> putList = new ArrayList<Put>();
        Put put = new Put(Bytes.toBytes(taskId));

        for(String s:set){

            put.addColumn(Bytes.toBytes(taskInfo),Bytes.toBytes("memberIds"),Bytes.toBytes(s));
            putList.add(put);
        }


        taskTable.put(putList);

    }

    /**
     * 添加成员的方法
     */
public Set<String> addMember(Set<String> set,String memberId){
    if(set.contains(memberId)){
        return set;
    }
    set.add(memberId);
    return set;
}



    /**
     * 獲取列族的方法
     */
    public String getCf(String columnName,int index){
        String cfs = ConfigUtil.getString(columnName);
        return cfs.split(",")[index];
    }





    //获取办理人，判断当前的是否是办理人里
    public Set<String> getHandler(String taskId) throws IOException {

        Table taskTable = conn.getTable(TableName.valueOf(taskTableName));
        //構建Get對象
        Get get = new Get(Bytes.toBytes(taskId));
        get.addColumn(Bytes.toBytes(taskInfo),Bytes.toBytes("handlerStack"));
        //構建Set
        Set<String> set = new HashSet<>();
        Result result = taskTable.get(get);
        Cell[] cells = result.rawCells();
        for(Cell c:cells){
            //獲得全部辦理人
            String handers = Bytes.toString(CellUtil.cloneValue(c));
            String[] spl = handers.split(",");
            for(String s:spl){
                set.add(s);
            }
        }


        return set;
    }

    /**
     * 获取user表userTasks列
     */
    public Set<String> getTasks(String userName,Table userTable)throws IOException{
        //创建Set存储列族下的列
        Set<String> set = new HashSet<>();
        //get对象
        Get get = new Get(Bytes.toBytes(userName));
        get.addFamily(Bytes.toBytes(userTasks));

        Result result = userTable.get(get);
        Cell[] cells = result.rawCells();

        for (Cell cell : cells) {
            //将查询到的所有列结果存入set集合中
            set.add(Bytes.toString(CellUtil.cloneQualifier(cell)));
        }
        return set;
    }


    /**
     * 向表里插入数据
     */
    public void putTask(String taskId,String memberId,Table userTable) throws IOException {
        Put put = new Put(Bytes.toBytes(memberId));
        put.addColumn(Bytes.toBytes(userTasks),Bytes.toBytes(taskId),Bytes.toBytes("1"));
        userTable.put(put);
        userTable.close();
    }




}
