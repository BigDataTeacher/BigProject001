package com.tecode.G04.dao.impl;

import com.tecode.bean.User;
import com.tecode.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 *   用户数据处理层的具体实现
 *
 *   需要类上添加@Repository注解
 */
@Repository
public class G04CommentDaoImpl implements UserDao {

    /**
     * 根据传入的评论者ID获得评论者的姓名
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public User getUserByUserName(String username) throws Exception {



        return null;
    }
}
