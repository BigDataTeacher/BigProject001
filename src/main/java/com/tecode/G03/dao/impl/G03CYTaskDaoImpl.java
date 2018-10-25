package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.G03CYTaskDao;
import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.bean.TaskLog;
import com.tecode.enumBean.TaskState;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.*;

/**
 * 用户数据处理层的具体实现
 * <p>
 * 需要类上添加@Repository注解
 */
@Repository
public class G03CYTaskDaoImpl implements G03CYTaskDao {

    Configuration conf = null;

    Connection conn = null;

    @Override
    public void updateTask(Task task) throws IOException {
        Map<String, String> map = new HashMap<>();
        //获取链接
        conn = HBaseUtils.getConnection();
        //获取表的对象
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //任务ID
        String taskId = task.getTaskId();

        //当前办理人姓名
        map.put("nowHandler", task.getNowHandler());
        //办理人ID栈
        map.put("handlerStack", task.getHandlerStack());
        //任务成员ID
        map.put("memberIds", task.getMemberIds());
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        //获取列族名
        String[] hbase_task_tbale_cfs = ConfigUtil.getString("hbase_task_tbale_cf").split(",");
        String cf = hbase_task_tbale_cfs[0];
        //创建Put集合
        List<Put> puts = new ArrayList<Put>();
        //遍历map集合
        for (Map.Entry<String, String> entry : entrySet) {
            //构建Put对象
            Put put = new Put(Bytes.toBytes(taskId));
            //添加列族等信息
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
            //将Put对象加入集合
            puts.add(put);
        }
        //批量添加信息
        table.put(puts);

    }

    @Override
    public void addComment(String taskId, Set<TaskComment> comments) throws IOException {
        Map<String, String> commentMap = new HashMap<>();
        conn = ConnectionFactory.createConnection(conf);
        //获取表的对象
        Table tableTask = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        for (TaskComment comment : comments) {
            String key = Long.toString(comment.getTaskCommentTime().getTime());
            String value = comment.getRealName() + "_" +
                    comment.getCommentatorType() + "_" +
                    comment.getCommentType() + "_" +
                    comment.getTaskComment();
            commentMap.put(key, value);
        }
        Set<Map.Entry<String, String>> entrySet = commentMap.entrySet();
        //获取列族名
        String[] hbase_task_tbale_cfs = ConfigUtil.getString("hbase_task_tbale_cf").split(",");
        String cf = hbase_task_tbale_cfs[2];
        List<Put> puts = new ArrayList<Put>();
        for (Map.Entry<String, String> entry : entrySet) {
            Put put = new Put(Bytes.toBytes(taskId));
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
            puts.add(put);
        }
        tableTask.put(puts);
    }

    @Override
    public void addLog(String taskId, Set<TaskLog> logs) throws IOException {
        Map<String, String> logMap = new HashMap<>();
        conn = ConnectionFactory.createConnection(conf);
        //获取表的对象
        Table tableTask = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        for (TaskLog log : logs) {
            String key = Long.toString(log.getLogTime().getTime());
            //格式为： log类型：动作:任务创建人：接办人
            String value = log.getCommentatorType().getType() + ":" + log.getContent();
            logMap.put(key, value);
        }
        Set<Map.Entry<String, String>> entrySet = logMap.entrySet();
        //获取列族名
        String[] hbase_task_tbale_cfs = ConfigUtil.getString("hbase_task_tbale_cf").split(",");
        String cf = hbase_task_tbale_cfs[1];
        List<Put> puts = new ArrayList<Put>();
        for (Map.Entry<String, String> entry : entrySet) {
            Put put = new Put(Bytes.toBytes(taskId));
            put.addColumn(Bytes.toBytes(cf), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
            puts.add(put);
        }
        tableTask.put(puts);
    }

    @Override
    public Task getTaskByTaskId(String taskId) throws IOException {
        //获取系统
        conf = HBaseConfiguration.create();

        conn = ConnectionFactory.createConnection(conf);

        Task task = new Task();
        //创建表对象，并且获得表
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        //创建Get对象
        Get get = new Get(Bytes.toBytes(taskId));

        get.addFamily(Bytes.toBytes("info"));

        Result result = table.get(get);

        Map<String, String> taskMap = new HashMap<>();

        Cell[] cells = result.rawCells();

        for (Cell cell : cells) {
            taskMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
        }
        String value = null;
        for (String key : taskMap.keySet()) {
            value = taskMap.get(key);
            if (key.equals("taskId")) {
                task.setTaskId(value);
            } else if (key.equals("taskTag")) {
                task.setTaskTag(value);
            } else if (key.equals("taskTitle")) {
                task.setTaskTitle(value);
            } else if (key.equals("taskDesc")) {
                task.setTaskDesc(value);
            } else if (key.equals("taskState")) {
                task.setTaskState(TaskState.fromHandleState(value));
            } else if (key.equals("sponsor")) {
                task.setSponsor(value);
            } else if (key.equals("sponsorId")) {
                task.setSponsorId(value);
            } else if (key.equals("nowHandler")) {
                task.setNowHandler(value);
            } else if (key.equals("handlerStack")) {
                task.setHandlerStack(value);
            } else if (key.equals("createTime")) {
                task.setCreateTime(value);
            } else if (key.equals("timeLimit")) {
                task.setTimeLimit(value);
            } else if (key.equals("memberIds")) {
                task.setMemberIds(value);
            }
        }
        return task;
    }
}
