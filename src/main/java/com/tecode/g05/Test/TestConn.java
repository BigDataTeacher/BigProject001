package com.tecode.g05.Test;

import com.tecode.enumBean.TaskState;
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

        Cell[] cells = G05HBaseTableUtil.getColumn("oa:user", "1082038", "tasks", "04_1082026_20170101010101");
        for (Cell cell : cells) {
            System.out.println(Bytes.toString(CellUtil.cloneRow(cell)));
            System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }

}
