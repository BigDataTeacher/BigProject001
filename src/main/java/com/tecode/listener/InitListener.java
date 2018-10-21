package com.tecode.listener;

import com.tecode.util.hbase.table.ConfigUtil;
import com.tecode.util.hbase.table.HBaseUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 初始化表：
 */
public class InitListener implements ServletContextListener {
    private static Logger log = Logger.getLogger(HBaseUtils.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HBaseUtils hBaseUtils = new HBaseUtils();
        String userTableName = null;
        try {
            userTableName = ConfigUtil.getString("hbase_user_tbale_name");
            String userCF[] = ConfigUtil.getString("hbase_user_tbale_cf").split(",");
            //判断表是否存在
            if(hBaseUtils.isTableExists(userTableName)) return;

            //如果不存在则，创建表
            hBaseUtils.create(userTableName, userCF);

            String taskTableName = ConfigUtil.getString("hbase_task_table_name");
            String taskCF[] = ConfigUtil.getString("hbase_task_tbale_cf").split(",");

            hBaseUtils.create(taskTableName, taskCF);
        }catch (Exception e){
            System.out.println("初始化表失败。。。。。。。。。");
            e.printStackTrace();
        }


        //初始化用户数据

            String path = ConfigUtil.class.getClassLoader()
                    .getResource("userInitData.txt").getPath();
            InputStream is = null;
            InputStreamReader isr = null;
             BufferedReader br = null;
            try {
                is = new FileInputStream(path);
                isr=new InputStreamReader(is,"UTF-8");
                br = new BufferedReader(isr);
                String line ="";
                String tableColumnName[] = new String[]{"username","password","name","department"};
                Map<String,String> map = null;
                while((line = br.readLine()) != null){
                        String split[] = line.split(",");
                         map = new HashMap<>();

                    for (int i = 0; i < split.length; i++) {
                        map.put(tableColumnName[i],split[i]);
                    }
                    try {
                        HBaseUtils.put(userTableName,split[0],"info",map);
                    } catch (Exception e) {
                        System.out.println("初始化数据失败。。。。。。");
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //关闭连接
        HBaseUtils.closeConn();
    }
}
