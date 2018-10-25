package com.tecode.g02.service;

/**
 * Created by Administrator on 2018/10/18.
 */



import com.tecode.exception.BaseException;

import java.io.IOException;

/**
 * @author zhangzhou
 * 处理用户信息相关的业务层
 */
public interface G02ZzReplyService {

    /**
     * 判断回复是否成功。
     * @param taskId:任务Id
     * @param cusId：当前登录用户Id
     * @return 返回true:表明回复成功，返回false:表明回复失败
     * @throws IOException：IO异常
     */
    boolean isReplySuccess(String taskId, String cusId) throws IOException;


    /**
     * 判断当前的用户是否是办理人
     * @param taskId: 任务Id
     * @param cusId： 用户Id
     * @return 返回true：表明当前登录用户是当前用户的办理人，返回false：表明当前登录用户不是当前任务的办理人
     * @throws Exception：抛出的是整个回复任务所有的异常
     */
    boolean isHandler(String taskId,String cusId) throws Exception;


}
