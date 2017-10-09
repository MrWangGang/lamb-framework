package org.lamb.lambframework.core.util;

import java.util.UUID;

/**
 * Created by WangGang on 2017/7/10 0010.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class UUIDToolKit {

    public static String get(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.toUpperCase().trim();
    }
}
