package com.tecode.listener;

import com.tecode.util.hbase.table.HBaseUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * 初始化表：
 */
public class InitListener implements ServletContextListener {
    private static Logger log = Logger.getLogger(HBaseUtils.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //关闭连接
        HBaseUtils.closeConn();
    }
}
