package com.tecode.G04.dao.impl;

import com.tecode.bean.TaskComment;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;


/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */


public class G04CommentDaoImpl implements G04CommentDao {
    //task列表的rowkye
    private  String commentrowkey = "taskid";
    private String commentfamily = "comment";
    private String tablename = "task";

    /**
     *
     * @param taskid 任务ID
     * @param userid 用户ID
     * @param commentType 内容类型
     * @param comment  评论内容
     * @return
     */

    @Override
    public Boolean addcomment(String taskid, String userid, String commentType, String comment) throws IOException {
        Boolean flag = false;
        /**
         * 获得当前时间的时间戳作为列名
         */
        long date = System.currentTimeMillis();

        /**
         * 根据用户ID调用G04UserDaoImpl的getname()返回用户姓名
         */
        G04UserDaoImpl userdao = new G04UserDaoImpl();
        String Name = userdao.getName(userid);

        /**
         *  插入的value值：评论人ID，评论人姓名，评论类型，评论内容组成的字符串，用逗号隔开
         */

        String value =taskid+","+Name+","+commentType+","+comment;

        /**
         * 获取Hbase链接
         */

        Connection conn = HBaseUtils.getConnection();

        //构建put对象
        Put put = new Put(Bytes.toBytes( taskid ));

        //使用addColumn()方法 来设置需要在哪个列族的列新增值
        put.addColumn(Bytes.toBytes(ConfigUtil.getString("hbase_task_tbale_cf")),Bytes.toBytes(date),Bytes.toBytes(value));

        //使用Connection连接对象调用getTable(TableName tableName) 来获得Table的对象
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));

        table.put(put);

        flag = true;

        return flag;
    }



}

