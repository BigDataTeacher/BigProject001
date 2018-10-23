package com.tecode.G06.dao.impl;

import com.tecode.G06.dao.G06AssignTaskDao;
import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.enumBean.TaskState;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 版本：2018/10/23 V1.0
 * Created by Administrator on 2018/10/23.
 */
public class G06AssignTaskDaoImpl implements G06AssignTaskDao {
    @Override
    public boolean assign(String taskId, String cusId, String handlerId) {
        try {
            // 获取下一个用户信息
            User nextUser = createUser(getRow("oa:user", handlerId));
            Connection connection = HBaseUtils.getConnection();
            // 获取表名
            TableName tn = TableName.valueOf("oa:task");
            // 获取表
            Table table = connection.getTable(tn);
            Get get = new Get(Bytes.toBytes(taskId));
            Result rs = table.get(get);
            // 获取Task信息
            Task task = createTask(rs);

            // 更改当前办理人
            insertData("oa:task", taskId,"info","nowHandler" ,nextUser.getName());
            // 更改办理人ID栈
            insertData("oa:task", taskId, "info", "handlerStack", task.getHandlerStack() + "," + nextUser.getUsername());
            // 添加组成员
            insertData("oa:task", taskId, "info", "memberIds", task.getMemberIds() + "," + nextUser.getUsername());
            // 添加日志
            insertData("oa:task", taskId, "log", new Date().getTime() + "", "消息：" + nextUser.getName() + "接到了转办任务");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 生成task
     * @param rs    查询出的单条数据
     * @return
     */
    public static Task createTask(Result rs) {
        Task task = new Task();
        Class  c = task.getClass();
        Cell[] cells = rs.rawCells();
        for (Cell cell : cells) {
            // 列族
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            // 列名
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            // rowKey
            String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
            // 值
            String value = Bytes.toString(CellUtil.cloneValue(cell));

            // 只需要info列族的数据
            if(family.equals("info") && !qualifier.equals("taskState")) {
                // 通过反射获取set方法
                Method method = null;
                try {
                    method = c.getMethod("set" + qualifier.substring(0, 1).toUpperCase() + qualifier.substring(1), String.class);
                    method.invoke(task, value);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else if(family.equals("info") && qualifier.equals("taskState")) {
                task.setTaskState(TaskState.fromHandleState(value));
            }
        }
        return task;
    }

    /**
     * 生成User
     *
     * @param cells 从HBase中读取的数据
     * @return 生成的User
     */
    public static User createUser(Cell[] cells) {
        User user = new User();
        Class c = user.getClass();
        // 存放用户的所有任务集合    key 为任务id   value 为这个任务的未读消息数量
        Map<String, Integer> userTasks = new HashMap();
        user.setUserTask(userTasks);
        for (Cell cell : cells) {
            // 列族
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            // 列名
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            // rowkey
            String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
            // 值
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            // info列族的调用Set方法
            if (family.equals("info")) {
                try {
                    // 通过反射获取set方法
                    Method method = c.getMethod("set" + qualifier.substring(0, 1).toUpperCase() + qualifier.substring(1), String.class);
                    method.invoke(user, value);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {    // tasks列族的保存到Map集合中
                userTasks.put(qualifier, Integer.parseInt(value));
            }
        }
        return user;
    }

    /**
     * 插入数据
     * @param tableName	表名
     * @param rowKey	行键
     * @param columnFamily	列族
     * @param columnName	列名
     * @param value	列值
     * @return	是否插入成功
     */
    public boolean insertData(String tableName, String rowKey, String columnFamily, String columnName,
                                     String value) {

        try {
            Connection connection = HBaseUtils.getConnection();
            // 获取表名
            TableName tn = TableName.valueOf(tableName);
            // 获取表
            Table table = connection.getTable(tn);
            Put put = new Put(Bytes.toBytes(rowKey));
            // 添加列族、列名、列值
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), Bytes.toBytes(value));
            table.put(put);
            table.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取数据
     * @param tableName	表名
     * @param rowKey	行键
     * @return	结果数组/null
     */
    public static Cell[] getRow(String tableName, String rowKey) {
        try {
            Connection connection = HBaseUtils.getConnection();
            // 获取表名
            TableName tn = TableName.valueOf(tableName);
            // 获取表
            Table table = connection.getTable(tn);
            Get get = new Get(Bytes.toBytes(rowKey));
            Result rs = table.get(get);
            return rs.rawCells();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
