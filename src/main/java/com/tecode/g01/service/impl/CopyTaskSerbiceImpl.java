package com.tecode.g01.service.impl;

import com.tecode.g01.dao.CopyDaoTask;
import com.tecode.g01.service.CopyTaskService;
import org.apache.hadoop.mapred.IFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/24.
 */
@Service
public class CopyTaskSerbiceImpl implements CopyTaskService {
    @Autowired
    private CopyDaoTask copydao;



    @Override
    public boolean toNext(String taskId, String memberId, String username) throws IOException {

        Integer taskcount = copydao.putIntoMenment(taskId, memberId);
        Integer usercount = copydao.putIdintoUserTask(taskId, memberId);

        if ((taskcount == 0 && usercount > 0) || usercount > 0) {
            int logs = copydao.addLog(taskId, username, memberId);
            if (logs > 0) {
                return true;
            }
        }


        return false;
    }

    @Override
    public boolean isHandler(String taskId, String username) throws IOException {
        Set<String> handler = copydao.getHandler(taskId);
        if(handler.contains(username)){
            return true;
        }
        return false;
    }
}
