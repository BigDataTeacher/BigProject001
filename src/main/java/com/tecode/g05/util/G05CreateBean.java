package com.tecode.g05.util;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.enumBean.TaskState;
import com.tecode.g05.bean.G05TaskBean;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建Bean
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class G05CreateBean {
    // 格式化日期
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
                int i = G05NumberUtil.isNumber(value) ? Integer.parseInt(value) : 0;
                userTasks.put(qualifier, i);
            }
        }
        return user;
    }

    /**
     * 生成task
     *
     * @param rs 查询出的单条数据
     * @return
     */
    public static Task createTask(Result rs) {
        Task task = new Task();
        Class c = task.getClass();
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
            if (family.equals("info") && !qualifier.equals("taskState")) {
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
            } else if (family.equals("info") && qualifier.equals("taskState")) {
                task.setTaskState(TaskState.fromHandleState(value));
            }
        }
        return task;
    }

    public static G05TaskBean createG05TaskBean(Task task) {
        G05TaskBean gtb = new G05TaskBean();
        gtb.setTaskId(task.getTaskId());
        gtb.setSponsor(task.getSponsor());
        gtb.setTaskTitle(task.getTaskTitle());
        gtb.setTaskState(task.getTaskState().getType());
        //****************剩余时限*****************
        gtb.setBalanceTime("不限");
        try {
            // 任务规定完成时间
            String timeLimit = task.getTimeLimit();
            Date limitDate = sdf.parse(timeLimit);
            // 当前时间
            Date nowDate = new Date();
            long time = limitDate.getTime() - nowDate.getTime();
            if (time > 0) {
                gtb.setBalanceTime(G05TimeUtil.getTime(time));
            }
        } catch (ParseException e) {
            // 日期格式错误则使用默认的"不限"
            // e.printStackTrace();
        }
        gtb.setTaskTag(task.getTaskTag());
        gtb.setIsHandle("false");
        if (task.getSponsor() != null && task.getSponsor().equals(task.getNowHandler())) {
            gtb.setIsHandle("true");
        }
        gtb.setNowHandle(task.getNowHandler());
        gtb.setTaskFinishTime(task.getFinishTime());
        return gtb;
    }
}
