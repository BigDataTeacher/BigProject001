package com.tecode.G03.dao.impl;

import com.tecode.G03.dao.G3TaskDao;
import com.tecode.bean.Task;
import com.tecode.bean.TaskComment;
import com.tecode.bean.TaskLog;
import com.tecode.bean.User;
import org.springframework.stereotype.Repository;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G3TaskDaoImpl implements G3TaskDao {


    @Override
    public void addTask(Task task) throws Exception {



    }

    @Override
    public void addComment(TaskComment coment) throws Exception{

    }

    @Override
    public void addLog(TaskLog log) throws Exception{

    }
}
