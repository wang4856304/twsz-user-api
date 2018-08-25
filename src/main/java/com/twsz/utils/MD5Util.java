package com.twsz.utils;

import com.twsz.exception.BusinessException;

import java.security.MessageDigest;

/**
 * }
 *
 * @author Administrator
 * @Description: md5工具
 * @date 2018/8/16 21:21
 */
public class MD5Util {

    public static String getMd5(String value) {
        MessageDigest md5 = null;
        StringBuffer hexValue = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = value.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            throw new BusinessException("md5加密错误");
        }
        return hexValue.toString().toUpperCase();
    }

    public static void main(String args[]) {
        String str = getMd5("1233alklsdad");
        System.out.println(str + ":" + str.length());
    }
}
