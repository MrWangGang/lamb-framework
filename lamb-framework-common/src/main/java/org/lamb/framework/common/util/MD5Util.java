package org.lamb.framework.common.util;

import org.lamb.framework.common.exception.EventException;

import java.security.MessageDigest;

import static org.lamb.framework.common.enumeration.ExceptionEnum.ES00000035;

/**
 * @description: MD5加密
 * @author: Mr.WangGang
 * @create: 2018-12-03 上午 9:49
 **/
public class MD5Util {

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            throw new EventException(ES00000035);
        }
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            throw new EventException(ES00000035);
        }
    }
}
