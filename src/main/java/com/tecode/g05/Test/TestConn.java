package com.tecode.g05.Test;

import com.tecode.enumBean.TaskState;
import com.tecode.g05.dao.impl.G05TaskDaoImpl;
import com.tecode.g05.util.G05HBaseTableUtil;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.ref.SoftReference;

/**
 * 测试能否连接到HBase
 * 版本：2018/10/22 V1.0<br>
 * 小组：D01.G05<br>
 * 成员：李晋、建晨飞<br>
 */
public class TestConn {
    public static void main(String[] args) {
        G05TaskDaoImpl.addLogAndMessage("02_04102269_1539547875112", "系统消息_系统_text_aaaaaa");
    }

}
