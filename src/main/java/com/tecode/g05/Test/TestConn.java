package com.tecode.g05.Test;

import com.tecode.util.hbase.table.HBaseUtils;

import java.lang.ref.SoftReference;

/**
 * 测试能否连接到HBase
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class TestConn {
    public static void main(String[] args) {
        System.out.println( HBaseUtils.getConnection());
    }

}
