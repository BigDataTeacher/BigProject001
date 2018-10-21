package com.tecode.util.hbase.table;

/**
 * Created by Administrator on 2018/10/17.
 */

import com.tecode.bean.User;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016-11-23.
 */
public class HBaseUtils {
    private static Configuration cfg;
    private static Connection connection;

    private static Logger log = Logger.getLogger(HBaseUtils.class.getName());

    // private static final String HBASE_ZOOKEEPER_QUORUM = ConfigUtil.getString("hbase_zookeeper_quorum");
    //  private static final String ZOOKEEPER_ZNODE_PARENT = ConfigUtil.getString("zookeeper_znode_parent");

    static {
        cfg = HBaseConfiguration.create();
        //zk的集群地址
        // cfg.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
        //zk节点地址
        //  cfg.set("zookeeper.znode.parent", ZOOKEEPER_ZNODE_PARENT);
        try {
            //创建连接
            connection = ConnectionFactory.createConnection(cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // System.out.println(cfg.get("hbase.master"));
    }

    public static void closeConn() {
        try {
            connection.close();
        } catch (IOException e) {
            System.out.println("关闭连接失败");
            e.printStackTrace();
        }
    }

    //

    /**
     * 新建表
     *
     * @param tableName    表名称
     * @param columnFamily 列族
     * @return
     * @throws Exception
     */
    public static boolean create(String tableName, String... columnFamily)
            throws Exception {
        Admin admin = connection.getAdmin();
        //判断表是否存在
        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println(tableName + " exists!");
            return false;
        }
        //创建列族的描述器数组
        HColumnDescriptor[] hColumnDescriptor = new HColumnDescriptor[columnFamily.length];
        //遍历传入的列族
        for (int i = 0; i < hColumnDescriptor.length; i++) {
            hColumnDescriptor[i] = new HColumnDescriptor(columnFamily[i]);
        }
        //创建表描述器
        HTableDescriptor familyDesc = new HTableDescriptor(TableName.valueOf(tableName));
        //把列族描述器添加到表描述器中
        for (HColumnDescriptor columnDescriptor : hColumnDescriptor) {
            familyDesc.addFamily(columnDescriptor);
        }

        HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName), familyDesc);

        //创建表
        admin.createTable(tableDesc, genSpiltKeys(ConfigUtil.getInt("hbase.regions")));
       // System.out.println(tableName + " create successfully!");
        return true;
    }

    /**
     * 判断表是否存在
     *
     * @return
     * @throws IOException
     */
    public boolean isTableExists(String tableName) throws IOException {

        Admin admin = connection.getAdmin();
        boolean exists = admin.tableExists(TableName.valueOf(tableName));

        admin.close();
        return exists;
    }


    /**
     * regions： 分区的数量
     * 返回预分区号码
     */
    public static byte[][] genSpiltKeys(int regions) {

        //定义存放二维数组中的变量
        byte[][] spiltKeys = new byte[regions][];
        //返回分区号的格式
        DecimalFormat sf = new DecimalFormat("00");

        //定义一个treeSet的集合来对分区的编号进行排序，可以指定排序的规则。
        TreeSet<byte[]> tree = new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);

        //创建二维数组中的每个一位数组
        for (int i = 0; i < regions; i++) {
            //0 1 2, 3 4 5
            tree.add(Bytes.toBytes(sf.format(i) + "|"));  // 00 01 02 03 04 05
        }

        //把tree集合中的元素，添加到spiltKeys中
        int index = 0;
        for (byte[] bs : tree) {
            spiltKeys[index++] = bs;
        }

        return spiltKeys;
    }

    /**
     * 插入数据
     *
     * @param tablename    表名称
     * @param row          rowkey
     * @param columnFamily 列族
     * @param map 存放一个rowkey需要存储的所以列的数据  key为列名  value 为值。
     * @return
     * @throws Exception
     */
    public static boolean put(String tablename, String row, String columnFamily,Map<String,String> map
                              ) throws Exception {
        //创建表对象
        Table table = connection.getTable(TableName.valueOf(tablename));

        List<Put> puts = new ArrayList<>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            //创建Put对象，把rowkey作为参数
            Put put = new Put(Bytes.toBytes(row));
            //添加插入的列及值。
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(entry.getKey()),
                    Bytes.toBytes(entry.getValue()));

            puts.add(put);
        }


        //插入数据。
        table.put(puts);
        return true;
    }

    /**
     * 根据rowkey查询记录
     *
     * @param tablename
     * @param columnFamily
     * @param rowkey
     * @return
     * @throws Exception
     */
    public static User queryRowKey(String tablename, String columnFamily, String rowkey) throws Exception {
        Table table = connection.getTable(TableName.valueOf(tablename));
        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = table.get(get);
        return genObjet(result,User.class);


    }

    //单个qualifier的值等于
    public static User queryequal(String tablename, String columnFamily, String qualifier, String data) throws Exception {
        User user = null;


        //某列等于data的   添加过滤的添加   info:username =04123123
        Filter filter = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier),
                CompareFilter.CompareOp.EQUAL, Bytes.toBytes(data));


        FilterList filterList = new FilterList();
        filterList.addFilter(filter);


        Table table = connection.getTable(TableName.valueOf(tablename));
        Scan s = new Scan();
        s.setFilter(filterList);

        //添加查询的列族
        s.addFamily(Bytes.toBytes("info"));

        ResultScanner rs = table.getScanner(s);
        for (Result r : rs) {

            user =genObjet(r,User.class);
        }

        return user;
    }

    /**
     * 处理每个Cell
     * @param r
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T genObjet(Result r, Class<T> clazz) {
        T o = null;
        try {
            o = clazz.newInstance();

            Cell[] cells = r.rawCells();

            for (Cell cell : cells) {
                String columnName = Bytes.toString(CellUtil.cloneQualifier(cell));
                String columnValue = Bytes.toString(CellUtil.cloneValue(cell));

                String methodName = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);


                Method method = clazz.getMethod(methodName, String.class);
                method.invoke(o, columnValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  o;
    }


}