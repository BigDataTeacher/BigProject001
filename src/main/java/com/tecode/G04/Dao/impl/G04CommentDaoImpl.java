package com.tecode.G04.Dao.impl;

import com.tecode.G04.Dao.G04CommentDao;
import com.tecode.bean.TaskComment;
import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.Method.get;


/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */


public class G04CommentDaoImpl implements G04CommentDao {
    //task列表的rowkye
//    private  String commentrowkey = "taskid";
//    private String commentfamily = "comment";
//    private String tablename = "task";

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

    /**
     * 查询出当前任务下的所有任务成员ID
     * @param taskid 任务成员ID
     * @return 返回成员ID组成的集合
     */
   @Override
    public  String getmerberID(String taskid) throws IOException {
        //获取链接
        Connection conn = HBaseUtils.getConnection();
        Table table = conn.getTable(TableName.valueOf(ConfigUtil.getString("hbase_task_table_name")));
        //构建表描述器
        Scan scan = new Scan();
        //得到get对象
        Get get = new Get(Bytes.toBytes(taskid));
        //设置获得哪一个列祖下的哪一列
        get.addColumn(Bytes.toBytes(ConfigUtil.getString("hbase_task_tbale_cf")),Bytes.toBytes("memberlds"));
        Result result = table.get(get);

        return result.toString();
    }



}

