package com.tecode.g05.dao.impl;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.enumBean.TaskCommentType;
import com.tecode.enumBean.TaskState;
import com.tecode.exception.BaseException;
import com.tecode.g05.bean.G05TaskBean;
import com.tecode.g05.bean.RequestTaskListBean;
import com.tecode.g05.bean.UpdateTaskBean;
import com.tecode.g05.dao.G05TaskDao;
import com.tecode.g05.filter.KeywordFilter;
import com.tecode.g05.util.G05CreateBean;
import com.tecode.g05.util.G05HBaseTableUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.exceptions.HBaseException;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
@Repository
public class G05TaskDaoImpl implements G05TaskDao {
    /**
     * 表名
     */
    private static final String TABLE_NAME = "oa:task";

    @Override
    public List<G05TaskBean> getTasksByRequest(RequestTaskListBean rtb, User user) throws IOException {
        // 判断请求信息是否完整
        if (rtb == null || rtb.getTaskState() == null || rtb.getCusId() == null || rtb.getP() <= 0) {
            return null;
        }
        // 判断用户信息是否完整
        if (user == null || user.getUserTask() == null || user.getUserTask().size() == 0) {
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
        List<G05TaskBean> requestBeans = new ArrayList<>(user.getUserTask().size());
        for (Result rs : scanner) {
            Task task = G05CreateBean.createTask(rs);
            G05TaskBean taskBean = G05CreateBean.createG05TaskBean(task);
            // 设置未读消息数
            taskBean.setUnreadMsgCount(user.getUserTask().get(task.getTaskId()) + "");
            requestBeans.add(taskBean);
        }

        // 过滤状态
        requestBeans = taskStateFilter(requestBeans, rtb.getTaskState());
        // 过滤关键字
        requestBeans = keywordFilter(requestBeans, rtb.getQueryStr());
        return requestBeans;
    }

    @Override
    public boolean updateTask(UpdateTaskBean utb) throws BaseException {
        G05HBaseTableUtil.insertData("oa:task", utb.getTaskId(), "info", "taskTitle", utb.getTaskTitle());
        G05HBaseTableUtil.insertData("oa:task", utb.getTaskId(), "info", "taskDesc", utb.getTaskDesc());
        if (utb.getCusId() == utb.getSponsorId()) {
            G05HBaseTableUtil.insertData("oa:task", utb.getTaskId(), "info", "timeLimit", utb.getTaskEndTime());
        }
        G05HBaseTableUtil.insertData("oa:task", utb.getTaskId(), "log", new Date().getTime() + "", "系统消息_系统_" + TaskCommentType.TEXT.getType() + "_" + utb.getSponsorId() + "修改了任务");
        return true;
    }

    /**
     * 过滤状态
     *
     * @param list 过滤列表
     * @param ts   状态
     * @return
     */
    private List<G05TaskBean> taskStateFilter(List<G05TaskBean> list, TaskState ts) {
        return list.stream().filter((gtb) -> gtb.getTaskState().equals(ts.getType())).collect(Collectors.toList());
    }

    /**
     * 过滤关键字
     *
     * @param list    过滤列表
     * @param keyword 关键字
     * @return
     */
    private List<G05TaskBean> keywordFilter(List<G05TaskBean> list, String keyword) {
        return list.stream().filter((gtb) -> KeywordFilter.filter(gtb, new String[]{"sponsor", "taskTitle", "taskTag"}, keyword, KeywordFilter.Operator.MUST_PASS_ONE)).collect(Collectors.toList());
    }

    /**
     * 构建过滤器
     *
     * @param rtb  保存了搜索字段，状态，页码等
     * @param user 保存了查询出的taskId列表
     * @return 过滤器列表
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


        // 把taskID过滤器添加到总过滤器
        filterList.addFilter(taskIDFilterList);
        return filterList;
    }

}


















