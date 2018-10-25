package com.tecode.g02.dao.impl;

import com.tecode.bean.User;
import com.tecode.g02.dao.G02ZXLUserDao;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据处理层的具体实现
 * <p>
 * 需要类上添加@Repository注解
 * <p>
 * <p>
 * 根据姓名查找用户的具体实现类
 */
@Repository
public class G02ZXLUserDaoImpl implements G02ZXLUserDao {


    @Override
    public List<User> findUserByName(String name) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //创建一个user对象的List集合来存储查询的结果
        List<User> userList = new ArrayList<User>();

        //获取链接
        Connection conn = HBaseUtils.getConnection();

        //获取user表对象
        Table userTable = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));

        //创建扫描器
        Scan scan = new Scan();

        //创建单列值过滤器，过滤选择info列族，name列的值
        SingleColumnValueFilter scvf = new SingleColumnValueFilter(
                Bytes.toBytes(getFamilyName()),
                Bytes.toBytes("name"),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(name));
        //在扫描器中设置要查询的列
        scan.addColumn(Bytes.toBytes(getFamilyName()), Bytes.toBytes("name")).
                addColumn(Bytes.toBytes(getFamilyName()), Bytes.toBytes("department")).
                addColumn(Bytes.toBytes(getFamilyName()), Bytes.toBytes("username"));

        //设置过滤器
        scan.setFilter(scvf);

        //使用设置好的扫描器扫描user表，并获得扫描结果
        ResultScanner scanner = userTable.getScanner(scan);

        //遍历扫描结果
        for (Result rs : scanner) {
            //将获取到的每一行的值封装为一个user对象并添加进userList中
            User user = new User();

            //获得user对象的类对象
            Class<? extends User> userClazz = user.getClass();

            //获取每一行的扫描结果
            Cell[] cells = rs.rawCells();

            //遍历每一行的结果
            for (Cell cell : cells) {
                //获取列名
                byte[] bytes = CellUtil.cloneQualifier(cell);
                String cl = Bytes.toString(bytes);

                //获取对应列的对应值
                String value = Bytes.toString(CellUtil.cloneValue(cell));

                //设置user对象的set方法的表达式
                String mysetName = "set" + cl.substring(0, 1).toUpperCase() + cl.substring(1);

                //获取user类的方法
                Method method = userClazz.getMethod(mysetName, String.class);

                //执行
                method.invoke(user, value);
            }
            //将user对象存入list集合中
            userList.add(user);
        }

        //返回封装了user对象的List集合
        return userList;

    }


    /**
     * 获取info列族的方法
     *
     * @return 列族名 info
     */
    private String getFamilyName() {
        //获取用户信息列族
        String cfs = ConfigUtil.getString("hbase_user_tbale_cf");

        //将获取到的多个列族进行切割并取出用户信息info的列族
        String fimalyName = cfs.split(",")[0];

        return fimalyName;
    }
}
