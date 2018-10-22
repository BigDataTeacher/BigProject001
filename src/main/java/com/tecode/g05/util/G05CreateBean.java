package com.tecode.g05.util;

import com.tecode.bean.User;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建Bean
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class G05CreateBean {
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
}
