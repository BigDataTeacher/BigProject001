package com.tecode.util.hbase.table;

/**
 * Created by Administrator on 2018/10/17.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016-11-23.
 */
public class HBaseUtils {
    private static Configuration cfg;
    private final  static Connection connection;

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
            throw  new RuntimeException(e);
        }
       // System.out.println(cfg.get("hbase.master"));
    }


    /**
     * HBase的连接方法
     * @return
     */
    public static Connection getConnection(){
        return  connection;
    }


    /**
     * 在程序处理期间不用认为的关闭链接，
     */
    public static void closeConn() {
        try {
            connection.close();
        } catch (IOException e) {
            System.out.println("关闭连接失败");
            e.printStackTrace();
        }
    }


}