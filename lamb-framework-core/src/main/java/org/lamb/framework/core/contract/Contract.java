package org.lamb.framework.core.contract;

/**
 * @description: 契约
 * @author: Mr.WangGang
 * @create: 2018-11-16 下午 3:56
 **/
public class Contract {
    public static final String LAMB_AUTH_TOKEN_SALT  = "lamb.salt";
    public static final String LAMB_TOKEN_KEY = "lamb.auth.token.";
    public static final Long LAMB_TOKEN_TIME = new Long(1800000);
    public static final String LAMB_AUTH_TOKEN_REGX = "^"+LAMB_TOKEN_KEY+"[a-zA-Z\\d]{1,}\\.[a-zA-Z\\d]{1,}$";
}
