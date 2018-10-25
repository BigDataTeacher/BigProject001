package com.tecode.G06.service;

import com.tecode.bean.Task;
import com.tecode.bean.User;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/10/23.
 * 处理任务交办业务流程
 *
 */

public interface G06AssignTaskService {
    /**
     * 通过传入的TaskID查询任务中的当前办理人
     */
    // Task getTaskbyTaskId(String taskId)throws Exception;
    /**
     * 通过传入的用户ID查询办理人姓名name
     */
    // User getUserbyUserId(String username)throws Exception;
    /**
     * 判断用户ID是否是任务查询中查出的当前办理人
     * 是才能继续操作
     * 不是则跳出
     */
    boolean handler(String taskId,String userId)throws Exception;
    /**
     * 将当前办理人ID存入班里人ID栈handlerStack
     * 将办理人名字存入当前办理人nowHandler
     * 将办理人存入任务成员IDmemberlds
     * taskId://任务id
     cusId://⽤户id
     handlerId://下⼀个办理⼈id
     */
    boolean inHandlerId(String taskId,String handlerId,String userId)throws Exception;
    /**
     * 将任务ID加入下一个班里人用户任务IDtasks
     *
     */
    boolean inTask(String taskId,String handlerId)throws Exception;
}


