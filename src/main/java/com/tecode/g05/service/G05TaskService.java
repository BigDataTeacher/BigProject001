package com.tecode.g05.service;

import com.tecode.g05.bean.G05TaskBean;
import com.tecode.g05.bean.RequestTaskListBean;
import java.util.Set;

/**
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public interface G05TaskService {
     Set<G05TaskBean> getTaskList(RequestTaskListBean rtb);
}
