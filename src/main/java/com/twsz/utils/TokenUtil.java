package com.twsz.utils;

/**
 * }
 *
 * @author Administrator
 * @Description: token生成
 * @date 2018/8/16 21:17
 */
public class TokenUtil {

    private final static String SECRET = "twsz@!123";
    private final static String DELIMITER = "_";

    public static String createToken(String mobile) {
        String value = mobile + DELIMITER + SECRET + System.currentTimeMillis();
        String md5Str = MD5Util.getMd5(value);
        return md5Str;
    }
}
