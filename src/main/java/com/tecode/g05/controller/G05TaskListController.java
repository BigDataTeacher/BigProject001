package com.tecode.g05.controller;

import com.tecode.g05.bean.RequestTaskListBean;
import com.tecode.g05.bean.UpdateTaskBean;
import com.tecode.g05.service.G05TaskService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
@Controller
class G05TaskListController {
    @Autowired
    private G05TaskService taskService;

    /**
     * 请求列表失败时的返回数据
     */
    private static Map<String, Object> taskListFail = new HashedMap();
    static {
        taskListFail.put("success", false);
        taskListFail.put("data", null);
        taskListFail.put("totalPage", -1);
    }
    /**
     * <pre>
     * 请求任务列表功能模块：
     * 1. 用户登录后默认显示当前用户待处理任务列表
     * 2. 可选显示历史任务列表。
     * 3. 可以按关键字查询任务列表。关键字可以匹配：标题、描述、发起人、任务标签。
     * 4. 向上滑动可翻页。
     * 请求时需要接收的参数：
     *  {
     *      cusId://用户id，如果ID为null或者用户ID在数据库中不存在则会返回请求失败
     *      taskState://任务状态，"处理中"或"已完成"，不填默认为"处理中"，填写其他字段则返回请求失败
     *      queryStr://搜索词，不填则代表不需要进行关键词搜索
     *      p://页码，不填或者传入的参数为非数字则默认为第一页，页码超标则会返回请求失败
     *  }
     * 请求以如下json格式回复：
     *{
     *     "success":true,
     *     "data":{
     *          "list":[{
     *          "taskId":"9d3bd3348b214e12ac4a674a7488dc57",//任务id
     *          "sponsor":"李小灵",//发起人
     *          "taskTitle":"测试创建任务",//任务标题
     *          "taskState":"HANDLE",//任务状态
     *          "balanceTime":"不限"//剩余时间
     *          "taskTag":"通用"//任务标签
     *          "isHandle":true/false;//true-表示当前办理人为当前登录用户，false-表示当前办理人不是当前登录用户
     *          "nowHandle":"李小灵"//当前办理人
     *          "unreadMsgCount":1;//未读消息数量
     *          "taskFinishTime"://任务完成时间-历史记录才有
     *     }],
     *     "totalPage":1// 总页数
     *     }
     *}</pre>
     * @param rtb 接收请求参数的Bean，传入的参数将会封装到此Bean中并进行初步验证
     * @return 请求回复
     */
    @ResponseBody
    @RequestMapping(value = "/task-list", method = RequestMethod.POST)
    public Map<String, Object> getTaskList(RequestTaskListBean rtb) {
        // 如果没有传入用户ID，或者传入的任务状态参数不合法，则返回请求错误
        if(rtb.getCusId() == null || rtb.getTaskState() == null) {
            return taskListFail;
        }

        // 查询数据
        taskService.getTaskList(rtb);

        return taskListFail;
    }


    /**
     * <pre>
     * 请求修改任务，传入参数：
     * {
     * taskId;// 任务id，不存在则会返回请求失败
     * sponsorId;// 任务发起人ID，用于校验是否有权限修改任务结束时间
     * userID;// 当前操作用户ID
     * taskTitle;// 任务标题，为null表示不修改此字段
     * taskDesc;// 任务描述，为null表示不修改此字段
     * taskEndTime;// 任务结束时间--只有发起人能改
     * }
     * 返回参数：
     * 成功{"success":true,"data":true}
     * 失败{"success":false,"msg":"错误原因"}
     * </pre>
     * @param utb 用于封装请求参数的bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update-task", method = RequestMethod.POST)
    public Map<String, Object> updateTask(UpdateTaskBean utb) {
        System.out.println(utb);
        return taskListFail;
    }
}
