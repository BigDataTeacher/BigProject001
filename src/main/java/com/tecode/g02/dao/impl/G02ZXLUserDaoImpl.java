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
import java.util.ArrayList;
import java.util.List;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 *
 *
 *   根据姓名查找用户的具体实现类
 */
@Repository
public class G02ZXLUserDaoImpl implements G02ZXLUserDao {


    @Override
    public List<User> findUserByName(String name) throws IOException {

        //创建一个user对象的List集合来存储查询的结果
        List<User> userList=new ArrayList<User>();

        //获取链接
        Connection conn=HBaseUtils.getConnection();

        //获取user表对象
        Table userTable = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_user_tbale_name")));

        //创建扫描器
        Scan scan=new Scan();

        //创建单列值过滤器，过滤选择info列族，name列的值
        SingleColumnValueFilter scvf = new SingleColumnValueFilter(
                                                                    Bytes.toBytes(getFamilyName()),
                                                                    Bytes.toBytes("name"),
                                                                    CompareFilter.CompareOp.EQUAL,
                                                                    Bytes.toBytes(name));
        //在扫描器中设置过滤器
//        scan.addColumn(Bytes.toBytes(getFamilyName()),Bytes.toBytes("name"));
        scan.setFilter(scvf);





        //使用设置好的扫描器扫描user表，并获得扫描结果
        ResultScanner scanner = userTable.getScanner(scan);
        System.out.println(scanner);


        //遍历扫描结果
        for (Result rs :scanner) {

            //获取每一行的扫描结果
            Cell[] cells = rs.rawCells();

            //遍历每一行的结果
            for (Cell cell:cells) {

                byte[] bytes = CellUtil.cloneQualifier(cell);
                String cl=Bytes.toString(bytes);




                    //将获取到的每一行的值封装为一个user对象并添加进userList中
                    User user = new User();

                    //获取到用户名
                    String userName = Bytes.toString(CellUtil.cloneRow(cell));

                    //设置用户名
                    user.setUsername(userName);

                    //获取并设置姓名
                    user.setName(Bytes.toString(CellUtil.cloneValue(cell)));

                    //因为不需要密码，这里直接传一个空值
                    user.setPassword("");

                    //通过获取到的用户名调用获取部门的方法
                    user.setDepartment(getDepartment(userName, userTable));

                    System.out.println(user);

                    //将获取到的uesr对象添加进user集合中
                    userList.add(user);


            }


        }

        //返回封装了user对象的List集合
        return userList;

    }

    /**
     * 获取部门名称的方法
     * @param userName 用户名
     * @param userTable 表对象
     * @return  部门名称
     * @throws IOException
     *
     * 根据传入的用户名和表对象查询出部门名称
     */
    private String getDepartment(String userName, Table userTable) throws IOException {
        //先对用户名进行判断，如果为空则直接返回null
        if(userName==null||userName.equals("")){
            return null;
        }
        //设置一个变量来接受部门名称
        String department="";

        //创建一个get对象，rowkey为传入的username
        Get get=new Get(Bytes.toBytes(userName));
        //设置要获取的内容，info列族里的department列的
        get.addColumn(Bytes.toBytes(getFamilyName()),Bytes.toBytes("department"));

        //查询出这个值，并得到查询结果
        Result result = userTable.get(get);

        //获取结果的每一行
        Cell[] cells = result.rawCells();

        //遍历每一行的结果
        for (Cell cell:cells) {
            //将获取到的值（也就是部门名称）付给department变量
            department=Bytes.toString(CellUtil.cloneValue(cell));
        }
        //返回department变量
        return department;


    }


    /**
     * 获取info列族的方法
     * @return
     */
    private String getFamilyName(){
        //获取用户信息列族
        String cfs = ConfigUtil.getString("hbase_user_tbale_cf");

        //将获取到的多个列族进行切割并取出用户信息info的列族
        String fimalyName=cfs.split(",")[0];

        return fimalyName;
    }
}
