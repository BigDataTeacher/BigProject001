package com.tecode.g05.dao.impl;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.g05.bean.RequestTaskListBean;
import com.tecode.g05.dao.G05TaskDao;
import com.tecode.g05.util.G05CreateBean;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
@Repository
public class G05TaskDaoImpl implements G05TaskDao{
    /**
     * 表名
     */
    private static final String  TABLE_NAME = "oa:task";
    @Override
    public List<Task> getTasksByRequest(RequestTaskListBean rtb, User user) throws IOException {
        // 判断请求信息是否完整
        if(rtb == null || rtb.getTaskState() == null || rtb.getCusId() == null || rtb.getP() <= 0) {
            return null;
        }
        // 判断用户信息是否完整
        if(user == null || user.getUserTask() == null || user.getUserTask().size() == 0) {
            return null;
        }
        // -------------------获取数据-------------------
        // 获取连接
        Connection conn = HBaseUtils.getConnection();
        // 查询的表
        Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
        Scan scan = new Scan();
        // 添加过滤器
        scan.setFilter(getFilterList(rtb, user));
        // 查询数据
        ResultScanner scanner = table.getScanner(scan);
        // 查询出的Task列表，长度不会超过user.getUserTask().size()
        List<Task> tasks = new ArrayList<>(user.getUserTask().size());
        for (Result rs : scanner) {
            Task task = G05CreateBean.createTask(rs);
            tasks.add(task);
        }
        return tasks;
    }

    /**
     * 构建过滤器
     * @param rtb   保存了搜索字段，状态，页码等
     * @param user  保存了查询出的taskId列表
     * @return  过滤器列表
     */
    private FilterList getFilterList(RequestTaskListBean rtb, User user) {
        Set<String> taskIds = user.getUserTask().keySet();
        // 总过滤器列表
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        // 用于过滤一组taskID，taskId满足其中一个条件即通过
        FilterList taskIDFilterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        for (String taskId : taskIds) {
            // 过滤指定的ID
            Filter taskIdFlter = new RowFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(taskId)));
            // 添加到组ID过滤器
            taskIDFilterList.addFilter(taskIdFlter);
        }
        // 过滤状态
        // 过滤关键字

        // 如果有关键字，过滤关键字
        // 把taskID过滤器添加到总过滤器
        filterList.addFilter(taskIDFilterList);
        return filterList;
    }

}


















