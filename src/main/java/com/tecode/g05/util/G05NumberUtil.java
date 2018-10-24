package com.tecode.g05.util;

import java.util.regex.Pattern;

/**
 * 版本：2018/10/24 V1.0
 * 小组：D01.G05
 * 成员：李晋、建晨飞
 * Created by Administrator on 2018/10/24.
 */
public class G05NumberUtil {

    /**
     * 判断传入的字符串是否是数字
     * @param s
     * @return
     */
    public static boolean isNumber(String s) {
        Pattern pattern = Pattern.compile("^[\\d]*$");
        // 判断传入的字符串是否为null或者不是正整数
        if(s == null || !pattern.matcher(s).matches()) {
            return false;
        } else {
           return true;
        }
    }
}
