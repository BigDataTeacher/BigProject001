package com.tecode.g02.service;

/**
 * Created by Administrator on 2018/10/18.
 */



import com.tecode.exception.BaseException;

import java.io.IOException;

/**
 *
 * 处理用户信息相关的业务层
 */
public interface G02ZzReplyService {
    /**
     * 判断回复是否成功。
     * @return
     */
    boolean isReplySuccess(String taskId, String cusId) throws IOException;

    /**
     *判断当前的用户是否是办理人
     * @return
     */
    boolean isHandler(String taskId, String cusId) throws Exception;


}
