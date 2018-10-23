package com.tecode.g05.filter;

import com.tecode.enumBean.TaskState;
import com.tecode.g05.util.G05CreateBean;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterBase;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 关键字过滤
 * 版本：2018/10/23 V1.0
 * 小组：D01.G05
 * 成员：李晋、建晨飞
 * Created by Administrator on 2018/10/23.
 */
public class KeywordFilter{
    public static enum Operator {
        /** !AND */
        MUST_PASS_ALL,
        /** !OR */
        MUST_PASS_ONE
    }

    /**
     * 对Bean的指定属性进行关键字过滤，属性必须是String类型才能过滤关键字
     * @param objBean   过滤的Bean
     * @param fields     过滤哪些字段
     * @param keyword   关键字
     * @param op   过滤模式(MUST_PASS_ALL：全部字段都必须满足;MUST_PASS_ONE：满足其中一个字段即可)
     * @return
     * @throws IOException
     */
    public static boolean filter(Object objBean, String[] fields, String keyword, Operator op) {
        // 是否严格匹配
        boolean b = (op == Operator.MUST_PASS_ALL) ? true : false;
        // 匹配结果，如果匹配是严格模式，则默认为true，如果匹配是非严格模式，默认为false
        boolean flag = b ? true : false;    // 如果是严格模式，只要一项不匹配就将默认值反转；如果非严格模式，只要有一项匹配就将默认值反转；反转结果后即可跳出循环
        Class c = objBean.getClass();
        for (int i = 0; i < fields.length; i++) {
            String fieldsName = fields[i];
            try {
                // 获取字段的get方法
                Method method = c.getMethod("get" + fieldsName.substring(0, 1).toUpperCase() + fieldsName.substring(1));
                // 获取属性值
                String fieldValue = (String)method.invoke(objBean);
                // 如果是严格模式且匹配失败，或是非严格模式且匹配成功，反转默认值并跳出循环
                if((fieldValue.indexOf(keyword) != -1 && !b) || (fieldValue.indexOf(keyword) == -1 && b)) {
                        flag = !flag;
                        break;
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
