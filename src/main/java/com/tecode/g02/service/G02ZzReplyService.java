package com.tecode.g02.service;

/**
 * Created by Administrator on 2018/10/18.
 */

import com.tecode.bean.User;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface G02ZzReplyService {
    /**
     * 判断回复是否成功。
     * @return
     */
    boolean isReplySuccess(String taskId,String cusId);

    /**
     *判断当前的用户是否是办理人
     * @return
     */
    boolean isHandler(String cusId,String handlerId);


}
