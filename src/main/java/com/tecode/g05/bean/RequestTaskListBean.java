package com.tecode.g05.bean;

import com.tecode.enumBean.TaskState;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

/**
 *  封装请求任务列表时传入的参数，封装时进行初步校验<br>
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class RequestTaskListBean {
    private String cusId;
    private TaskState taskState;
    private String queryStr;
    private int p = 1;

    /**
     * 用户ID
     */
    public String getCusId() {
        return cusId;
    }

    /**
     * 用户ID
     */
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    /**
     * 任务状态
     */
    public TaskState getTaskState() {
        return taskState;
    }

    /**
     * 任务状态
     */
    public void setTaskState(String taskState) {
        if(taskState == null) {
            this.taskState = TaskState.HANDLE;
        } else {
            this.taskState = TaskState.fromHandleState(taskState);
        }
    }

    /**
     * 搜索词
     */
    public String getQueryStr() {
        return queryStr;
    }

    /**
     * 搜索词
     */
    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }

    /**
     * 页码
     */
    public int getP() {
        return p;
    }

    /**
     * 页码
     */
    public void setP(String p) {
        Pattern pattern = Pattern.compile("^[\\d]*$");
        // 判断传入的字符串是否为null或者不是正整数
        if(p == null || !pattern.matcher(p).matches()) {
            this.p = 1;
        } else {
            this.p = Integer.parseInt(p);
        }
    }

    @Override
    public String toString() {
        return "RequestTaskListBean{" +
                "cusId='" + cusId + '\'' +
                ", taskState=" + taskState +
                ", queryStr='" + queryStr + '\'' +
                ", p=" + p +
                '}';
    }
}
