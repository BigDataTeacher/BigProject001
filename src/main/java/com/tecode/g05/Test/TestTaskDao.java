package com.tecode.g05.Test;

import com.tecode.bean.User;
import com.tecode.enumBean.TaskState;
import com.tecode.g05.bean.RequestTaskListBean;
import com.tecode.g05.dao.impl.G05TaskDaoImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 版本：2018/10/23 V1.0
 * 小组：D01.G05
 * 成员：李晋、建晨飞
 * Created by Administrator on 2018/10/23.
 */
public class TestTaskDao {
    public static void main(String[] args) {
        G05TaskDaoImpl dao = new G05TaskDaoImpl();
        RequestTaskListBean rtb = new RequestTaskListBean();
        rtb.setCusId("04101266");
        rtb.setTaskState(TaskState.FINISH.getType());
        rtb.setP("1");
        rtb.setQueryStr("bbb");
        User user = new User();
        Map<String, Integer> tasks = new HashMap<>();
        user.setUserTask(tasks);
        tasks.put("03_04101266_1519921798654", 1);
        tasks.put("03_04101266_1530844692963", 1);
        tasks.put("03_04101266_1531018218221", 1);
        tasks.put("03_04101266_1534169502682", 1);
        try {
            dao.getTasksByRequest(rtb, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
