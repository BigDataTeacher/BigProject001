package com.tecode.g05.Test;

import com.tecode.bean.User;
import com.tecode.g05.filter.KeywordFilter;
import com.tecode.util.hbase.table.HBaseUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.Filter;

import java.io.IOException;

/**
 * 测试过滤器
 * 版本：2018/10/23 V1.0
 * 小组：D01.G05
 * 成员：李晋、建晨飞
 * Created by Administrator on 2018/10/23.
 */
public class TestFilter {

    public static void main(String[] args) {
        User user = new User();
        user.setDepartment("aaabb");
        user.setName("bbbcc");
        System.out.println(KeywordFilter.filter(user, new String[]{"department", "name"}, "ab", KeywordFilter.Operator.MUST_PASS_ONE));
        System.out.println(KeywordFilter.filter(user, new String[]{"department", "name"}, "ab", KeywordFilter.Operator.MUST_PASS_ALL));
    }
}
