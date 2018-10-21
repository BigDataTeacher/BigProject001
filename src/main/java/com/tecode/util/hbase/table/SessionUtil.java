package com.tecode.util.hbase.table;

import com.tecode.bean.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/10/21.
 */
public class SessionUtil {

    /**
     *  获得当前登录的用户对象
     * @param session   当前的回话对象
     * @return
     */
    public static User getLogingUser(HttpSession session){
        return (User)(session.getAttribute("loginUser"));
    }

    /**
     * 保存当前登录人
     * @param session
     * @param user
     */
    public static  void setLoginUser(HttpSession session,User user){
            session.setAttribute("loginUser",user);
    }

    /**
     * 退出登录
     * 注销当前回话
     * @param session
     */
    public  static   void logout(HttpSession session){
        session.invalidate();
    }

}
