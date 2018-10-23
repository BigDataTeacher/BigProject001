package com.tecode.g05.util;

/**
 * 计算时间的工具类
 * 版本：2018/10/23 V1.0
 * 小组：D01.G05
 * 成员：李晋、建晨飞
 * Created by Administrator on 2018/10/23.
 */
public class G05TimeUtil {

    /**
     * 一天有多少毫秒
     */
    private static final long DAY_TO_SM = 86400000L;
    /**
     * 一小时有多少毫秒
     */
    private static final long HOUR_TO_SM = 3600000L;

    /**
     * 一分钟有多少毫秒
     */
    private static final long MIN_TO_SM = 60000L;

    /**
     * 一分钟有多少毫秒
     */
    private static final long S_TO_M = 1000L;

    /**
     * 传入一个时间差（毫秒），返回（*天*日*时*分*秒）
     *
     * @param time
     * @return
     */
    public static String getTime(long time) {
        long day = time / DAY_TO_SM;
        long hour = (time - day * DAY_TO_SM) / HOUR_TO_SM;
        long m = (time - day * DAY_TO_SM - hour * HOUR_TO_SM) / MIN_TO_SM;
        long s = (time - day * DAY_TO_SM - hour * HOUR_TO_SM - m * MIN_TO_SM) / S_TO_M;

        return day + "天" + hour + "小时" + m + "分钟" + s + "秒";
    }
}
