package com.tecode.util.hbase.table;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static Properties props = new Properties();

    static{
        String path = ConfigUtil.class.getClassLoader()
                .getResource("zookeeper.properties").getPath();
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            props.load(is);
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



    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    public static String getString(String key) {
        return props.getProperty(key);
    }



}
