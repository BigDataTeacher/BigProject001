package com.tecode.g05.dao;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import com.tecode.g05.bean.RequestTaskListBean;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public interface G05TaskDao {
    /**
     * 根据请求条件获取对应的Task集合
     * @param rtb   请求条件
     * @param user  用户信息
     * @return
     */
    List<Task> getTasksByRequest(RequestTaskListBean rtb, User user) throws IOException;
}
