package com.tecode.g05.filter;

import com.tecode.enumBean.TaskState;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterBase;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * 关键字过滤器
 * 版本：2018/10/23 V1.0
 * 小组：D01.G05
 * 成员：李晋、建晨飞
 * Created by Administrator on 2018/10/23.
 */
public class KeywordFilter extends FilterBase{
    @Override
    public ReturnCode filterKeyValue(Cell cell) throws IOException {
        // 列族
        String family = Bytes.toString(CellUtil.cloneFamily(cell));
        // 列名
        String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
        // rowKey
        String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
        // 值
        String value = Bytes.toString(CellUtil.cloneValue(cell));
        return null;
    }
}
