package com.tecode.util.hbase.table;

import java.security.MessageDigest;

/**
 * Sha1的加密工具类
 *
 */
public class UtilSha1 {


    /**
     * 把密码传入，加密成16进制不可逆的密码。
     * 注意，大小写 不相同，加密后的结果不同
     * @param str
     * @return
     */
    public static String getSha1(String str) {

        char hexDigits[] = "0123456789ABCDEF".toCharArray();
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
