//package com.tecode.G06.service.impl;
//
//import com.tecode.G06.dao.G06TaskDao;
//import com.tecode.G06.service.G06UserService;
//import com.tecode.bean.User;
//import com.tecode.util.hbase.table.UtilSha1;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 被Controller层调用的方法所在类上添加@Service
// *
// * 这是处理用户请求的业务逻辑实现层。
// */
//@Service
//public class G06TaskServiceImpl implements G06UserService {
//
//    /**
//     * 需要调用Dao层的方法时 声明的对象 类型为接口， 添加@Autowired，实现对该对象的实例化。
//     */
//    @Autowired
//    private G06TaskDao taskDao;
//
//
//    /**
//     * 正在的登录业务处理方法
//     * @param user
//     * @return
//     */
//    @Override
//    public User getUseLogin(User user) throws Exception {
//        //System.out.println(UtilSha1.getSha1(user.getPassword().toUpperCase()));
//        User loginUser=getUserByUserName(user.getUsername());
//        //判断密码是否正确
//        boolean b= UtilSha1.getSha1(user.getPassword().toUpperCase()).equals(loginUser.getPassword());
//        if(b==false){return null;}
//
//        return  loginUser;
//    }
//    /**
//     * 根据任务id查询
//     */
//    @Override
//    public User getUserByUserName(String username) throws Exception {
//
//
//
//
//        return taskDao.getUserByUserName(username);
//    }
//
//
//}
